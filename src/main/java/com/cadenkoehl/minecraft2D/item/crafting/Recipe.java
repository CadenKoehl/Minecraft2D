package com.cadenkoehl.minecraft2D.item.crafting;

import com.cadenkoehl.minecraft2D.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recipe {

    private static final List<Recipe> RECIPES = new ArrayList<>();

    public static List<Recipe> getRecipes() {
        return RECIPES;
    }

    public static void register(Recipe recipe) {
        RECIPES.add(recipe);
    }

    private final Item result;
    private final List<Item> requiredItems;
    private final int resultCount;

    public Recipe(Item result, int resultCount, Item... requiredItems) {
        this.result = result;
        this.requiredItems = Arrays.asList(requiredItems);
        this.resultCount = resultCount;
    }

    public Item getResult() {
        return result;
    }

    public List<Item> getRequiredItems() {
        return requiredItems;
    }

    public int getResultCount() {
        return resultCount;
    }

    public List<String> getRequiredItemsAsString() {

        List<String> items = new ArrayList<>();

        for(Item item : requiredItems) {
            items.add(item.getName());
        }
        return items;
    }
}
