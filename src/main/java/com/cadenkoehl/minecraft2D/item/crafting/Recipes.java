package com.cadenkoehl.minecraft2D.item.crafting;

import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.item.Items;

public class Recipes {

    public static final Recipe WOOD_PLANKS_FROM_LOGS = new Recipe(Blocks.OAK_PLANKS.getBlockItem(), 4, Blocks.OAK_LOG.getBlockItem());
    public static final Recipe SANDSTONE_FROM_SAND = new Recipe(Blocks.SANDSTONE.getBlockItem(), 1, Blocks.SAND.getBlockItem(), Blocks.STONE.getBlockItem());
    public static final Recipe FLINT_AND_STEEL = new Recipe(Items.FLINT_AND_STEEL, 1, Items.FLINT);

    public static void registerRecipes() {
        register(
                WOOD_PLANKS_FROM_LOGS,
                SANDSTONE_FROM_SAND,
                FLINT_AND_STEEL
        );
    }

    private static void register(Recipe... recipes) {
        for(Recipe recipe : recipes) {
            Recipe.register(recipe);
        }
    }
}
