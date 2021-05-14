package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.block.BlockState;
import com.cadenkoehl.minecraft2D.physics.Vec2d;

import java.util.ArrayList;
import java.util.List;

public class Chunk {

    private final List<BlockState> blocks;

    public Chunk() {
        blocks = new ArrayList<>();
    }

    public void tick() {
        for(BlockState block : new ArrayList<>(blocks)) {
            block.tick();
            if(block.hasBeenMined()) {
                blocks.remove(block);
            }
        }
    }

    public void render() {
        for(BlockState block : blocks) {
            block.render();
        }
    }

    public boolean setBlock(BlockState block) {
        block.onPlace();
        return blocks.add(block);
    }

    public void removeBlock(BlockState block) {
        blocks.remove(block);
    }

    public BlockState getBlock(Vec2d pos) {
        for(BlockState block : new ArrayList<>(blocks)) {
            if(block.pos.x == pos.x && block.pos.y == pos.y) return block;
        }
        return null;
    }

    public List<BlockState> getBlocks() {
        return blocks;
    }
}