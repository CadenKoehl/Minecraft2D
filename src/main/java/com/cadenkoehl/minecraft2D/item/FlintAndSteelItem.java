package com.cadenkoehl.minecraft2D.item;

import com.cadenkoehl.minecraft2D.block.BlockState;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;

public class FlintAndSteelItem extends Item {

    public FlintAndSteelItem(Settings settings) {
        super(settings);
    }

    @Override
    public ClickResult onClick(PlayerEntity player, ItemStack stack, Vec2d pos) {

        boolean success = false;

        for(BlockState state : player.getChunk().getBlocks()) {
            if(state.getBlock() == Blocks.NETHER_PORTAL) {
                success = true;
                state.setVisible(true);
            }
        }

        if(success) return ClickResult.SUCCESS;
        else return ClickResult.FAILED;
    }
}