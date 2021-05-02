package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.item.Item;
import com.cadenkoehl.minecraft2D.item.Items;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.util.Util;
import com.cadenkoehl.minecraft2D.world.World;

public class GravelBlock extends Block {

    public GravelBlock(Vec2d pos, World world) {
        super("Gravel", pos, world);
    }

    @Override
    public Item getItem() {
        if(Util.random(10)) return Items.FLINT;
        return super.getItem();
    }
}
