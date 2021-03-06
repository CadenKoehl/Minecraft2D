package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.world.biome.Biomes;
import com.cadenkoehl.minecraft2D.world.gen.TerrainGenerator;

import java.awt.*;

public class Nether extends World {


    public Nether(Realm realm) {
        super(realm);
    }

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
                .addBiome(Biomes.NETHER_WASTES)
                .defaultBlock(Blocks.NETHERRACK)
                .depth(14)
                .build();
    }

    @Override
    public boolean hasDaylightCycle() {
        return false;
    }
}