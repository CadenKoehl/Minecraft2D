package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.Blocks;

public class Overworld extends World {

    @Override
    public void load() {}

    @Override
    public String getDisplayName() {
        return "The Overworld";
    }

    @Override
    public Block getSurfaceBlock() {
        return Blocks.GRASS_BLOCK;
    }
}