package com.cadenkoehl.minecraft2D.render;

import com.cadenkoehl.minecraft2D.entities.PlayerEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;

public class Camera {

    private Vec2d offset;

    Camera() {
        this.offset = new Vec2d(0, 0);
    }

    public void centerOn(PlayerEntity player) {
        this.offset = player.screenPos;
    }
}
