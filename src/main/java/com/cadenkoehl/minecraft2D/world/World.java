package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.Game;
import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.display.GameFrame;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.entities.mob.LivingEntity;
import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.util.LogLevel;
import com.cadenkoehl.minecraft2D.util.Logger;
import com.cadenkoehl.minecraft2D.util.Util;
import com.cadenkoehl.minecraft2D.world.gen.TerrainGenerator;
import com.cadenkoehl.minecraft2D.world.gen.feature.ConfiguredFeature;
import com.cadenkoehl.minecraft2D.world.gen.feature.NetherPortalFeature;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class World {

    public final List<Chunk> chunks;
    private final List<Tile> entities;
    private final Sun sun;
    public final TerrainGenerator generator;
    public int width;
    public int time;
    public int days;
    private static final int sunTravelLength = GameFrame.WIDTH * 2;
    private static final int dayLength = 200;
    private final Random random;
    public Color skyColor;
    private final long seed;
    public final List<List<Vec2d>> netherPortals;

    public World(long seed) {
        this.netherPortals = new ArrayList<>();
        this.seed = seed;
        this.random = new Random(seed);
        this.chunks = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.generator = this.getGenerator();
        this.sun = new Sun(0, 200, 100, 100);
        time = 0;
        days = 1;
        this.genSpawnTerrain();
        if(this.hasDaylightCycle()) {
            Util.scheduleTask(this::updateDaylightCycle, dayLength);
        }
        skyColor = this.getSkyColor();
        updateSkyColor();
        new Thread(this::startChunkGen, "Chunk gen thread").start();
    }

    public void startChunkGen() {
        while(true) {
            new Scanner(System.in);
            if(Game.getPlayer() != null) {
                if(Game.getPlayer().pos.x > this.width - 12) {
                    generator.nextChunk();
                }
            }
        }
    }

    private void updateSkyColor() {

        if(time > (sunTravelLength / 2) - (sunTravelLength / 6) && skyColor.getRed() > 0 && skyColor.getGreen() > 0 && skyColor.getBlue() > 0) {
            skyColor = new Color(skyColor.getRed() - 1, skyColor.getGreen() - 1, skyColor.getBlue() - 1);
        }
        if(time < sunTravelLength / 4 && skyColor.getRed() < 255 && skyColor.getGreen() < 255 && skyColor.getBlue() < 255) {
            skyColor = new Color(skyColor.getRed() + 1, skyColor.getGreen() + 1, skyColor.getBlue() + 1);
        }
    }

    public abstract String getDisplayName();
    public abstract Color getSkyColor();
    public abstract TerrainGenerator getGenerator();
    public abstract List<ConfiguredFeature> getFeatures();
    public abstract boolean hasDaylightCycle();

    public void tick() {
        for(Chunk chunk : chunks) {
            chunk.tick();
        }
        for(Tile entity : new ArrayList<>(entities)) {
            if(entity instanceof LivingEntity) {
                if(!((LivingEntity) entity).isAlive()) entities.remove(entity);
            }
            entity.tick();
        }
    }

    public void addChunk(Chunk chunk) {
        chunks.add(chunk);
    }

    private void updateDaylightCycle() {
        updateSkyColor();
        time++;
        if(time > sunTravelLength) {
            time = -100;
            days++;
        }
        sun.pos.x = time;
        Util.scheduleTask(this::updateDaylightCycle, dayLength);
    }

    public boolean isNight() {
        return time > sunTravelLength / 2;
    }

    public boolean isDay() {
        return !isNight() && this.hasDaylightCycle();
    }

    public void genSpawnTerrain() {
        generator.genSpawn();
    }

    public void spawnEntity(Tile entity) {
        entities.add(entity);
        entity.render();
        entity.updateGraphics();
    }

    public Tile getEntity(Vec2d pos) {
        for(Tile entity : entities) {
            if((entity.pos.x == pos.x || entity.pos.x + 1 == pos.x) && entity.pos.y == pos.y) return entity;
        }
        return null;
    }

    public List<Tile> getEntities() {
        return entities;
    }

    /**
     * @return a block from a given position
     */
    public Block getBlock(Vec2d pos) {
        if(pos.x / 16 >= chunks.size()) return null;

        Chunk chunk = chunks.get(pos.x / 16);
        return chunk.getBlock(pos);
    }

    public void spawnPortal(int x, int y) {
        new NetherPortalFeature().generate(x, y, this);
        for(List<Vec2d> portal : netherPortals) {
            for(Vec2d pos : portal) {
                if(pos.x == x && pos.y == y) {
                    lightPortal(portal);
                    return;
                }
            }
        }
        Logger.log(LogLevel.WARN, "Failed to light portal at " + x + " " + y);
    }

    public void lightPortal(List<Vec2d> portal) {
        for(Vec2d pos : portal) {
            Block copy = Blocks.NETHER_PORTAL.copy();
            copy.setPos(pos);
            replaceBlock(copy, false);
        }
    }

    /**
     * @return a lowercase version of this world's name with spaces replaced by underscores and
     */
    public String getName() {
        return this.getDisplayName().toLowerCase().replace(" ", "_");
    }

    /**
     * @return a block from a given location
     */
    public Block getBlock(int x, int y) {
        return this.getBlock(new Vec2d(x, y));
    }

    public boolean setBlock(Block block, boolean canCollide) {
        block = block.copy();
        block.setWorld(this);
        block.setCanCollide(canCollide);
        if(block.pos == null) throw new IllegalStateException("Block has no state yet!");
        if(this.getBlock(block.pos.x, block.pos.y) == null) {
            if(block.pos.x / 16 >= chunks.size()) return false;

            Chunk chunk = chunks.get(block.pos.x / 16);
            return chunk.setBlock(block);
        }
        return false;
    }

    /**
     * @param chance The probability that this method will return true
     * @return true or false
     */
    public boolean random(int chance) {
        return random.nextInt(Math.abs(chance)) == 0;
    }

    public Random getRandom() {
        return random;
    }

    public long getSeed() {
        return seed;
    }

    public Chunk getChunk(int posX) {
        if(posX / 16 >= chunks.size()) return null;

        return chunks.get(posX / 16);
    }

    public void replaceBlock(Block block, boolean canCollide) {

        Chunk chunk = this.getChunk(block.pos.x);
        if(chunk == null) return;

        chunk.removeBlock(getBlock(block.pos));
        setBlock(block, canCollide);
    }

    public void replaceBlock(Block block) {
        replaceBlock(block, true);
    }

    public boolean setBlock(Block block) {
        return setBlock(block, true);
    }

    public boolean setBlock(Block block, Vec2d pos) {
        block.setPos(pos);
        return this.setBlock(block);
    }

    public boolean setBlock(Block block, Vec2d pos, boolean canCollide) {
        block.setPos(pos);
        return this.setBlock(block, canCollide);
    }

    public Block breakBlock(PlayerEntity player, Vec2d pos) {

        Block brokeBlock = null;

        Chunk chunk = this.getChunk(pos.x);
        if(chunk == null) return null;

        for(Block block : chunk.getBlocks()) {
            if(block.pos.x == pos.x && block.pos.y == pos.y) {
                brokeBlock = block;
            }
        }
        if(brokeBlock == null) return null;
        if(!brokeBlock.canBeMined()) return null;
        if(brokeBlock.minedTicks < brokeBlock.getBreakSpeed() * 9) {
            brokeBlock.miner = player;
            return null;
        }
        brokeBlock.mine();
        return brokeBlock;
    }

    public void render() {
        try {
            if(this.hasDaylightCycle()) sun.render();
            renderBlocks();
            renderEntities();
        }
        catch (ConcurrentModificationException ex) {
            render();
        }
    }
    private void renderBlocks() {
        Chunk chunk = Game.getPlayer().getChunk();
        Chunk chunk2 = this.getChunk(Game.getPlayer().pos.x - 16);
        Chunk chunk3 = this.getChunk(Game.getPlayer().pos.x + 16);

        if(chunk3 != null) {
            chunk3.render();
        }
        if(chunk2 != null) {
            chunk2.render();
        }
        if(chunk != null) {
            chunk.render();
        }
    }
    private void renderEntities() {
        for(Tile entity : entities) {
            entity.render();
        }
    }
}
