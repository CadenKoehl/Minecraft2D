package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.display.GameWindow;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

/**
 * Represents an object in the game with a texture
 */
public abstract class Tile {

    public static final int SIZE_MULTIPLIER = 5;
    public static final int GRAVITY_MULTIPLIER = 10;

    public Vec2d pos;
    public Vec2d velocity;
    private World world;

    private final String name;
    private final int height;
    private final int width;

    public Tile(Vec2d pos, World world) {
        this.pos = pos;
        this.velocity = new Vec2d(0, 0);
        this.world = world;
        this.name = this.getDisplayName().toLowerCase().replace(" ", "_");
        this.height = this.getTexture().getIcon().getIconHeight() * SIZE_MULTIPLIER;
        this.width = this.getTexture().getIcon().getIconWidth() * SIZE_MULTIPLIER;
    }

    public void tick() {

        if(pos == null || world == null) return;

        if(velocity.x == 0 && velocity.y == 0) return;

        setPosX(this.getPosX() + velocity.x);
        setPosY(this.getPosY() + velocity.y);
    }

    public void render() {
        Renderer.render(this, this.getPosX() * SIZE_MULTIPLIER, this.getPosY() * SIZE_MULTIPLIER);
    }

    public String getName() {
        return name;
    }

    public Vec2d getLocation() {
        return this.pos;
    }

    public World getWorld() {
        return world;
    }

    public void setVelocity(Vec2d velocity) {
        this.velocity = velocity;
    }

    public void setVelocityX(int x) {
        setVelocity(new Vec2d(x, velocity.y));
    }

    public void setVelocityY(int y) {
        setVelocity(new Vec2d(velocity.x, y));
    }

    public void setPosX(int posX) {
        setPos(new Vec2d(posX, pos.y));
    }

    public void setPosY(int posY) {
        setPos(new Vec2d(pos.x, posY));
    }

    public void setPos(Vec2d pos) {
        updateGraphics();
        this.pos = pos;
        updateGraphics();
    }

    public void changePosWithoutRender(Vec2d pos) {
        this.pos = pos;
    }

    private void applyGravity() {
        setVelocityY(1);
    }

    public boolean hasCollidedWith(Block block) {

        int blockPosX = block.getLocation().x;
        int blockPosY = block.getLocation().y;

        int entityWidth = this.getWidth();
        int entityHeight = this.getHeight();

        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        return blockPosX < this.getPosX() + entityWidth &&
                blockPosX + blockWidth > this.getPosX() &&
                blockPosY < this.getPosY() + entityHeight &&
                blockPosY + blockHeight > this.getPosY();
    }

    public boolean isCollidingWithBlock() {
        for(Block block : world.getBlocks()) {
            if(this.hasCollidedWith(block)) return true;
        }
        return false;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    protected void updateGraphics() {
        GameWindow.INSTANCE.repaint(getPosX(), getPosY(), getWidth(), getHeight());
    }

    public int getPosX() {
        return this.pos.x;
    }

    public int getPosY() {
        return this.pos.y;
    }

    public abstract Texture getTexture();
    public abstract String getDisplayName();
}
