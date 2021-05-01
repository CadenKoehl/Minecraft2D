package com.cadenkoehl.minecraft2D.world.biome;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.world.gen.feature.ConfiguredFeature;

import java.util.List;

public class NetherWastesBiome implements Biome {

    @Override
    public Block getSurfaceBlock() {
        return Blocks.NETHERRACK;
    }

    @Override
    public Block getSecondarySurfaceBlock() {
        return Blocks.SOUL_SAND;
    }

    @Override
    public List<ConfiguredFeature> getFeatures() {
        return null;
    }

    @Override
    public int rarity() {
        return 0;
    }
}
