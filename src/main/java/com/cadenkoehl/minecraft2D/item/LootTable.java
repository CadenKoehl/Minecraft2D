package com.cadenkoehl.minecraft2D.item;

import com.cadenkoehl.minecraft2D.util.Util;

import java.util.Collections;
import java.util.List;

public class LootTable {

    private final List<Entry> entries;

    public LootTable(List<Entry> entries) {
        this.entries = entries;
    }

    public ItemStack dropItem() {
        if(entries.size() == 1) {
            Entry entry = entries.get(0);
            return new ItemStack(entry.item, Util.randomIntBetween(entry.minCount, entry.maxCount));
        }
        for(Entry entry : entries) {
            if(entry.dropChance > 1 && Util.random(entry.dropChance)) {
                return new ItemStack(entry.item, Util.randomIntBetween(entry.minCount, entry.maxCount));
            }
        }
        Entry entry = entries.get(1);
        return new ItemStack(entry.item, Util.randomIntBetween(entry.minCount, entry.maxCount));
    }

    public static class Entry {

        private final Item item;
        private final int dropChance, minCount, maxCount;

        public Entry(Item item, int dropChance, int minCount, int maxCount) {
            this.item = item;
            this.dropChance = dropChance;
            this.minCount = minCount;
            this.maxCount = maxCount;
        }
    }
}
