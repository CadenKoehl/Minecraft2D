package com.cadenkoehl.minecraft2D.world.gen.feature;

import com.cadenkoehl.minecraft2D.Game;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.block.NetherPortalBlock;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

import java.util.List;

public class NetherPortalFeature implements ConfiguredFeature {

    @Override
    public void generate(int startX, int startY, World world) {
        gen(startX, startY, Game.getOverworld());
        gen(startX, startY, Game.getNether());
    }

    private void gen(int startX, int startY, World world) {
        //bottom two
        world.setBlock(Blocks.OBSIDIAN, new Vec2d(startX, startY - 1));
        world.setBlock(Blocks.OBSIDIAN, new Vec2d(startX + 1, startY - 1));

        //netherrack
        world.setBlock(Blocks.NETHERRACK, new Vec2d(startX + 2, startY - 1));
        world.setBlock(Blocks.NETHERRACK, new Vec2d(startX - 1, startY - 1));
        world.setBlock(Blocks.NETHERRACK, new Vec2d(startX + 2, startY - 5));
        world.setBlock(Blocks.NETHERRACK, new Vec2d(startX - 1, startY - 5));

        //right 3
        world.setBlock(Blocks.OBSIDIAN, new Vec2d(startX - 1, startY - 2), false);
        world.setBlock(Blocks.OBSIDIAN, new Vec2d(startX - 1, startY - 3), false);
        world.setBlock(Blocks.OBSIDIAN, new Vec2d(startX - 1, startY - 4), false);

        //left 3
        world.setBlock(Blocks.OBSIDIAN, new Vec2d(startX + 2, startY - 2), false);
        world.setBlock(Blocks.OBSIDIAN, new Vec2d(startX + 2, startY - 3), false);
        world.setBlock(Blocks.OBSIDIAN, new Vec2d(startX + 2, startY - 4), false);

        //top 2
        world.setBlock(Blocks.OBSIDIAN, new Vec2d(startX, startY - 5));
        world.setBlock(Blocks.OBSIDIAN, new Vec2d(startX + 1, startY - 5));


        //inside portal checkers
        world.netherPortals.add(List.of(
                new Vec2d(startX, startY - 2),
                new Vec2d(startX, startY - 3),
                new Vec2d(startX, startY - 4),

                new Vec2d(startX + 1, startY - 2),
                new Vec2d(startX + 1, startY - 3),
                new Vec2d(startX + 1, startY - 4)
        ));
    }

    @Override
    public int rarity() {
        return 1;
    }
}