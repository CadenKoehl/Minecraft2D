package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.display.GameWindow;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an object in the game with a texture
 */
public abstract class Tile {

    public static final int SIZE_MULTIPLIER = 3;
    public static final int BLOCK_SIZE = SIZE_MULTIPLIER * 16;
    public static final int GRAVITY_MULTIPLIER = 10;

    public Vec2d pos;
    public Vec2d screenPos;
    public Vec2d velocity;
    private World world;

    private final String name;
    public final int height;
    public final int width;

    public Tile(Vec2d pos, World world) {
        this.pos = pos;
        if (pos != null) screenPos = Vec2d.toScreenPos(pos);
        this.velocity = new Vec2d(0, 0);
        this.world = world;
        this.name = this.getDisplayName().toLowerCase().replace(" ", "_");
        this.height = this.getTexture().getIcon().getIconHeight();
        this.width = this.getTexture().getIcon().getIconWidth();
    }

    public void tick() {

        if (pos == null || world == null) return;

        syncPos();
        //applyGravity();

        if (velocity.x == 0 && velocity.y == 0) return;

        checkCollisions();
        updatePos();
    }

    public void updatePos() {

        Block blockX = this.getCollidingBlockX();
        Block blockY = this.getCollidingBlockY();

        if(canUpdateXPos(blockX)) setScreenPosX(screenPos.x + velocity.x);
        if(canUpdateYPos(blockY)) setScreenPosY(screenPos.y + velocity.y);
    }

    private boolean canUpdateXPos(Block blockX) {
        if(blockX == null) return true;

        //if this is to the left of the block
        if(this.screenPos.x < blockX.screenPos.x) {
            //if this is going to the right
            if(this.velocity.x < 0) {
                return true;
            }
        }
        //if this is to the right if the block
        if(this.screenPos.x > blockX.screenPos.x) {
            //if this is going to the left
            if(this.velocity.x > 0) {
                return true;
            }
        }
        return this.getCollidingBlockY() == null;
    }

    private boolean canUpdateYPos(Block blockY) {
        if(blockY == null) return true;

        //if this is below the block
        if(this.screenPos.y > blockY.screenPos.y) {
            //if this is going upwards
            if(this.velocity.y > 0) {
                return true;
            }
        }

        //if this is above the block
        if(this.screenPos.y < blockY.screenPos.y) {
            //if this is going downwards
            if(this.velocity.y < 0) {
                return true;
            }
        }
        return this.getCollidingBlockX() == null;
    }

    protected void syncPos() {
        if (pos != null) pos = Vec2d.toGamePos(screenPos);
    }

    public void render() {
        Renderer.render(this, screenPos.x, screenPos.y);
    }

    public String getName() {
        return name;
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
        this.pos = pos;
    }

    public void setScreenPos(Vec2d screenPos) {
        updateGraphics();
        this.screenPos = screenPos;
        updateGraphics();
        setPos(Vec2d.toGamePos(screenPos));
    }

    public void setScreenPosX(int x) {
        setScreenPos(new Vec2d(x, screenPos.y));
    }

    public void setScreenPosY(int y) {
        setScreenPos(new Vec2d(screenPos.x, y));
    }

    public void changePosWithoutRender(Vec2d pos) {
        this.pos = pos;
        screenPos = Vec2d.toScreenPos(pos);
    }

    private void applyGravity() {
        if(this.isAffectedByGravity()) {
            setVelocityY(1);
        }
    }

    public boolean isAffectedByGravity() {
        return false;
    }

    public void checkCollisions() {
    }

    public boolean hasCollidedWithY(Block block) {
        return this.screenPos.y < block.screenPos.y + block.width &&
                this.screenPos.y + this.height > block.screenPos.y;
    }

    public boolean hasCollidedWithX(Block block) {
        return this.screenPos.x < block.screenPos.x + block.width &&
                this.screenPos.x + this.width > block.screenPos.x;
    }

    public boolean hasCollidedWith(Block block) {
        return this.screenPos.x < block.screenPos.x + block.getWidth() &&
                this.screenPos.x + this.width > block.screenPos.x &&
                this.screenPos.y < block.screenPos.y + block.getHeight() &&
                this.screenPos.y + this.height > block.screenPos.y;
    }

    public Block getCollidingBlockY() {
        for(Block block : world.getBlocks()) {
            if(this.hasCollidedWithY(block)) return block;
        }
        return null;
    }

    public Block getCollidingBlockX() {
        for(Block block : world.getBlocks()) {
            if(this.hasCollidedWithX(block)) return block;
        }
        return null;
    }

    public List<Block> getCollidingBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (Block block : world.getBlocks()) {
            if (this.hasCollidedWith(block)) blocks.add(block);
        }
        return blocks;
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
        if(screenPos == null) {
            screenPos = Vec2d.toScreenPos(pos);
        }
        GameWindow.INSTANCE.repaint(screenPos.x, screenPos.y, width, height);
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
