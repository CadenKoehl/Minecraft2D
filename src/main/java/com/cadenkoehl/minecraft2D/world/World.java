package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.Game;
import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.block.FluidBlock;
import com.cadenkoehl.minecraft2D.display.GameFrame;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.entities.mob.LivingEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.util.Util;
import com.cadenkoehl.minecraft2D.world.gen.TerrainGenerator;
import com.cadenkoehl.minecraft2D.world.gen.feature.ConfiguredFeature;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class World {

    private final List<Block> blocks;
    private final List<Tile> entities;
    private final Sun sun;
    public final TerrainGenerator generator;
    public int width;
    public int time;
    public int days;
    private static final int sunTravelLength = GameFrame.WIDTH * 2;
    private static final int dayLength = 200;
    public Color skyColor;

    public World() {
        this.blocks = new ArrayList<>();
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
        if(Game.getPlayer().pos.x > this.width - 12) {
            generator.nextChunk();
        }
        try {
            List<Block> minedBlocks = new ArrayList<>();
            for(Block block: blocks) {
                if(block.isMined()) {
                    minedBlocks.add(block);
                }
                block.tick();
            }
            for(Block block : minedBlocks) {
                blocks.remove(block);
            }

            List<Tile> deadEntities = new ArrayList<>();
            for(Tile entity : entities) {
                if(entity instanceof LivingEntity) {
                    if(!((LivingEntity) entity).isAlive()) deadEntities.add(entity);
                }
                entity.tick();
            }
            for(Tile entity : deadEntities) {
                entities.remove(entity);
            }
        }
        catch (ConcurrentModificationException ex) {
            //empty catch block
        }
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
     * @return a block from a given pos
     */
    public Block getBlock(Vec2d vec2d) {
        for(Block block : blocks) {
            if(block == null) continue;
            if(block.pos.x == vec2d.x && block.pos.y == vec2d.y) return block;
        }
        return null;
    }

    public List<Block> getBlocks() {
        return blocks;
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
            blocks.add(block);
            return true;
        }
        return false;
    }

    public void replaceBlock(Block block, boolean canCollide) {
        blocks.remove(getBlock(block.pos));
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


    public Block breakBlock(Vec2d pos) {

        Block brokeBlock = null;

        for(Block block : blocks) {
            if(block.pos.x == pos.x && block.pos.y == pos.y) {
                brokeBlock = block;
            }
        }
        if(brokeBlock == null) return null;

        if(brokeBlock.canBeMined()) {
            brokeBlock.mine();
            return brokeBlock;
        }
        return null;
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
        for(Block block : blocks) {
            if(block.screenPos.x - Renderer.CAMERA.offset.x > -50 && block.screenPos.x - Renderer.CAMERA.offset.x < GameFrame.WIDTH) {
                if(block.screenPos.y - Renderer.CAMERA.offset.y > -50 && block.screenPos.y - Renderer.CAMERA.offset.y < GameFrame.HEIGHT) {
                    block.render();
                }
            }
        }
    }
    private void renderEntities() {
        for(Tile entity : entities) {
            entity.render();
        }
    }
}
