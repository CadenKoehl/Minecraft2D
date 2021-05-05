package com.cadenkoehl.minecraft2D.item;

import net.querz.nbt.tag.CompoundTag;

public class ItemStack {

    private final Item item;
    private int count;
    private Inventory inventory;

    public ItemStack(Item item) {
        this(item, 1);
    }

    public ItemStack(Item item, int count) {
        this.item = item;
        setCount(count);
    }

    public void setCount(int count) {

        if(count > item.getMaxStackSize()) return;

        if(count < 1) {
            inventory.remove(this);
            return;
        }

        this.count = count;
    }

    public CompoundTag createCompoundTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString("Item", item.getName());
        tag.putInt("Count", this.count);
        return tag;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public void increment(int amount) {
        if(count < item.getMaxStackSize()) {
            count += amount;
        }
    }

    public void increment() {
        increment(1);
    }

    public void decrement(int amount) {
        if(count > 0) {
            count -= amount;
            if(count == 0 && inventory != null) {
                inventory.remove(this);
                inventory.setSelectedItemSlot(0);
            }
        }
    }
    public void decrement() {
        decrement(1);
    }
}