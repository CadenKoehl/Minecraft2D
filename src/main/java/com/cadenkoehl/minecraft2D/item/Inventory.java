package com.cadenkoehl.minecraft2D.item;

import com.cadenkoehl.minecraft2D.entities.mob.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory implements Iterable<ItemStack> {

    public static final int MAX_SIZE = 9;

    private final LivingEntity entity;
    private final List<ItemStack> items;

    public Inventory(LivingEntity entity) {
        this.entity = entity;
        this.items = new ArrayList<>();
    }

    public void add(ItemStack stack) {
        if(this.size() < MAX_SIZE) {
            items.add(stack);
        }
    }

    public int size() {
        return items.size();
    }

    public LivingEntity getEntity() {
        return entity;
    }

    @NotNull
    @Override
    public Iterator<ItemStack> iterator() {
        return items.iterator();
    }
}
