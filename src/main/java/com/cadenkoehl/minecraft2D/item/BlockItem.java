package com.cadenkoehl.minecraft2D.item;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.BlockState;
import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Texture;

public class BlockItem extends Item {

    private final Block block;

    public BlockItem(Settings settings, Block block) {
        super(settings);
        this.block = block;
    }

    @Override
    public ClickResult onClick(PlayerEntity player, ItemStack stack, Vec2d clickPos) {
        if(player.placeBlock(new BlockState(block, clickPos, player.getWorld()))) {
            return ClickResult.SHOULD_DECREMENT;
        }
        return ClickResult.FAILED;
    }

    @Override
    public Texture getTexture() {
        return block.getTexture().rescale(ITEM_SIZE);
    }

    public Block getBlock() {
        return block;
    }
}
