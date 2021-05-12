package com.cadenkoehl.minecraft2D.world.gen;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.BlockState;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.display.GameFrame;
import com.cadenkoehl.minecraft2D.entities.Entity;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.Chunk;
import com.cadenkoehl.minecraft2D.world.Realm;
import com.cadenkoehl.minecraft2D.world.World;
import com.cadenkoehl.minecraft2D.world.biome.Biome;
import com.cadenkoehl.minecraft2D.world.gen.feature.ConfiguredFeature;
import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.ListTag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class TerrainGenerator {

    public abstract World getWorld();
    public abstract Block getDefaultBlock();
    public abstract List<Biome> getBiomes();
    public abstract boolean shouldGenerateCaves();
    public abstract int getDepth();

    public final int surfaceHeight = GameFrame.HEIGHT / Tile.BLOCK_SIZE - 2;
    private final World world = this.getWorld();
    private final Realm realm = world.getRealm();
    private int chunks;
    private Biome biome;
    private int chunksInCurrentBiome;
    private int mountain;
    private boolean upMountain;

    public TerrainGenerator() {

        int i = world.getRandom().nextInt(getBiomes().size());

        biome = getBiomes().get(i);
    }

    public void nextChunk() {
        genChunk(chunks);
    }

    public void genChunk(int chunkX) {
        world.addChunk(new Chunk());

        File file = new File("saves/" + realm.getName() + "/" + world.getName() + "/chunks/chunk_" + chunkX + ".dat");

        chunkX = chunkX * 16;
        world.width = world.width + 16;
        chunks++;

        if(file.exists()) {
            CompoundTag tag;

            try {
                tag = (CompoundTag) NBTUtil.read(file).getTag();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            tag.forEach(entry -> {
                if(entry.getKey().equals("Entities")) {
                    ListTag<CompoundTag> entities = new ListTag<>(CompoundTag.class);
                    entities.forEach(entityTag -> {
                        Entity entity = Entity.loadFromTag(entityTag);
                        world.spawnEntity(entity);
                    });
                    return;
                }
                String[] pos = entry.getKey().split(":");
                world.setBlock(new BlockState((CompoundTag) entry.getValue(), new Vec2d(Integer.parseInt(pos[0]), Integer.parseInt(pos[1])), world));
            });
            return;
        }

        Collections.shuffle(getBiomes());
        nextBiome();
        chunksInCurrentBiome++;

        for(int x = chunkX; x < chunkX + 16; x++) {

            genBedrock(x);

            //features/structures
            if(x % 16 == 0) {
                genFeatures(x);
            }

            //general terrain
            genSurfaceTerrain(x);
            genDefaultBlocks(x);
        }
    }

    public void genFeatures(int x) {
        if(biome.getFeatures() == null) return;

        int xOffset = 2;
        for(ConfiguredFeature feature : biome.getFeatures()) {
            if(world.random(feature.rarity() + 1)) {
                xOffset = xOffset + 5;
                if(xOffset > 16) return;
                feature.generate(x + xOffset, surfaceHeight, world);
            }
        }
    }

    private void nextBiome() {

        if(chunksInCurrentBiome < 4) return;
        if(!world.random(2)) return;

        int i = world.getRandom().nextInt(this.getBiomes().size());

        Biome newBiome = this.getBiomes().get(i);

        if(this.biome == newBiome) return;

        if(world.random(biome.rarity() + 1)) {
            this.biome = newBiome;
            chunksInCurrentBiome = 0;
            return;
        }
        chunksInCurrentBiome = 0;
        biome = this.getBiomes().get(0);
    }

    private void genSurfaceTerrain(int x) {
        if(biome.height() < 1) {
            gen(biome.getSurfaceBlock(), x, surfaceHeight);
            gen(biome.getSecondarySurfaceBlock(), x, surfaceHeight + 1);
            return;
        }

        gen(biome.getSecondarySurfaceBlock(), x, surfaceHeight + 1);

        for(int y = surfaceHeight; y > surfaceHeight - mountain; y--) {
            gen(biome.getSecondarySurfaceBlock(), x, y);
        }
        gen(biome.getSurfaceBlock(), x, surfaceHeight - mountain);

        updateMountains();
    }

    private void updateMountains() {

        if(world.random(2)) {
            if(upMountain) mountain++;
            else mountain--;
        }

        if(biome.height() == 0) upMountain = false;

        else if(mountain < 0) upMountain = true;

        else if(mountain > biome.height()) {
            upMountain = false;
        }
    }

    private void genDefaultBlocks(int x) {
        for(int y = 2; y < this.getDepth(); y++) {
            if(world.random(2) && y < this.getDepth() / 2) gen(biome.getSecondarySurfaceBlock(), x, surfaceHeight + y);
            else gen(this.getDefaultBlock(), x, surfaceHeight + y);
        }
    }

    private void genBedrock(int x) {
        gen(Blocks.BEDROCK, x, surfaceHeight + getDepth());
        if(world.random(2)) gen(Blocks.BEDROCK, x, surfaceHeight + getDepth() - 1);
        if(world.random(3)) gen(Blocks.BEDROCK, x, surfaceHeight + getDepth() - 2);
    }

    private void gen(Block block, int x, int y, boolean canCollide) {
        World world = this.getWorld();
        world.setBlock(block, new Vec2d(x, y), canCollide);
    }

    private void gen(Block block, int x, int y) {
        gen(block, x, y, true);
    }

    private void replace(Block block, int x, int y, boolean canCollide) {
        world.replaceBlock(new BlockState(block, new Vec2d(x, y), world), canCollide);
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