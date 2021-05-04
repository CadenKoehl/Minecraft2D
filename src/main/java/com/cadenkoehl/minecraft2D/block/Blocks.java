package com.cadenkoehl.minecraft2D.block;

import java.awt.*;

public class Blocks {

    public static final Block DIRT = new Block(new Block.Settings("Dirt"));
    public static final Block GRASS_BLOCK = new Block(new Block.Settings("Grass Block"));
    public static final Block SAND = new Block(new Block.Settings("Sand"));
    public static final Block SANDSTONE = new Block(new Block.Settings("Sandstone"));
    public static final Block OAK_LOG = new Block(new Block.Settings("Oak Log"));
    public static final Block OAK_PLANKS = new Block(new Block.Settings("Oak Planks"));
    public static final Block LEAF_BLOCK = new Block(new Block.Settings("Leaf Block").breakSpeed(1));
    public static final Block STONE = new Block(new Block.Settings("Stone"));
    public static final Block CACTUS = new Block(new Block.Settings("Cactus"));
    public static final Block GRAVEL = new Block(new Block.Settings("Gravel"));

    public static final Block OBSIDIAN = new Block(new Block.Settings("Obsidian").breakSpeed(-1));
    public static final Block NETHER_PORTAL = new Block(new Block.Settings("Nether Portal"));
    public static final Block NETHERRACK = new Block(new Block.Settings("Netherrack"));
    public static final Block SOUL_SAND = new Block(new Block.Settings("Soul Sand"));
    public static final Block BEDROCK = new Block(new Block.Settings("Bedrock").breakSpeed(-1)) {
    };

    public static final FluidBlock WATER = new FluidBlock(new Color(0, 123, 255, 70), new Block.Settings("Water"));

}
