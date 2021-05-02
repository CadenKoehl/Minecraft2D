package com.cadenkoehl.minecraft2D.world.biome;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.world.gen.feature.ConfiguredFeature;
import com.cadenkoehl.minecraft2D.world.gen.feature.LakeFeature;
import com.cadenkoehl.minecraft2D.world.gen.feature.OreFeature;
import com.cadenkoehl.minecraft2D.world.gen.feature.TreeFeature;

import java.util.List;

public class PlainsBiome implements Biome {

    @Override
    public Block getSurfaceBlock() {
        return Blocks.GRASS_BLOCK;
    }

    @Override
    public Block getSecondarySurfaceBlock() {
        return Blocks.DIRT;
    }

    @Override
    public List<ConfiguredFeature> getFeatures() {
        return List.of(
                new TreeFeature(),
                new LakeFeature(Blocks.WATER),
                new OreFeature(Blocks.GRAVEL, 1, 5, 10)
        );
    }

    @Override
    public int rarity() {
        return 0;
    }
}
