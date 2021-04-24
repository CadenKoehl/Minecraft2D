package com.cadenkoehl.minecraft2D.world.gen;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.display.GameFrame;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public interface TerrainGenerator {

    World getWorld();
    Block getSurfaceBlock();
    Block getDefaultBlock();

    default void generate() {

        final int surfaceHeight = GameFrame.HEIGHT / Tile.BLOCK_SIZE - 2;

        World world = this.getWorld();
        for(int x = 0; x < GameFrame.WIDTH / Block.BLOCK_SIZE + 1; x++) {
            world.setBlock(this.getSurfaceBlock(), new Vec2d(x, surfaceHeight));
            world.setBlock(this.getDefaultBlock(), new Vec2d(x, surfaceHeight + 1));
        }
    }

    class Builder {

        private final World world;
        private Block surfaceBlock;
        private Block defaultBlock;

        public Builder(World world) {
            this.world = world;
        }

        public Builder surfaceBlock(Block surfaceBlock) {
            this.surfaceBlock = surfaceBlock;
            return this;
        }

        public Builder defaultBlock(Block defaultBlock) {
            this.defaultBlock = defaultBlock;
            return this;
        }

        public TerrainGenerator build() {
            return new TerrainGenerator() {
                @Override
                public World getWorld() {
                    return world;
                }
                @Override
                public Block getSurfaceBlock() {
                    return surfaceBlock;
                }

                @Override
                public Block getDefaultBlock() {
                    return defaultBlock;
                }
            };
        }
    }
}