package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.StoneBlock;
import com.cadenkoehl.minecraft2D.display.GameFrame;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.entities.mob.LivingEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.world.gen.TerrainGenerator;

import java.util.*;

public abstract class World {

    private final List<Block> blocks = new ArrayList<>();
    private final List<Tile> entities = new ArrayList<>();

    public World() {
        this.genSpawnTerrain();
    }

    public abstract String getDisplayName();
    public abstract TerrainGenerator getGenerator();

    public void tick() {
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

    public void genSpawnTerrain() {
        this.getGenerator().generate();
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

    public void setBlock(Block block, boolean canCollide) {
        block = block.copy();
        block.setWorld(this);
        if(block.pos == null) throw new IllegalStateException("Block has no state yet!");
        if(this.getBlock(block.pos.x, block.pos.y) == null) {
            blocks.add(block);
        }
    }

    public void setBlock(Block block) {
        setBlock(block, true);
    }

    public void setBlock(Block block, Vec2d pos) {
        block.setPos(pos);
        this.setBlock(block);
    }

    public void setBlock(Block block, Vec2d pos, boolean canCollide) {
        block.setPos(pos);
        this.setBlock(block, canCollide);
    }


    public void breakBlock(Vec2d pos) {

        Block brokeBlock = null;

        for(Block block : blocks) {
            if(block.pos.x == pos.x && block.pos.y == pos.y) {
                brokeBlock = block;
            }
        }
        if(brokeBlock == null) return;

        brokeBlock.mine();
    }

    public void render() {
        try {
            renderBlocks();
        }
        catch (ConcurrentModificationException ex) {
            renderBlocks();
        }
    }
    private void renderBlocks() {
        for(Block block : blocks) {
            if(block.screenPos.x - Renderer.CAMERA.offset.x > -50 && block.screenPos.x - Renderer.CAMERA.offset.x < GameFrame.WIDTH
            && block.screenPos.y - Renderer.CAMERA.offset.y > -50 && block.screenPos.y - Renderer.CAMERA.offset.y < GameFrame.HEIGHT) {
                block.render();
            }
        }
        for(Tile entity : entities) {
            entity.render();
        }
    }
}
