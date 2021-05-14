package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.item.Items;
import com.cadenkoehl.minecraft2D.item.LootTable;
import com.cadenkoehl.minecraft2D.sound.SoundEvents;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Blocks {

    private static final Map<String, Block> registries = new HashMap<>();

    public static final Block DIRT = register(new Block.Settings("Dirt").breakSound(SoundEvents.GRASS_BREAK));
    public static final Block GRASS_BLOCK = register(new Block.Settings("Grass Block").breakSound(SoundEvents.GRASS_BREAK));
    public static final Block SAND = register(new Block.Settings("Sand").affectedByGravity(true).breakSound(SoundEvents.GRASS_BREAK));
    public static final Block SANDSTONE = register(new Block.Settings("Sandstone"));
    public static final Block OAK_LOG = register(new Block.Settings("Oak Log"));
    public static final Block OAK_PLANKS = register(new Block.Settings("Oak Planks"));
    public static final Block LEAF_BLOCK = register(new Block.Settings("Leaf Block").breakSound(SoundEvents.GRASS_BREAK).breakSpeed(1));
    public static final Block STONE = register(new Block.Settings("Stone"));
    public static final Block CACTUS = register(new Block.Settings("Cactus").breakSound(SoundEvents.GRASS_BREAK));
    public static final Block GRAVEL = register(new Block.Settings("Gravel").affectedByGravity(true).breakSound(SoundEvents.GRASS_BREAK).addDrop(new LootTable.Entry(Items.FLINT, 10, 1, 1)));
    public static final FluidBlock WATER = register(new FluidBlock(new Color(0, 123, 255, 70), new Block.Settings("Water")));
    public static final Block OBSIDIAN = register(new Block.Settings("Obsidian").breakSpeed(-1));
    public static final Block NETHER_PORTAL = register(new Block.Settings("Nether Portal").defaultCanCollide(false).breakSpeed(-1));
    public static final Block NETHERRACK = register(new Block.Settings("Netherrack"));
    public static final Block SOUL_SAND = register(new Block.Settings("Soul Sand").breakSound(SoundEvents.GRASS_BREAK));
    public static final Block BEDROCK = register(new Block.Settings("Bedrock").breakSpeed(-1));

    public static Block valueOf(String name) {
        return registries.get(name);
    }

    private static Block register(Block.Settings settings) {
        Block block = new Block(settings);
        registries.put(block.getName(), block);
        return block;
    }

    private static <T extends Block> T register(T block) {
        registries.put(block.getName(), block);
        return block;
    }
}