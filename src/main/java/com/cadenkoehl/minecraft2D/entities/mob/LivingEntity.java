package com.cadenkoehl.minecraft2D.entities.mob;

import com.cadenkoehl.minecraft2D.Game;
import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.NetherPortalBlock;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.util.Util;
import com.cadenkoehl.minecraft2D.world.Nether;
import com.cadenkoehl.minecraft2D.world.Overworld;
import com.cadenkoehl.minecraft2D.world.World;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public abstract class LivingEntity extends Tile {

    public int health = this.getMaxHealth();
    private boolean affectedByGravity;
    private boolean alive;
    private int damageCooldown;
    private int healCooldown;
    public int portalTicks;
    public Block blockOnHead;
    public Block blockOnFeet;

    public LivingEntity(Vec2d pos, World world, String displayName) {
        super(pos, world, "entities", displayName);
        affectedByGravity = true;
        alive = true;
        damageCooldown = this.getMaxDamageCooldown();
        healCooldown = 10000;
    }

    @Override
    public void tick() {

        if(!alive) {
            return;
        }

        blockOnHead = this.getWorld().getBlock(pos.x, pos.y);
        blockOnFeet = this.getWorld().getBlock(pos.x, pos.y + 1);

        super.tick();

        damageCooldown--;
        healCooldown--;

        if(portalTicks > 500) {
            portalTicks = 0;
            if(this.getWorld() instanceof Overworld) setWorld(Game.getNether());
            else setWorld(Game.getOverworld());
        }

        if(this.affectedByGravity) {
            setVelocityY(1);
        }
        if(healCooldown < 0) {
            healCooldown = 10000;
            heal();
        }

        if(blockOnHead != null && blockOnHead.getName().equals("nether_portal")) {
            portalTicks++;
        }
        else if(blockOnFeet != null && blockOnFeet.getName().equals("nether_portal")) {
            portalTicks++;
        }
        else portalTicks = 0;
        portalTicks = 0;
    }

    public void heal() {
        if(health < getMaxHealth()) {
            health++;
        }
    }

    protected void updatePosX() {

        //Moving right
        if(velocity.x > 0) {

            if(this.pos.x > getWorld().width - 2) return;

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

            if(this.pos.x < 1) return;

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

        Block block;

        if(pos.x == x && pos.y == y) {
            block = blockOnHead;
        }
        else if(pos.x == x && pos.y == y + 1) {
            block = blockOnFeet;
        }
        else {
            block = this.getWorld().getBlock(new Vec2d(x, y));
        }

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
                    Util.scheduleTask(() -> target.moveRight(1), 100);
                    Util.scheduleTask(() -> target.setVelocityX(0), 400);
                }
                if(target.screenPos.x < this.screenPos.x) {
                    target.moveLeft(3);
                    Util.scheduleTask(() -> target.moveLeft(1), 100);
                    Util.scheduleTask(() -> target.setVelocityX(0), 400);
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
            damageCooldown = this.getMaxDamageCooldown();
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

    public int getMaxDamageCooldown() {
        return 200;
    }

    public abstract int getMaxHealth();
    public abstract int getBaseAttackDamage();
}
