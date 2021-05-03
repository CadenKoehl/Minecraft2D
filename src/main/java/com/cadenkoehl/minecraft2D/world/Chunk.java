package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.physics.Vec2d;

import java.util.ArrayList;
import java.util.List;

public class Chunk {
    
    private final List<Block> blocks;
    
    public Chunk() {
        blocks = new ArrayList<>();
    }

    public void tick() {
        for(Block block : new ArrayList<>(blocks)) {
            block.tick();
            if(block.isMined()) blocks.remove(block);
        }
    }

    public void render() {
        for(Block block : blocks) {
            block.render();
        }
    }

    public boolean setBlock(Block block) {
        return blocks.add(block);
    }

    public void removeBlock(Block block) {
        blocks.remove(block);
    }

    public Block getBlock(Vec2d pos) {
        for(Block block : new ArrayList<>(blocks)) {
            if(block.pos.x == pos.x && block.pos.y == pos.y) return block;
        }
        return null;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}