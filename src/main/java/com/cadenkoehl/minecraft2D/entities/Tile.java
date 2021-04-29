package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.display.GameFrame;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

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
    private final Texture texture;
    private final String displayName;
    private final String name;

    public final int height;
    public final int width;

    public final int blockWidth;
    public final int blockHeight;

    public Tile(Vec2d pos, World world, String textureCategory, String displayName) {
        this.pos = pos;
        if (pos != null) screenPos = Vec2d.toScreenPos(pos);
        this.velocity = new Vec2d(0, 0);
        this.world = world;
        this.displayName = displayName;
        this.name = displayName.replace(" ", "_").toLowerCase();
        this.texture = new Texture("textures/" + textureCategory + "/" + name + ".png");
        this.height = texture.getIcon().getIconHeight();
        this.width = texture.getIcon().getIconWidth();
        this.blockHeight = height / BLOCK_SIZE;
        this.blockWidth = width / BLOCK_SIZE;
    }

    public void tick() {

        if(!this.inFrame()) return;

        if (pos == null || world == null) return;

        syncPos();

        if (velocity.x == 0 && velocity.y == 0) return;

        updatePos();
    }

    public void updatePos() {
        updatePosX();
        updatePosY();
    }

    public boolean inFrame() {
        return this.screenPos.y - Renderer.CAMERA.offset.y > -50 &&
                this.screenPos.y - Renderer.CAMERA.offset.y < 570 &&
                this.screenPos.x - Renderer.CAMERA.offset.x > -50 &&
                this.screenPos.x - Renderer.CAMERA.offset.x < GameFrame.WIDTH;
    }

    protected void updatePosX(){
        setScreenPosX(screenPos.x + velocity.x);
    }

    protected void updatePosY() {
        setScreenPosY(screenPos.y + velocity.y);
    }

    protected void syncPos() {
        if (pos != null) pos = Vec2d.toGamePos(screenPos);
    }

    public void render() {
        if(this.inFrame()) {
            Renderer.render(this, screenPos.x, screenPos.y);
        }
    }

    public int distanceFrom(Vec2d pos) {
        int x = this.pos.x - pos.x;
        int y = this.pos.y - pos.y;
        return Math.abs(x + y);
    }

    public int distanceFrom(Tile tile) {
        return this.distanceFrom(tile.pos);
    }

    public String getName() {
        return name;
    }

    public World getWorld() {
        return world;
    }

    public Texture getTexture() {
        return texture;
    }

    public String getDisplayName() {
        return displayName;
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
    }

    public int getPosX() {
        return this.pos.x;
    }

    public int getPosY() {
        return this.pos.y;
    }
}
