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

/**
 * Represents a block that has been placed in the world.
 */
public class BlockState extends Tile {

    private final Block block;
    private boolean canCollide;
    public PlayerEntity miner;
    public int minedTicks;
    private boolean mined;
    private final Item blockItem;
    private final Texture texture;

    public BlockState(Block block, Vec2d pos, World world) {
        this(block, pos, world, true);
    }

    public BlockState(Block block, Vec2d pos, World world, boolean canCollide) {
        super(pos, world, "block", block.getDisplayName());
        this.block = block;
        this.canCollide = canCollide;
        this.blockItem = new BlockItem(new Item.Settings(this.block.getDisplayName()), this.block);
        this.texture = this.block.getTexture();
    }

    @Override
    public void tick() {
        if(!this.inFrame()) return;
        if(this.miner != null) {
            if(minedTicks < this.block.getBreakSpeed() * 90) {
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
            Renderer.render(Block.breakTexture(minedTicks / (this.block.getBreakSpeed() * 9)), screenPos.x - Renderer.CAMERA.offset.x, screenPos.y - Renderer.CAMERA.offset.y);
        }
    }

    public Block getBlock() {
        return block;
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

    public boolean hasBeenMined() {
        return mined;
    }

    public void mine() {
        mined = true;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}