package com.cadenkoehl.minecraft2D.world.gen.feature;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class OreFeature implements ConfiguredFeature {

    private final Block ore;
    private final int rarity;
    private final int minSize;
    private final int maxSize;

    public OreFeature(Block ore, int rarity, int minSize, int maxSize) {
        this.ore = ore;
        this.rarity = rarity;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @Override
    public void generate(int startX, int startY, World world) {

        int maxY = world.getGenerator().getDepth();
        int minY = startY;


        int oreSize = world.getRandom().nextInt(maxSize - minSize + 1) + minSize;
        startY = world.getRandom().nextInt(maxY - startY + 1) + startY;

        for(int x = 0; x < oreSize / 3; x++) {
            for(int y = 0; y < oreSize / 4; y++) {

                if(startY + y <= minY) return;

                world.setBlock(ore, new Vec2d(startX + x, startY + y));
                world.setBlock(ore, new Vec2d(startX - x, startY - y));

                world.setBlock(ore, new Vec2d(startX - x, startY + y));
                world.setBlock(ore, new Vec2d(startX + x, startY - y));
            }
        }
    }

    @Override
    public int rarity() {
        return rarity;
    }
}
