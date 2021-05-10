package com.cadenkoehl.minecraft2D.item;

import java.util.HashMap;
import java.util.Map;

public class Items {

    private static final Map<String, Item> registries = new HashMap<>();

    public static final Item FLINT = register(new Item(new Item.Settings("Flint")));
    public static final Item FLINT_AND_STEEL = register(new FlintAndSteelItem(new Item.Settings("Flint and Steel")));
    public static final Item STEAK = register(new FoodItem(new FoodItem.Settings("Steak").restoredHealth(2)));

    public static Item valueOf(String name) {
        return registries.get(name);
    }

    public static Item register(Item item) {
        registries.put(item.getName(), item);
        return item;
    }
}