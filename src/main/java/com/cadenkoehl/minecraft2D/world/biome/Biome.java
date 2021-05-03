package com.cadenkoehl.minecraft2D.world.biome;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.world.gen.feature.ConfiguredFeature;

import java.util.List;

public interface Biome {

    Block getSurfaceBlock();
    Block getSecondarySurfaceBlock();
    List<ConfiguredFeature> getFeatures();
    int rarity();

    /**
     *
     * @return how high above surface height terrain will try to generate
     */
    default int height() {
        return 0;
    }
}
