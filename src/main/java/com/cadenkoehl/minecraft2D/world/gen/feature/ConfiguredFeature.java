package com.cadenkoehl.minecraft2D.world.gen.feature;

import com.cadenkoehl.minecraft2D.world.World;

public interface ConfiguredFeature {

    void generate(int startX, int startY, World world);
    int rarity();

}
