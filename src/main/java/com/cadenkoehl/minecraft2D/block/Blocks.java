package com.cadenkoehl.minecraft2D.block;

import java.util.ArrayList;
import java.util.List;

public class Blocks {

    public static Block GRASS_BLOCK;
    public static Block DIRT;
    public static Block AIR;

    public static void registerBlocks() {
        GRASS_BLOCK = register("Grass Block");
        DIRT = register("Dirt");
        AIR = register("Air");
    }

    public static List<Block> blocks = new ArrayList<>();

    public static Block getBlockByName(String name) {
        for(Block block : blocks) {
            if(block.getName().equals(name)) return block;
        }
        return null;
    }

    private static Block register(String name) {
        Block block = new Block(name);
        blocks.add(block);
        return block;
    }
}