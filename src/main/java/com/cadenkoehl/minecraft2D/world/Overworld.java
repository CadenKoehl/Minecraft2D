package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.world.gen.TerrainGenerator;

public class Overworld extends World {

    @Override
    public String getDisplayName() {
        return "The Overworld";
    }

    @Override
    public TerrainGenerator getGenerator() {
        return new TerrainGenerator.Builder(this)
                .defaultBlock(Blocks.DIRT)
                .surfaceBlock(Blocks.GRASS_BLOCK)
                .build();
    }
}