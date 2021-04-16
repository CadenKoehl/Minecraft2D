package com.cadenkoehl.minecraft2D.world.gen;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.physics.Location;
import com.cadenkoehl.minecraft2D.world.World;

public class TerrainGenerator {

    private final World world;

    public TerrainGenerator(World world) {
        this.world = world;
    }

    public void generate() {
        if(!world.isCaveWorld()) {
            for(int x = 0; x < 100; x++) {
                Block surfaceBlock = world.getSurfaceBlock();
                Location surfaceLocation = new Location(x * surfaceBlock.getWidth(), world.getGroundHeight());
                world.blocks.put(surfaceLocation, surfaceBlock);
            }
        }
    }
}