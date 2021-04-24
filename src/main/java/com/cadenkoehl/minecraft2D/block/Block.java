package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

public abstract class Block extends Tile {

    private boolean canCollide;
    private boolean mined;

    public Block(Vec2d pos, World world) {
        super(pos, world);
        canCollide = true;
    }

    public void setCanCollide(boolean canCollide) {
        this.canCollide = canCollide;
    }

    public boolean canCollide() {
        return canCollide;
    }

    public boolean isMined() {
        return mined;
    }

    public void mine() {
        mined = true;
    }

    @Override
    public Texture getTexture() {
        return new Texture("textures/blocks/" + getName() + ".png");
    }

    public Block copy() {

        String displayName = this.getDisplayName();
        return new Block(pos, getWorld()) {
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

    @Override
    public int getCollisionHeight() {
        return 0;
    }

    @Override
    public int getCollisionWidth() {
        return 0;
    }
}