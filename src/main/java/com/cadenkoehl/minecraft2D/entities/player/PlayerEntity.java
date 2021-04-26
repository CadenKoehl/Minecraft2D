package com.cadenkoehl.minecraft2D.entities.player;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.display.Hud;
import com.cadenkoehl.minecraft2D.entities.mob.LivingEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

public class PlayerEntity extends LivingEntity {

    public final Vec2d originalPos;
    public Block heldBlock;

    public PlayerEntity(String username, Vec2d vec2d, World world) {
        super(vec2d, world, username);
        this.originalPos = vec2d;
        this.heldBlock = Blocks.DIRT;
    }

    public void placeBlock(Vec2d pos) {
        placeBlock(pos, heldBlock);
    }

    public void placeBlock(Vec2d pos, Block block) {
        if(distanceFrom(pos) <= getReach()) {
            this.getWorld().setBlock(block, pos);
            block.updateGraphics();
        }
    }

    public void breakBlock(Vec2d pos) {
        if(distanceFrom(pos) <= getReach()) {
            this.getWorld().breakBlock(pos);
        }
    }

    @Override
    public void render() {
        super.render();
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
    public int getBaseAttackDamage() {
        return 1;
    }

    @Override
    public String getDisplayName() {
        return "player";
    }
}