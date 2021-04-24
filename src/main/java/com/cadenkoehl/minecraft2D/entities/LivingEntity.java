package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.block.Block;
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

    protected void updatePosX(){
        //Moving right
        if(velocity.x > 0) {
            if(!collisionWithBlock(this.pos.x + 1, this.pos.y)) {
                if(!collisionWithBlock(this.pos.x + 1, this.pos.y + 1)) {
                    setScreenPosX(screenPos.x + velocity.x);
                    return;
                }
                jump(100);
            }
        }
        //Moving left
        if(velocity.x < 0) {
            if(!collisionWithBlock(this.pos.x, this.pos.y)) {
                if(!collisionWithBlock(this.pos.x, this.pos.y + 1)) {
                    setScreenPosX(screenPos.x + velocity.x);
                    return;
                }
                jump(100);
            }
        }
    }

    protected void updatePosY() {
        //Moving down
        if(velocity.y > 0) {
            if(!collisionWithBlock(this.pos.x + 1, this.pos.y + 2) && !collisionWithBlock(this.pos.x, this.pos.y + 2)) {
                setScreenPosY(screenPos.y + velocity.y);
            }
        }
        //Moving up
        if(velocity.y < 0) {
            if(!collisionWithBlock(this.pos.x, this.pos.y)) {
                setScreenPosY(screenPos.y + velocity.y);
            }
        }
    }

    public boolean collisionWithBlock(int x, int y) {
        Block block = this.getWorld().getBlock(new Vec2d(x, y));

        if (block == null) {
            return false;
        }

        return block.canCollide();
    }

    public boolean hasCollidedWith(Block block) {

        int playerWidth = this.getCollisionWidth();
        int playerHeight = this.getCollisionHeight();

        return this.screenPos.x < block.screenPos.x + block.getWidth() &&
                this.screenPos.x + playerWidth > block.screenPos.x &&
                this.screenPos.y < block.screenPos.y + block.getHeight() &&
                this.screenPos.y + playerHeight > block.screenPos.y;
    }

    public void jump() {
        jump(150);
    }

    public void jump(long force) {
        if(this.isOnGround()) {
            affectedByGravity = false;
            setVelocityY(-1);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    affectedByGravity = true;
                }
            }, force);
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
