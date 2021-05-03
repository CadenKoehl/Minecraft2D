package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Chunk {
    
    private final List<Block> blocks;
    
    public Chunk() {
        blocks = new ArrayList<>();
    }

    public void tick() {
        for(Block block : blocks) {
            block.tick();
        }
    }

    public void render() {
        for(Block block : blocks) {
            block.render();
        }
    }
}