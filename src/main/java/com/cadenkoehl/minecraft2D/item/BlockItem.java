package com.cadenkoehl.minecraft2D.item;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.render.Texture;

public class BlockItem extends Item {

    private final Block block;

    public BlockItem(Settings settings, Block block) {
        super(settings);
        this.block = block;
    }

    @Override
    public Texture getTexture() {
        return block.getTexture();
    }

    public Block getBlock() {
        return block;
    }
}
