package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.item.BlockItem;
import com.cadenkoehl.minecraft2D.item.Item;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

public class Block extends Tile {

    private boolean canCollide;
    private boolean mined;
    private final BlockItem blockItem;
    private final Texture texture;

    public Block(String displayName, Vec2d pos, World world) {
        super(pos, world, "block", displayName);
        canCollide = true;
        this.blockItem = new BlockItem(new Item.Settings(displayName), this);
        this.texture = new Texture("textures/blocks/" + getName() + ".png");
    }

    public Block(String displayName, Vec2d pos, World world, boolean canCollide) {
        super(pos, world, "block", displayName);
        this.canCollide = canCollide;
        this.blockItem = new BlockItem(new Item.Settings(displayName), this);
        this.texture = new Texture("textures/blocks/" + getName() + ".png");
    }

    public Item getItem() {
        return blockItem;
    }

    public void setCanCollide(boolean canCollide) {
        this.canCollide = canCollide;
    }

    public boolean canCollide() {
        return this.canCollide;
    }

    public boolean canBeMined() {
        return true;
    }

    public Block getBlockBehind() {
        return null;
    }

    public boolean isMined() {
        return mined;
    }

    public void mine() {
        if(canBeMined()) {
            mined = true;
        }
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    public Block copy() {
        String displayName = this.getDisplayName();
        return new Block(displayName, pos, getWorld(), this.canCollide) {
            @Override
            public String getDisplayName() {
                return displayName;
            }

            @Override
            public Texture getTexture() {
                return Block.this.getTexture();
            }

            @Override
            public boolean canBeMined() {
                return Block.this.canBeMined();
            }

            @Override
            public Item getItem() {
                return Block.this.getItem();
            }

            @Override
            public Block getBlockBehind() {
                return Block.this.getBlockBehind();
            }

        };
    }
}