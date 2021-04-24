package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.util.LogLevel;
import com.cadenkoehl.minecraft2D.util.Logger;
import com.cadenkoehl.minecraft2D.world.gen.TerrainGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class World {

    private final List<Block> blocks = new ArrayList<>();

    public World() {
        this.genSpawnTerrain();
    }

    public abstract String getDisplayName();
    public abstract TerrainGenerator getGenerator();

    public void genSpawnTerrain() {
        this.getGenerator().generate();
    }

    /**
     * @return a block from a given pos
     */
    public Block getBlock(Vec2d vec2d) {
        for(Block block : blocks) {
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

    public void setBlock(Block block) {
        block = block.copy();
        block.setWorld(this);
        if(block.pos == null) throw new IllegalStateException("Block has no state yet!");
        blocks.add(block);
    }

    public void setBlock(Block block, Vec2d pos) {
        block.setPos(pos);
        this.setBlock(block);
    }


    public void breakBlock(Vec2d pos) {

        Block brokeBlock = null;

        for(Block block : blocks) {
            if(block.pos.x == pos.x && block.pos.y == pos.y) {
                brokeBlock = block;
            }
        }
        if(brokeBlock == null) return;

        blocks.remove(brokeBlock);
        brokeBlock.updateGraphics();
    }

    public void render() {
        for(Block block : blocks) {
            block.render();
        }
    }
}
