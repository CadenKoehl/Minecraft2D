package com.cadenkoehl.minecraft2D.item;

import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;

import java.util.List;

public class FlintAndSteelItem extends Item {

    public FlintAndSteelItem(Settings settings) {
        super(settings);
    }

    @Override
    public ClickResult onClick(PlayerEntity player, Vec2d pos) {
        for(List<Vec2d> portal : player.getWorld().netherPortals) {
            for(Vec2d portalBlockPos : portal) {
                if(pos.x == portalBlockPos.x && pos.y == portalBlockPos.y) {
                    player.getWorld().lightPortal(portal);
                    return ClickResult.SUCCESS;
                }
            }
        }
        return ClickResult.FAILED;
    }
}
