package com.cadenkoehl.minecraft2D.world.gen.feature;

import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class TreeFeature implements ConfiguredFeature {

    @Override
    public void generate(int startX, int startY, World world) {
        world.setBlock(Blocks.OAK_LOG, new Vec2d(startX, startY - 1), false);
        world.setBlock(Blocks.OAK_LOG, new Vec2d(startX, startY - 2), false);
        world.setBlock(Blocks.OAK_LOG, new Vec2d(startX, startY - 3), false);
        world.setBlock(Blocks.LEAF_BLOCK, new Vec2d(startX, startY - 4), false);
        world.setBlock(Blocks.LEAF_BLOCK, new Vec2d(startX + 1, startY - 4), false);
        world.setBlock(Blocks.LEAF_BLOCK, new Vec2d(startX + 1, startY - 5));
        world.setBlock(Blocks.LEAF_BLOCK, new Vec2d(startX - 1, startY - 5), false);
        world.setBlock(Blocks.LEAF_BLOCK, new Vec2d(startX + 2, startY - 4));
        world.setBlock(Blocks.LEAF_BLOCK, new Vec2d(startX - 1, startY - 4));
        world.setBlock(Blocks.LEAF_BLOCK, new Vec2d(startX - 2, startY - 4));
        world.setBlock(Blocks.LEAF_BLOCK, new Vec2d(startX, startY - 5));
        world.setBlock(Blocks.LEAF_BLOCK, new Vec2d(startX, startY - 6), false);
    }

    @Override
    public int rarity() {
        return 0;
    }
}
