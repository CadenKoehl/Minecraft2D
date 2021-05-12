package com.cadenkoehl.minecraft2D.item;

import com.cadenkoehl.minecraft2D.entities.Entity;
import com.cadenkoehl.minecraft2D.item.crafting.Recipe;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.ListTag;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory implements Iterable<ItemStack> {

    public static final int MAX_SIZE = 10;

    private final Entity entity;
    private final List<ItemStack> items;
    private ItemStack selectedItem;
    private int slot;

    public Inventory(Entity entity) {
        this.entity = entity;
        this.items = new ArrayList<>();
    }

    public ListTag<CompoundTag> getTag() {
        ListTag<CompoundTag> tag = new ListTag<>(CompoundTag.class);

        for(ItemStack item : this) {
            tag.add(item.getCompoundTag());
        }
        return tag;
    }

    public void addItem(ItemStack stack) {
        if(this.size() < MAX_SIZE) {
            for(ItemStack item : items) {
                if(item.getItem().getDisplayName().equals(stack.getItem().getDisplayName())) {
                    item.increment(stack.getCount());
                    return;
                }
            }
            items.add(stack);
            stack.setInventory(this);
            if(items.size() == 1) setSelectedItemSlot(0);
        }
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void decrement(Item item, int amount) {
        for(ItemStack stack : this) {
            if(stack.getItem().getName().equals(item.getName())) {
                stack.decrement(amount);
                return;
            }
        }
    }

    public void decrement(Item item) {
        decrement(item, 1);
    }

    public void remove(ItemStack item) {
        items.remove(item);
    }

    public List<String> getItemsAsString() {
        List<String> items = new ArrayList<>();

        for(ItemStack stack : this) {
            items.add(stack.getItem().getName());
        }
        return items;
    }

    public boolean canCraft(Recipe recipe) {
        return this.getItemsAsString().containsAll(recipe.getRequiredItemsAsString());
    }

    public List<Recipe> getCraftableItems() {

        List<Recipe> recipes = new ArrayList<>();

        for(Recipe recipe : Recipe.getRecipes()) {
            if(this.canCraft(recipe)) recipes.add(recipe);
        }
        return recipes;
    }

    public ItemStack getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItemSlot(int slot) {

        if(slot < 0) return;

        if(slot >= items.size()) {
            if(this.size() == 0) selectedItem = null;
            return;
        }
        this.selectedItem = items.get(slot);
        this.slot = slot;
    }

    public ItemStack getItem(int slot) {
        if(slot >= items.size()) return null;
        return items.get(slot);
    }

    public int size() {
        return items.size();
    }

    public Entity getEntity() {
        return entity;
    }

    @NotNull
    @Override
    public Iterator<ItemStack> iterator() {
        return items.iterator();
    }
}
