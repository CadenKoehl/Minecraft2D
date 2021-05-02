package com.cadenkoehl.minecraft2D.block;

public class Blocks {

    public static final Block DIRT = new Block("Dirt", null, null);
    public static final Block GRASS_BLOCK = new Block("Grass Block", null, null);
    public static final Block SAND = new Block("Sand", null, null);
    public static final Block SANDSTONE = new Block("Sandstone", null, null);
    public static final Block OAK_LOG = new Block("Oak Log", null, null);
    public static final Block OAK_PLANKS = new OakPlanksBlock(null, null);
    public static final Block LEAF_BLOCK = new LeafBlock(null, null);
    public static final Block STONE = new Block("Stone", null, null);
    public static final Block CACTUS = new Block("Cactus", null, null);
    public static final Block GRAVEL = new GravelBlock(null, null);

    public static final Block OBSIDIAN = new ObsidianBlock(null, null);
    public static final NetherPortalBlock NETHER_PORTAL = new NetherPortalBlock("Nether Portal", null, null);
    public static final Block NETHERRACK = new Block("Netherrack", null, null);
    public static final Block SOUL_SAND = new Block("Soul Sand", null, null);
    public static final Block BEDROCK = new BedrockBlock(null, null);

    public static final FluidBlock WATER = new WaterBlock(null, null);

}
