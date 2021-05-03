package com.cadenkoehl.minecraft2D.world.biome;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.world.gen.feature.ConfiguredFeature;
import com.cadenkoehl.minecraft2D.world.gen.feature.OreFeature;

import java.util.List;

public class NetherWastesBiome implements Biome {

    @Override
    public Block getSurfaceBlock() {
        return Blocks.NETHERRACK;
    }

    @Override
    public Block getSecondarySurfaceBlock() {
        return Blocks.NETHERRACK;
    }

    @Override
    public List<ConfiguredFeature> getFeatures() {
        return List.of(new OreFeature(Blocks.SOUL_SAND, 1, 1, 15));
    }

    @Override
    public int height() {
        return 2;
    }

    @Override
    public int rarity() {
        return 0;
    }
}
