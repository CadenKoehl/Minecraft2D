package com.cadenkoehl.minecraft2D.item.crafting;

import com.cadenkoehl.minecraft2D.block.Blocks;

public class Recipes {

    public static final Recipe WOOD_PLANKS_FROM_LOGS = new Recipe(Blocks.OAK_PLANKS.getItem(), 4, Blocks.OAK_LOG.getItem());
    public static final Recipe SANDSTONE_FROM_SAND = new Recipe(Blocks.SANDSTONE.getItem(), 1, Blocks.SAND.getItem(), Blocks.STONE.getItem());
    public static final Recipe OBSIDIAN = new Recipe(Blocks.OBSIDIAN.getItem(), 1, Blocks.CACTUS.getItem(), Blocks.STONE.getItem());

    public static void registerRecipes() {
        register(
                WOOD_PLANKS_FROM_LOGS,
                SANDSTONE_FROM_SAND,
                OBSIDIAN
        );
    }

    private static void register(Recipe... recipes) {
        for(Recipe recipe : recipes) {
            Recipe.register(recipe);
        }
    }
}
