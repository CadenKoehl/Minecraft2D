package com.cadenkoehl.minecraft2D.item;

public class ItemStack {

    private final Item item;
    private int count;

    public ItemStack(Item item) {
        this.item = item;
        this.count = 1;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }
}