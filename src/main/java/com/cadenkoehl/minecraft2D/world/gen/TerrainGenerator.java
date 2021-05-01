package com.cadenkoehl.minecraft2D.world.gen;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.display.GameFrame;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.util.LogLevel;
import com.cadenkoehl.minecraft2D.util.Logger;
import com.cadenkoehl.minecraft2D.world.World;
import com.cadenkoehl.minecraft2D.world.biome.Biome;
import com.cadenkoehl.minecraft2D.world.gen.feature.ConfiguredFeature;

import java.util.ArrayList;
import java.util.List;

import static com.cadenkoehl.minecraft2D.util.Util.random;

public abstract class TerrainGenerator {

    public abstract World getWorld();
    public abstract Block getDefaultBlock();
    public abstract List<Biome> getBiomes();
    public abstract boolean shouldGenerateCaves();
    public abstract int getDepth();

    public final int surfaceHeight = GameFrame.HEIGHT / Tile.BLOCK_SIZE - 2;
    private final World world = this.getWorld();
    private int chunks;
    private Biome currentBiome;
    private int chunksInCurrentBiome;

    public void genSpawn() {
        currentBiome = getBiomes().get(0);
        Logger.log(LogLevel.INFO, "Generating " + world.getDisplayName() + "...");
        genChunk(0);
        Logger.log(LogLevel.INFO, "Done!");
    }

    public void nextChunk() {
        genChunk(chunks);
    }

    public void genChunk(int chunkX) {
        chunksInCurrentBiome++;
        nextBiome();
        chunkX = chunkX * 16;

        for(int x = chunkX; x < chunkX + 16; x++) {

            genBedrock(x);

            //features/structures
            if(x % 16 == 0) {
                genFeatures(x);
            }

            //general terrain
            gen(currentBiome.getSurfaceBlock(), x, surfaceHeight);
            gen(currentBiome.getSecondarySurfaceBlock(), x, surfaceHeight + 1);
            genDefaultBlocks(x);
        }
        gen(currentBiome.getSecondarySurfaceBlock(), chunkX + 16, surfaceHeight + 1);
        chunks++;
        world.width = world.width + 16;
    }

    public void genFeatures(int x) {
        if(currentBiome.getFeatures() == null) return;

        int xOffset = 2;
        for(ConfiguredFeature feature : currentBiome.getFeatures()) {
            if(random(feature.rarity() + 1)) {
                xOffset = xOffset + 5;
                if(xOffset > 16) return;
                feature.generate(x + xOffset, surfaceHeight, world);
            }
        }
    }

    private void nextBiome() {

        if(chunksInCurrentBiome < 4) return;
        if(!random(2)) return;

        for(Biome biome : this.getBiomes()) {
            if(biome == currentBiome) continue;
            if(random(biome.rarity() + 1)) {
                currentBiome = biome;
                chunksInCurrentBiome = 0;
                return;
            }
        }
        chunksInCurrentBiome = 0;
        currentBiome = this.getBiomes().get(0);
    }

    private void genDefaultBlocks(int x) {
        for(int y = 2; y < this.getDepth(); y++) {
            if(random(2) && y < this.getDepth() / 2) gen(currentBiome.getSecondarySurfaceBlock(), x, surfaceHeight + y);
            else gen(this.getDefaultBlock(), x, surfaceHeight + y);
        }
    }

    private void genBedrock(int x) {
        gen(Blocks.BEDROCK, x, surfaceHeight + getDepth());
        if(random(2)) gen(Blocks.BEDROCK, x, surfaceHeight + getDepth() - 1);
        if(random(3)) gen(Blocks.BEDROCK, x, surfaceHeight + getDepth() - 2);
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

    private void replace(Block block, int x, int y, boolean canCollide) {
        Block blockCopy = block.copy();
        blockCopy.setCanCollide(canCollide);
        blockCopy.setPos(new Vec2d(x, y));
        world.replaceBlock(blockCopy);
    }

    public int getChunks() {
        return chunks;
    }

    public static class Builder {

        private final World world;
        private Block defaultBlock;
        private final List<Biome> biomes;
        private boolean caves;
        private int depth;

        public Builder(World world) {
            this.world = world;
            biomes = new ArrayList<>();
        }

        public Builder defaultBlock(Block defaultBlock) {
            this.defaultBlock = defaultBlock;
            return this;
        }

        public Builder addBiome(Biome biome) {
            biomes.add(biome);
            return this;
        }

        public Builder addCarvers() {
            this.caves = true;
            return this;
        }

        public Builder depth(int depth) {
            this.depth = depth;
            return this;
        }

        public TerrainGenerator build() {
            return new TerrainGenerator() {
                @Override
                public World getWorld() {
                    return world;
                }

                @Override
                public Block getDefaultBlock() {
                    return defaultBlock;
                }

                @Override
                public List<Biome> getBiomes() {
                    return biomes;
                }
                
                @Override
                public boolean shouldGenerateCaves() {
                    return caves;
                }

                @Override
                public int getDepth() {
                    return depth;
                }
            };
        }
    }
}