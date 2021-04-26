package com.cadenkoehl.minecraft2D.render;

import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;

public class Camera {

    public Vec2d offset;

    Camera() {
        this.offset = new Vec2d(0, 0);
    }

    public void centerOn(PlayerEntity player) {
        if(this.offset.x == player.screenPos.x && this.offset.y == player.screenPos.y) return;

        this.offset.x = player.screenPos.x - 470;
        this.offset.y = player.screenPos.y - 380;
    }
}
