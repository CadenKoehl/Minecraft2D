package com.cadenkoehl.minecraft2D.world.gen.feature;

import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.util.Util;
import com.cadenkoehl.minecraft2D.world.World;

public class CactusFeature implements ConfiguredFeature {

    @Override
    public void generate(int startX, int startY, World world) {
        world.setBlock(Blocks.CACTUS, new Vec2d(startX, startY - 1), false);
        world.setBlock(Blocks.CACTUS, new Vec2d(startX, startY - 2), false);
        if(Util.random(2)) {
            world.setBlock(Blocks.CACTUS, new Vec2d(startX, startY - 3), false);
        }
    }

    @Override
    public int rarity() {
        return 0;
    }
}
