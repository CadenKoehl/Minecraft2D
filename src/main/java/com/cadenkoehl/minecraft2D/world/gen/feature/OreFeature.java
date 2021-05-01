package com.cadenkoehl.minecraft2D.world.gen.feature;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.world.World;

public class OreFeature implements ConfiguredFeature {

    private final Block ore;

    public OreFeature(Block ore) {
        this.ore = ore;
    }

    @Override
    public void generate(int startX, int startY, World world) {

    }

    @Override
    public int rarity() {
        return 0;
    }
}
