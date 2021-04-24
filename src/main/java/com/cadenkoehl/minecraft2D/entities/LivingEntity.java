package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

import java.util.Timer;
import java.util.TimerTask;


public abstract class LivingEntity extends Tile {

    public int health = this.getMaxHealth();
    private boolean affectedByGravity;
    private boolean alive;
    private int damageCooldown;

    public LivingEntity(Vec2d pos, World world) {
        super(pos, world);
        affectedByGravity = true;
        alive = true;
        damageCooldown = 500;
    }

    @Override
    public void tick() {

        if(!alive) return;

        super.tick();

        damageCooldown--;

        if(affectedByGravity) {
            setVelocityY(1);
        }
        if(this.screenPos.y > 2000) {
            this.damage(1);
        }
    }

    public void jump() {
        if(this.isOnGround()) {
            affectedByGravity = false;
            setVelocityY(-1);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    affectedByGravity = true;
                }
            }, 150);
        }
    }

    public boolean isOnGround() {
        return this.getWorld().getBlock(this.pos.x, this.pos.y + 2) != null;
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public int getCollisionHeight() {
        return height;
    }

    @Override
    public int getCollisionWidth() {
        return width;
    }

    @Override
    public boolean isAffectedByGravity() {
        return true;
    }

    public abstract int getMaxHealth();

    public void damage(int amount) {
        if(damageCooldown < 0) {
            health = health - amount;
            if(health < 1) kill();
            damageCooldown = 500;
        }
    }

    public void kill() {
        alive = false;
        updateGraphics();
    }
}
