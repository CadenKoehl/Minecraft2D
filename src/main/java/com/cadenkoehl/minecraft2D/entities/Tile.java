package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.display.GameWindow;
import com.cadenkoehl.minecraft2D.physics.Direction;
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

    public Vec2d pos;
    public Vec2d screenPos;
    public Vec2d velocity;
    private World world;

    private final String name;

    public final int height;
    public final int width;

    public final int blockWidth;
    public final int blockHeight;

    public Tile(Vec2d pos, World world) {
        this.pos = pos;
        if (pos != null) screenPos = Vec2d.toScreenPos(pos);
        this.velocity = new Vec2d(0, 0);
        this.world = world;
        this.name = this.getDisplayName().toLowerCase().replace(" ", "_");
        this.height = this.getTexture().getIcon().getIconHeight();
        this.width = this.getTexture().getIcon().getIconWidth();
        this.blockHeight = height / BLOCK_SIZE;
        this.blockWidth = width / BLOCK_SIZE;
    }

    public void tick() {

        if (pos == null || world == null) return;

        syncPos();
        //applyGravity();

        if (velocity.x == 0 && velocity.y == 0) return;

        updatePos();
    }

    public boolean collisionWithBlock(int x, int y) {
        return world.getBlock(new Vec2d(x, y)) != null;
    }

    public void updatePos() {
        updatePosX();
        updatePosY();
    }

    private void updatePosX(){
        //Moving right
        if(velocity.x > 0) {
            if(!collisionWithBlock(this.pos.x + 1, this.pos.y) && !collisionWithBlock(this.pos.x + 1, this.pos.y + 1)) {
                setScreenPosX(screenPos.x + velocity.x);
            }
        }
        //Moving left
        if(velocity.x < 0) {
            if(!collisionWithBlock(this.pos.x, this.pos.y) && !collisionWithBlock(this.pos.x, this.pos.y + 1)) {
                setScreenPosX(screenPos.x + velocity.x);
            }
        }
    }

    private void updatePosY() {
        //Moving down
        if(velocity.y > 0) {
            if(!collisionWithBlock(this.pos.x, this.pos.y + 2)) {
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


    public boolean hasCollidedWith(Block block) {

        int playerWidth = this.getCollisionWidth();
        int playerHeight = this.getCollisionHeight();

        return this.screenPos.x < block.screenPos.x + block.getWidth() &&
                this.screenPos.x + playerWidth > block.screenPos.x &&
                this.screenPos.y < block.screenPos.y + block.getHeight() &&
                this.screenPos.y + playerHeight > block.screenPos.y;
    }

    public List<Block> getCollidingBlocks() {

        List<Block> blocks = new ArrayList<>();

        for(Block block : world.getBlocks()) {
            if(this.hasCollidedWith(block)) {
                blocks.add(block);
            }
        }
        return blocks;
    }

    public Direction getDirection() {
        if(Math.abs(velocity.x) > Math.abs(velocity.y)) {
            if(velocity.x < 0) {
                return Direction.LEFT;
            }
            if(velocity.x > 0) {
                return Direction.RIGHT;
            }
        }
        if(velocity.y < 0) {
            return Direction.UP;
        }
        if(velocity.y > 0) {
            return Direction.DOWN;
        }
        return null;
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

    public void setWorld(World world) {
        this.world = world;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void updateGraphics() {
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
    public abstract int getCollisionWidth();
    public abstract int getCollisionHeight();
}
