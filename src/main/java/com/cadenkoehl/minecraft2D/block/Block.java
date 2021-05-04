package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.display.Input;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.item.BlockItem;
import com.cadenkoehl.minecraft2D.item.Item;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

public class Block extends Tile {

    public static final Texture BREAK_0 = new Texture("textures/blocks/destroy_stage_0.png");
    public static final Texture BREAK_1 = new Texture("textures/blocks/destroy_stage_1.png");
    public static final Texture BREAK_2 = new Texture("textures/blocks/destroy_stage_2.png");
    public static final Texture BREAK_3 = new Texture("textures/blocks/destroy_stage_3.png");
    public static final Texture BREAK_4 = new Texture("textures/blocks/destroy_stage_4.png");
    public static final Texture BREAK_5 = new Texture("textures/blocks/destroy_stage_5.png");
    public static final Texture BREAK_6 = new Texture("textures/blocks/destroy_stage_6.png");
    public static final Texture BREAK_7 = new Texture("textures/blocks/destroy_stage_7.png");
    public static final Texture BREAK_8 = new Texture("textures/blocks/destroy_stage_8.png");
    public static final Texture BREAK_9 = new Texture("textures/blocks/destroy_stage_9.png");

    public static Texture breakTexture(int breakLevel) {
        return switch (breakLevel) {
            case 0 -> BREAK_0;
            case 1 -> BREAK_1;
            case 2 -> BREAK_2;
            case 3 -> BREAK_3;
            case 4 -> BREAK_4;
            case 5 -> BREAK_5;
            case 6 -> BREAK_6;
            case 7 -> BREAK_7;
            case 8 -> BREAK_8;
            default -> BREAK_9;
        };
    }

    private boolean canCollide;
    public PlayerEntity miner;
    public int minedTicks;
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

    @Override
    public void tick() {
        if(!this.inFrame()) return;
        if(this.miner != null) {
            if(minedTicks < this.getBreakSpeed() * 90) {
                if(Input.isMousePressed() && this.miner.breakingBlock == this) minedTicks++;
                else {
                    minedTicks = 0;
                    miner = null;
                }
            }
            else {
                miner.breakBlock(this.pos);
            }
        }
        super.tick();
    }

    @Override
    public void render() {
        super.render();
        if(this.miner != null) {
            Renderer.render(breakTexture(minedTicks / (this.getBreakSpeed() * 9)), screenPos.x - Renderer.CAMERA.offset.x, screenPos.y - Renderer.CAMERA.offset.y);
        }
    }

    public Item getItem() {
        return blockItem;
    }

    public void setCanCollide(boolean canCollide) {
        this.canCollide = canCollide;
    }

    public int getBreakSpeed() {
        return 3;
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

    public boolean hasBeenMined() {
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
            public int getBreakSpeed() {
                return Block.this.getBreakSpeed();
            }

            @Override
            public Block getBlockBehind() {
                return Block.this.getBlockBehind();
            }

        };
    }
}