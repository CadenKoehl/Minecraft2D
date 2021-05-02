package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

import java.awt.*;

public abstract class FluidBlock extends Block {

    public FluidBlock(String displayName, Vec2d pos, World world) {
        super(displayName, pos, world);
    }

    @Override
    public Texture getTexture() {
        return new Texture(this.getColor(), BLOCK_SIZE, BLOCK_SIZE);
    }

    public abstract Color getColor();

    @Override
    public boolean canCollide() {
        return false;
    }

    @Override
    public boolean canBeMined() {
        return false;
    }
}
