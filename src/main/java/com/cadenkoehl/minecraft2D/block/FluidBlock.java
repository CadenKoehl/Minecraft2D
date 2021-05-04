package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

import java.awt.*;

public class FluidBlock extends Block {

    private final Color color;

    public FluidBlock(Color color, Settings settings) {
        super(settings.breakSpeed(-1));
        this.color = color;
    }

    @Override
    public Texture getTexture() {
        return new Texture(this.getColor(), Tile.BLOCK_SIZE, Tile.BLOCK_SIZE);
    }

    public Color getColor() {
        return color;
    }
}
