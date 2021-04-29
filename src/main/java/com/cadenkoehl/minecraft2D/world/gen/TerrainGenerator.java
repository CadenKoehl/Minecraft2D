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

public abstract class TerrainGenerator {

    abstract World getWorld();
    abstract Block getDefaultBlock();
    abstract List<Biome> getBiomes();
    abstract boolean shouldGenerateCaves();

    public final int surfaceHeight = GameFrame.HEIGHT / Tile.BLOCK_SIZE - 2;
    private final World world = this.getWorld();
    private int chunks;
    private Biome currentBiome;
    private int chunksInCurrentBiome;

    public void genSpawn() {
        currentBiome = getBiomes().get(0);
        Logger.log(LogLevel.INFO, "Generating level...");
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
            //surface
            gen(currentBiome.getSurfaceBlock(), x, surfaceHeight);
            gen(currentBiome.getSecondarySurfaceBlock(), x, surfaceHeight + 1);
            genDefaultBlocks(x);

            if(x % 16 == 0) {
                genFeatures(x);
            }
        }
        chunks++;
        world.width = world.width + 16;
    }

    public void genFeatures(int x) {
        if(currentBiome.getFeatures() == null) return;

        for(ConfiguredFeature feature : currentBiome.getFeatures()) {
            if(random(feature.rarity() + 1)) {
                feature.generate(x, surfaceHeight, world);
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
        for(int y = 2; y < 10; y++) {
            gen(this.getDefaultBlock(), x, surfaceHeight + y);
        }
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

    private void cave(int x) {
        replace(Blocks.BACKGROUND_STONE, x, surfaceHeight + 4, false);
        replace(Blocks.BACKGROUND_STONE, x, surfaceHeight + 5, false);
        replace(Blocks.BACKGROUND_STONE, x, surfaceHeight + 6, false);
    }

    private void replace(Block block, int x, int y, boolean canCollide) {
        Block blockCopy = block.copy();
        blockCopy.setCanCollide(canCollide);
        blockCopy.setPos(new Vec2d(x, y));
        world.replaceBlock(blockCopy);
    }

    /**
     * @param chance The probability that this method will return true
     * @return true or false
     */
    private boolean random(int chance) {
        int random = (int) Math.round(Math.random() * Math.abs(chance));
        return random == 1;
    }

    public int getChunks() {
        return chunks;
    }

    public static class Builder {

        private final World world;
        private Block defaultBlock;
        private final List<Biome> biomes;
        private boolean caves;

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
                List<Biome> getBiomes() {
                    return biomes;
                }
                
                @Override
                boolean shouldGenerateCaves() {
                    return caves;
                }
            };
        }
    }
}