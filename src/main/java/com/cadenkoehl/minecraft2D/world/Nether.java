package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.world.biome.NetherWastesBiome;
import com.cadenkoehl.minecraft2D.world.gen.TerrainGenerator;
import com.cadenkoehl.minecraft2D.world.gen.feature.ConfiguredFeature;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Nether extends World {

    @Override
    public String getDisplayName() {
        return "The Nether";
    }

    @Override
    public Color getSkyColor() {
        return new Color(0x551212);
    }

    @Override
    public TerrainGenerator getGenerator() {
        return new TerrainGenerator.Builder(this)
                .addBiome(new NetherWastesBiome())
                .defaultBlock(Blocks.NETHERRACK)
                .depth(14)
                .build();
    }

    @Override
    public List<ConfiguredFeature> getFeatures() {
        return new ArrayList<>();
    }

    @Override
    public boolean hasDaylightCycle() {
        return false;
    }
}
