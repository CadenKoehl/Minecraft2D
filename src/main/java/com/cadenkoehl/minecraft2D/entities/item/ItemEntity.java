package com.cadenkoehl.minecraft2D.entities.item;

import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.item.ItemStack;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

public class ItemEntity extends Tile {

    private final ItemStack stack;

    public ItemEntity(ItemStack stack, Vec2d pos, World world) {
        super(pos, world, "blocks", stack.getItem().getDisplayName());
        this.stack = stack;
    }

    public ItemStack getItemStack() {
        return stack;
    }

    @Override
    public Texture getTexture() {
        return stack.getItem().getTexture();
    }
}
