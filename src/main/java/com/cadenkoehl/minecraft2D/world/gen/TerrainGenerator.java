package com.cadenkoehl.minecraft2D.world.gen;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class TerrainGenerator {

    private final World world;

    public TerrainGenerator(World world) {
        this.world = world;
    }

    public void generate() {
        if (!world.isCaveWorld()) {
            for (int x = 0; x < 10; x++) {
                Block surfaceBlock = world.getSurfaceBlock();
                Vec2d surfaceLocation = new Vec2d(x, world.getGroundHeight());

                surfaceBlock.setWorld(world);
                surfaceBlock.changePosWithoutRender(surfaceLocation);

                world.setBlock(surfaceBlock);
            }
        }
    }
}