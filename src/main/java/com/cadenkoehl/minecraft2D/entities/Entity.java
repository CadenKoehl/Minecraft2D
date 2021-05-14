package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.client.GameClient;
import com.cadenkoehl.minecraft2D.block.BlockState;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.util.Util;
import com.cadenkoehl.minecraft2D.world.Overworld;
import com.cadenkoehl.minecraft2D.world.World;
import net.querz.nbt.tag.CompoundTag;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


public abstract class Entity extends Tile {

    public static Entity loadFromTag(CompoundTag tag) {
        EntityType<? extends Entity> entityType = EntityType.valueOf(tag.getString("Name"));

        Entity entity = entityType.createEntity();
        entity.setHealth(tag.getInt("Health"));
        entity.setPos(new Vec2d(tag.getInt("X"), tag.getInt("Y")));
        entity.setUuid(UUID.fromString(tag.getString("UUID")));

        return entity;
    }

    public int health = this.getMaxHealth();
    private boolean affectedByGravity;
    private boolean alive;
    private int damageCooldown;
    private int healCooldown;
    public int portalTicks;
    public BlockState blockOnHead;
    public BlockState blockOnFeet;
    private final EntityType<? extends Entity> type;
    private UUID uuid;

    public Entity(String displayName) {
        super(null, null, "entities", displayName);
        affectedByGravity = true;
        alive = true;
        damageCooldown = this.getMaxDamageCooldown();
        healCooldown = 10000;
        this.type = EntityType.valueOf(this.getName());
        uuid = UUID.randomUUID();
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
            if(this.getWorld() instanceof Overworld) changeWorld(GameClient.getNether());
            else changeWorld(GameClient.getOverworld());
        }

        if(this.affectedByGravity && this.getChunkX() < this.getWorld().chunksPos.size()) {
            setVelocityY(1);
        }
        if(healCooldown < 0) {
            healCooldown = 10000;
            heal();
        }

        if(blockOnHead != null && blockOnHead.getName().equals("nether_portal") && blockOnHead.isVisible()) {
            portalTicks++;
        }
        else if(blockOnFeet != null && blockOnFeet.getName().equals("nether_portal") && blockOnHead.isVisible()) {
            portalTicks++;
        }
        else portalTicks = 0;
    }

    public void postSpawn() {}

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public <E extends Entity> EntityType<E> getEntityType() {
        return (EntityType<E>) type;
    }

    public CompoundTag getCompoundTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString("Name", this.getName());
        tag.putString("UUID", uuid.toString());
        tag.putInt("Health", health);
        tag.putInt("X", pos.x);
        tag.putInt("Y", pos.y);
        return tag;
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
        this.getWorld().spawnEntity(this);
    }

    public void changeWorld(World newWorld) {
        this.getWorld().removeEntity(this);
        setWorld(newWorld);
        for(BlockState state : this.getChunk().getBlocks()) {
            if(state.getBlock() == Blocks.NETHER_PORTAL) {
                state.setVisible(true);
            }
        }
    }

    public boolean heal() {
        return heal(1);
    }

    public boolean heal(int amount) {
        if(health < getMaxHealth()) {
            health += amount;
            return true;
        }
        return false;
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

        BlockState block;

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

    public boolean isAffectedByGravity() {
        return affectedByGravity;
    }

    public void setAffectedByGravity(boolean affectedByGravity) {
        this.affectedByGravity = affectedByGravity;
    }

    public boolean tryAttack(Entity target) {

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

    public void setHealth(int health) {
        this.health = health;
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
        return this.getWorld().getBlock(this.pos.x, this.pos.y + 2) != null || this.getWorld().getBlock(this.pos.x + 1, this.pos.y + 2) != null;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getReach() {
        return 6;
    }

    public int getMaxDamageCooldown() {
        return 200;
    }

    public abstract int getMaxHealth();
    public abstract int getBaseAttackDamage();
}
