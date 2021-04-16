package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.physics.Location;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;


public abstract class Entity {

    private int health = this.getMaxHealth();
    private Location location;

    public static final int SIZE_MULTIPLIER = 5;
    public static final int GRAVITY_MULTIPLIER = 10;

    public Entity(Location location) {
        this.location = location;
    }

    public abstract Texture getTexture();
    public abstract int getMaxHealth();
    public abstract String getDisplayName();

    public String getName() {
        return this.getDisplayName().toLowerCase().replace(" ", "_");
    }

    public void applyGravity() {
        this.setPosY(this.getPosY() + 10);
    }

    public boolean hasCollidedWith(Block block, int blockPosX, int blockPosY) {
        int entityWidth = this.getWidth();
        int entityHeight = this.getHeight();

        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        return blockPosX < this.getPosX() + entityWidth &&
                blockPosX + blockWidth > this.getPosX() &&
                blockPosY < this.getPosY() + entityHeight &&
                blockPosY + blockHeight > this.getPosY();
    }

    public boolean isCollidingWithBlock(World world) {
        for(Location location : world.blocks.keySet()) {
            Block block = world.blocks.get(location);
            if(this.hasCollidedWith(block, location.getX(), location.getY())) return true;
        }
        return false;
    }

    public int getHeight() {
        return this.getTexture().getIcon().getIconHeight() * SIZE_MULTIPLIER;
    }

    public int getWidth() {
        return this.getTexture().getIcon().getIconWidth() * SIZE_MULTIPLIER;
    }

    public void render() {
        Renderer renderer = new Renderer();
        renderer.render(this, this.getPosX(), this.getPosY());
    }

    public void damage(int amount) {
        health = health - amount;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPosX(int posX) {
        this.location = new Location(posX, location.getY());
    }

    public void setPosY(int posY) {
        this.location = new Location(location.getX(), posY);
    }

    public int getPosX() {
        return this.location.getX();
    }

    public int getPosY() {
        return this.location.getY();
    }
}
