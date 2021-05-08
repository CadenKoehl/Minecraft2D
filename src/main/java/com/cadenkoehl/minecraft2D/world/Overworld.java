package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.world.biome.Biomes;
import com.cadenkoehl.minecraft2D.world.gen.TerrainGenerator;
import com.cadenkoehl.minecraft2D.world.gen.feature.ConfiguredFeature;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Overworld extends World {

    public Overworld(long seed) {
        super(seed);
    }

    @Override
    public String getDisplayName() {
        return "The Overworld";
    }

    @Override
    public Color getSkyColor() {
        return new Color(150, 212, 246);
    }

    @Override
    public TerrainGenerator getGenerator() {
        return new TerrainGenerator.Builder(this)
                .defaultBlock(Blocks.STONE)
                .addCarvers()
                .addBiome(Biomes.PLAINS)
                .addBiome(Biomes.DESERT)
                .addBiome(Biomes.DESERT_HILLS)
                .depth(14)
                .build();
    }

    @Override
    public boolean hasDaylightCycle() {
        return true;
    }
}