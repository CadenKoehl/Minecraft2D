package com.cadenkoehl.minecraft2D.entities.mob;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

import java.util.Timer;
import java.util.TimerTask;


public abstract class LivingEntity extends Tile {

    public int health = this.getMaxHealth();
    private boolean affectedByGravity;
    private boolean alive;
    private int damageCooldown;

    public LivingEntity(Vec2d pos, World world, String displayName) {
        super(pos, world, "entities", displayName);
        affectedByGravity = true;
        alive = true;
        damageCooldown = 500;
    }

    @Override
    public void tick() {

        if(!alive) {
            return;
        }

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

    public boolean hasCollidedWith(Tile entity) {

        int playerWidth = this.getWidth();
        int playerHeight = this.getHeight();

        return this.screenPos.x < entity.screenPos.x + entity.getWidth() &&
                this.screenPos.x + playerWidth > entity.screenPos.x &&
                this.screenPos.y < entity.screenPos.y + entity.getHeight() &&
                this.screenPos.y + playerHeight > entity.screenPos.y;
    }

    public void moveRight(int amount) {
        setVelocityX(amount);
    }

    public void moveLeft(int amount) {
        setVelocityX(-amount);
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

    public boolean tryAttack(LivingEntity target) {

        if(target == this) return false;

        if(distanceFrom(target) <= getReach()) {
            if(target.damage(this.getBaseAttackDamage())) {
                if(target.screenPos.x > this.screenPos.x) {
                    target.moveRight(3);
                }
                if(target.screenPos.x < this.screenPos.x) {
                    target.moveLeft(3);
                }
                target.jump();
                return true;
            }
        }
        return false;
    }

    public boolean damage(int amount) {
        if(damageCooldown < 0) {
            health = health - amount;
            if(health < 1) kill();
            damageCooldown = 350;
            return true;
        }
        return false;
    }

    public void kill() {
        alive = false;
        updateGraphics();
    }

    public boolean isOnGround() {
        return this.getWorld().getBlock(this.pos.x, this.pos.y + 2) != null;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getReach() {
        return 6;
    }

    @Override
    public boolean isAffectedByGravity() {
        return true;
    }

    public abstract int getMaxHealth();
    public abstract int getBaseAttackDamage();
}
