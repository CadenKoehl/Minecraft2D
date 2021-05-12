package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.client.GameClient;
import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.BlockState;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.display.GameFrame;
import com.cadenkoehl.minecraft2D.entities.EntityType;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.entities.Entity;
import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.util.Util;
import com.cadenkoehl.minecraft2D.world.gen.TerrainGenerator;
import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.ListTag;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public abstract class World {

    public final List<Chunk> chunksPos;
    public final List<Chunk> chunksNeg;

    private final Realm realm;
    private final List<Entity> entities;
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

    public World(Realm realm) {
        this.realm = realm;
        this.seed = realm.getSeed();
        this.random = new Random(seed);
        this.chunksPos = new ArrayList<>();
        this.chunksNeg = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.generator = this.getGenerator();
        this.sun = new Sun(0, 200, 100, 100);
        time = 0;
        days = 1;
        if(this.hasDaylightCycle()) {
            Util.scheduleTask(this::updateDaylightCycle, dayLength);
        }
        skyColor = this.getSkyColor();
        updateSkyColor();
        new Thread(this::startChunkGen, "Chunk gen thread").start();
    }

    public Realm getRealm() {
        return realm;
    }

    public void startChunkGen() {
        while(true) {
            new Scanner(System.in);
            if(GameClient.getPlayer() != null) {
                if(GameClient.getPlayer().pos.x > this.width - 12) {
                    generator.nextChunk();
                }
            }
        }
    }

    public void saveChunks() {

        File dir = new File("saves/" + this.getRealm().getName() + "/" + this.getName() + "/chunks/");
        dir.mkdirs();

        for (int i = 0, chunksSize = chunksPos.size(); i < chunksSize; i++) {
            Chunk chunk = chunksPos.get(i);
            CompoundTag chunkTag = new CompoundTag();
            for (BlockState block : chunk.getBlocks()) {
                chunkTag.put(block.pos.x + ":" + block.pos.y, block.getTag());
            }
            ListTag<CompoundTag> entityTag = new ListTag<>(CompoundTag.class);
            for(Entity entity : entities) {
                if(entity.getName().equals("player")) continue;
                if(entity.getChunkX() == i) {
                    entityTag.add(entity.getCompoundTag());
                }
            }
            chunkTag.put("Entities", entityTag);
            try {
                NBTUtil.write(new NamedTag("Data", chunkTag), new File(dir, "chunk_" + i + ".dat"));
            } catch (Exception e) {
                e.printStackTrace();
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
    public abstract boolean hasDaylightCycle();

    public void tick() {
        for(Chunk chunk : chunksPos) {
            chunk.tick();
        }
        for(Tile entity : new ArrayList<>(entities)) {
            if(entity instanceof Entity) {
                if(!((Entity) entity).isAlive()) entities.remove(entity);
            }
            entity.tick();
        }
    }

    public void addChunk(Chunk chunk) {
        chunksPos.add(chunk);
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

    public void spawnEntity(Entity entity) {
        entities.add(entity);
        entity.render();
        entity.updateGraphics();
    }

    public <E extends Entity> E spawnEntity(EntityType<E> entityType, Vec2d pos) {
        Entity entity = entityType.createEntity();
        entity.setPos(pos);
        entity.setWorld(this);
        entity.postSpawn();
        return (E) entity;
    }

    public void removeEntity(Tile entity) {
        entities.remove(entity);
    }

    public Tile getEntity(Vec2d pos) {
        for(Tile entity : entities) {
            if((entity.pos.x == pos.x || entity.pos.x + 1 == pos.x) && entity.pos.y == pos.y) return entity;
        }
        return null;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * @return a block from a given position
     */
    public BlockState getBlock(Vec2d pos) {
        Chunk chunk = this.getChunk(pos.x);
        if(chunk == null) return null;

        return chunk.getBlock(new Vec2d(pos.x, pos.y));
    }

    public void lightPortal(List<Vec2d> portal) {
        for(Vec2d pos : portal) {
            replaceBlock(new BlockState(Blocks.NETHER_PORTAL, pos, this), false);
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
    public BlockState getBlock(int x, int y) {
        return this.getBlock(new Vec2d(x, y));
    }

    public boolean setBlock(BlockState block, boolean canCollide) {
        block.setCanCollide(canCollide);
        if(this.getBlock(block.pos.x, block.pos.y) == null) {
            if(block.pos.x / 16 >= chunksPos.size()) return false;

            Chunk chunk = this.getChunk(block.pos.x);
            if(chunk == null) return false;
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
        int chunkX = posX / 16;

        if(chunkX >= chunksPos.size() && chunkX <= chunksNeg.size()) return null;

        if(chunkX >= 0) {
            if(chunkX >= chunksPos.size()) return null;
            return chunksPos.get(chunkX);
        }
        else {
            if(-chunkX >= chunksNeg.size()) return null;
            return chunksNeg.get(-chunkX);
        }
    }

    public void replaceBlock(BlockState block, boolean canCollide) {

        Chunk chunk = this.getChunk(block.pos.x);
        if(chunk == null) return;

        chunk.removeBlock(getBlock(block.pos));
        setBlock(block, canCollide);
    }

    public void replaceBlock(BlockState block) {
        replaceBlock(block, true);
    }

    public boolean setBlock(BlockState block) {
        return setBlock(block, block.canCollide());
    }

    public boolean setBlock(Block block, Vec2d pos) {
        return this.setBlock(new BlockState(block, pos, this));
    }

    public boolean setBlock(Block block, Vec2d pos, boolean canCollide) {
        return this.setBlock(new BlockState(block, pos, this), canCollide);
    }

    public BlockState breakBlock(PlayerEntity player, Vec2d pos) {

        BlockState brokeBlock = null;

        Chunk chunk = this.getChunk(pos.x);
        if(chunk == null) return null;

        for(BlockState block : chunk.getBlocks()) {
            if(block.pos.x == pos.x && block.pos.y == pos.y) {
                brokeBlock = block;
            }
        }
        if(brokeBlock == null) return null;
        if(!brokeBlock.getBlock().canBeMined()) return null;
        if(brokeBlock.minedTicks < brokeBlock.getBlock().getBreakSpeed() * 9) {
            brokeBlock.miner = player;
            player.breakingBlock = brokeBlock;
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
        Chunk chunk = GameClient.getPlayer().getChunk();
        Chunk chunk2 = this.getChunk(GameClient.getPlayer().pos.x - 16);
        Chunk chunk3 = this.getChunk(GameClient.getPlayer().pos.x + 16);

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
