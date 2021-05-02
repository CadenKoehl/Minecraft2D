package com.cadenkoehl.minecraft2D.world.gen.feature;

import com.cadenkoehl.minecraft2D.block.FluidBlock;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;
import com.cadenkoehl.minecraft2D.world.gen.feature.ConfiguredFeature;

public class LakeFeature implements ConfiguredFeature {

    private final FluidBlock fluid;

    public LakeFeature(FluidBlock fluid) {
        this.fluid = fluid;
    }

    @Override
    public void generate(int startX, int startY, World world) {
        world.setBlock(fluid, new Vec2d(startX, startY), false);
        world.setBlock(fluid, new Vec2d(startX - 1, startY), false);
        world.setBlock(fluid, new Vec2d(startX - 2, startY), false);
        world.setBlock(fluid, new Vec2d(startX - 3, startY), false);
        world.setBlock(fluid, new Vec2d(startX + 1, startY), false);
        world.setBlock(fluid, new Vec2d(startX + 2, startY), false);
        world.setBlock(fluid, new Vec2d(startX + 3, startY), false);

        world.setBlock(fluid, new Vec2d(startX, startY + 1), false);
        world.setBlock(fluid, new Vec2d(startX + 1, startY + 1), false);
        world.setBlock(fluid, new Vec2d(startX + 2, startY + 1), false);
        world.setBlock(fluid, new Vec2d(startX - 1, startY + 1), false);
        world.setBlock(fluid, new Vec2d(startX - 2, startY + 1), false);
    }

    @Override
    public int rarity() {
        return 4;
    }
}