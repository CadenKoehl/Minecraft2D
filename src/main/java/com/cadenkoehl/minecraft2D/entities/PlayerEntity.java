package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

public class PlayerEntity extends LivingEntity {

    public PlayerEntity(Vec2d vec2d, World world) {
        super(vec2d, world);
    }

    public PlayerEntity(int x, int y, World world) {
        super(new Vec2d(x, y), world);
    }

    @Override
    public Texture getTexture() {
        return new Texture("textures/skin.png");
    }

    @Override
    public int getMaxHealth() {
        return 20;
    }

    @Override
    public String getDisplayName() {
        return "Player";
    }
}