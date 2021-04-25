package com.cadenkoehl.minecraft2D.entities.mob.hostile;

import com.cadenkoehl.minecraft2D.display.GameWindow;
import com.cadenkoehl.minecraft2D.entities.mob.LivingEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public abstract class HostileEntity extends LivingEntity {

    public HostileEntity(Vec2d pos, World world, String displayName) {
        super(pos, world, displayName);
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
        if(distanceFrom(GameWindow.player) >= getReach() / 3) {
            if(GameWindow.player.pos.x > this.pos.x) {
                moveRight(1);
            }
            if(GameWindow.player.pos.x < this.pos.x) {
                moveLeft(1);
            }
        }
        else {
            if(GameWindow.player.pos.x > this.pos.x) {
                if(velocity.x > 0) {
                    setVelocityX(0);
                }
            }
            if(GameWindow.player.pos.x < this.pos.x) {
                if(velocity.x < 0) {
                    setVelocityX(0);
                }
            }
            tryAttack(GameWindow.player);
        }
    }

    public void runAwayFromPlayer() {
        if(distanceFrom(GameWindow.player) <= GameWindow.player.getReach()) {
            if(GameWindow.player.pos.x > this.pos.x) {
                moveLeft(1);
            }
            if(GameWindow.player.pos.x < this.pos.x) {
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
