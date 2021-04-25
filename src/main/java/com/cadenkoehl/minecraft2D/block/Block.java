package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.entities.item.ItemEntity;
import com.cadenkoehl.minecraft2D.item.BlockItem;
import com.cadenkoehl.minecraft2D.item.Item;
import com.cadenkoehl.minecraft2D.item.ItemStack;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

public abstract class Block extends Tile {

    private boolean canCollide;
    private boolean mined;
    private final BlockItem blockItem;

    public Block(String displayName, Vec2d pos, World world) {
        super(pos, world, "block", displayName);
        canCollide = true;
        this.blockItem = new BlockItem(new Item.Settings(displayName), this);
    }

    public void setCanCollide(boolean canCollide) {
        this.canCollide = canCollide;
    }

    public boolean canCollide() {
        return canCollide;
    }

    public boolean canBeMined() {
        return true;
    }

    public boolean isMined() {
        return mined;
    }

    public void mine() {
        mined = true;
        //if(blockItem == null) return;
        //this.getWorld().spawnEntity(new ItemEntity(new ItemStack(blockItem), new Vec2d(pos.x, pos.y), this.getWorld()));
    }

    @Override
    public Texture getTexture() {
        return new Texture("textures/blocks/" + getName() + ".png");
    }

    public Block copy() {

        String displayName = this.getDisplayName();
        return new Block(displayName, pos, getWorld()) {
            @Override
            public String getDisplayName() {
                return displayName;
            }

            @Override
            public boolean canCollide() {
                return canCollide;
            }
        };
    }
}