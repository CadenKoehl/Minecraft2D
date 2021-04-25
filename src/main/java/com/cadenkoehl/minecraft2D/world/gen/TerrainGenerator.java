package com.cadenkoehl.minecraft2D.world.gen;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.display.GameFrame;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public abstract class TerrainGenerator {

    abstract World getWorld();
    abstract Block getSurfaceBlock();
    abstract Block getSecondarySurfaceBlock();
    abstract Block getDefaultBlock();
    abstract boolean shouldGenerateTrees();

    private final int surfaceHeight = GameFrame.HEIGHT / Tile.BLOCK_SIZE - 2;
    private final World world = this.getWorld();

    public void generate() {

        for(int x = 0; x < GameFrame.WIDTH / Block.BLOCK_SIZE + 500; x++) {

            //surface
            gen(this.getSurfaceBlock(), x, surfaceHeight);
            gen(this.getSecondarySurfaceBlock(), x, surfaceHeight + 1);
            gen(this.getDefaultBlock(), x, surfaceHeight + 2);

            //trees
            if(this.shouldGenerateTrees()) {
                if(x % 10 == 0) {
                    if(random(3)) genTree(x, surfaceHeight);
                }
            }
        }
    }

    private void genTree(int x, int y) {

        gen(Blocks.LOG, x, y, false);
        gen(Blocks.LOG, x, y - 1, false);
        gen(Blocks.LOG, x, y - 2, false);
        gen(Blocks.LOG, x, y - 3, false);
        gen(Blocks.LEAF_BLOCK, x, y - 4, false);
        gen(Blocks.LEAF_BLOCK, x + 1, y - 4, false);
        gen(Blocks.LEAF_BLOCK, x + 1, y - 5);
        gen(Blocks.LEAF_BLOCK, x - 1, y - 5, false);
        gen(Blocks.LEAF_BLOCK, x + 2, y - 4);
        gen(Blocks.LEAF_BLOCK, x - 1, y - 4);
        gen(Blocks.LEAF_BLOCK, x - 2, y - 4);
        gen(Blocks.LEAF_BLOCK, x, y - 5);
        gen(Blocks.LEAF_BLOCK, x, y - 6, false);
    }

    private void gen(Block block, int x, int y, boolean canCollide) {
        World world = this.getWorld();
        Block blockCopy = block.copy();
        blockCopy.setCanCollide(canCollide);
        world.setBlock(blockCopy, new Vec2d(x, y));
    }

    private void gen(Block block, int x, int y) {
        gen(block, x, y, true);
    }

    /**
     * @param chance The probability that this method will return true
     * @return true or false
     */
    private boolean random(int chance) {
        int random = (int) Math.round(Math.random() * Math.abs(chance));
        return random == 1;
    }

    public static class Builder {

        private final World world;
        private Block surfaceBlock;
        private Block secondarySurfaceBlock;
        private Block defaultBlock;
        private boolean trees;

        public Builder(World world) {
            this.world = world;
        }

        public Builder surfaceBlock(Block surfaceBlock) {
            this.surfaceBlock = surfaceBlock;
            return this;
        }

        public Builder secondarySurfaceBlock(Block secondarySurfaceBlock) {
            this.secondarySurfaceBlock = secondarySurfaceBlock;
            return this;
        }

        public Builder defaultBlock(Block defaultBlock) {
            this.defaultBlock = defaultBlock;
            return this;
        }

        public Builder addTrees() {
            this.trees = true;
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
                Block getSecondarySurfaceBlock() {
                    return secondarySurfaceBlock;
                }

                @Override
                public Block getDefaultBlock() {
                    return defaultBlock;
                }

                @Override
                public boolean shouldGenerateTrees() {
                    return trees;
                }
            };
        }
    }
}