package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.item.BlockItem;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class LeafBlock extends Block {

    public LeafBlock(Vec2d pos, World world) {
        super("Leaf Block", pos, world);
    }
}