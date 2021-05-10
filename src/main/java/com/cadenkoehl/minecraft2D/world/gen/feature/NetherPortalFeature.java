package com.cadenkoehl.minecraft2D.world.gen.feature;

import com.cadenkoehl.minecraft2D.client.GameClient;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class NetherPortalFeature implements ConfiguredFeature {

    @Override
    public void generate(int startX, int startY, World world) {
        gen(startX, startY, GameClient.getOverworld());
        gen(startX, startY, GameClient.getNether());
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

        world.setBlock(Blocks.NETHER_PORTAL, new Vec2d(startX, startY - 2));
        world.setBlock(Blocks.NETHER_PORTAL, new Vec2d(startX, startY - 3));
        world.setBlock(Blocks.NETHER_PORTAL, new Vec2d(startX, startY - 4));

        world.setBlock(Blocks.NETHER_PORTAL, new Vec2d(startX + 1, startY - 2));
        world.setBlock(Blocks.NETHER_PORTAL, new Vec2d(startX + 1, startY - 3));
        world.setBlock(Blocks.NETHER_PORTAL, new Vec2d(startX + 1, startY - 4));

    }

    @Override
    public int rarity() {
        return 10;
    }
}
