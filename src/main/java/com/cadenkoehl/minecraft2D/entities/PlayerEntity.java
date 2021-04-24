package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.display.GameWindow;
import com.cadenkoehl.minecraft2D.display.Hud;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

public class PlayerEntity extends LivingEntity {

    public final Hud HUD;

    public PlayerEntity(String username, Vec2d vec2d, World world) {
        super(vec2d, world);
        HUD = new Hud(this);
    }

    public void placeBlock(Block block, Vec2d pos) {
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