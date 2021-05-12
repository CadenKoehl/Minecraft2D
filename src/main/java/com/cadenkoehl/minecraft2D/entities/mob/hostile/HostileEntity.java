package com.cadenkoehl.minecraft2D.entities.mob.hostile;

import com.cadenkoehl.minecraft2D.client.GameClient;
import com.cadenkoehl.minecraft2D.entities.Entity;
import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public abstract class HostileEntity extends Entity {

    public HostileEntity(String displayName) {
        super(displayName);
    }

    @Override
    public void tick() {
        super.tick();
        if(health > 2) {
            followPlayer();
        }
        else {
            runAwayFromPlayer();
        }
    }

    public void followPlayer() {

        PlayerEntity player = GameClient.getPlayer();

        if(distanceFrom(player) >= getReach() / 3) {
            if(player.pos.x > this.pos.x) {
                moveRight(1);
            }
            if(player.pos.x < this.pos.x) {
                moveLeft(1);
            }
        }
        else {
            if(player.pos.x > this.pos.x) {
                if(velocity.x > 0) {
                    setVelocityX(0);
                }
            }
            if(player.pos.x < this.pos.x) {
                if(velocity.x < 0) {
                    setVelocityX(0);
                }
            }
            tryAttack(player);
        }
    }

    public void runAwayFromPlayer() {
        PlayerEntity player = GameClient.getPlayer();

        if(distanceFrom(player) <= player.getReach()) {
            if(player.pos.x > this.pos.x) {
                moveLeft(1);
            }
            if(player.pos.x < this.pos.x) {
                moveRight(1);
            }
        }
        else {
            setVelocityX(0);
        }
    }

    @Override
    public int getMaxHealth() {
        return 10;
    }
}
