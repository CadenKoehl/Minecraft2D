package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.display.Hud;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

public class PlayerEntity extends LivingEntity {

    public final Hud HUD;
    public final Vec2d originalPos;
    public Block heldBlock;

    public PlayerEntity(String username, Vec2d vec2d, World world) {
        super(vec2d, world);
        HUD = new Hud(this);
        this.originalPos = vec2d;
        this.heldBlock = Blocks.DIRT;
    }

    public void placeBlock(Vec2d pos) {
        Block block = this.heldBlock.copy();
        this.getWorld().setBlock(block, pos);
        block.updateGraphics();
    }

    public void breakBlock(Vec2d pos) {
        this.getWorld().breakBlock(pos);
    }

    @Override
    public void damage(int amount) {
        super.damage(amount);
        HUD.updateHP();
    }

    @Override
    public void kill() {
        super.kill();
    }

    @Override
    public void render() {
        super.render();
        HUD.updateHP();
    }

    @Override
    public Texture getTexture() {
        return new Texture("textures/skin.png");
    }

    @Override
    public int getMaxHealth() {
        return 10;
    }

    @Override
    public String getDisplayName() {
        return "player";
    }
}