package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;


public abstract class LivingEntity extends Tile {

    private int health = this.getMaxHealth();

    public LivingEntity(Vec2d pos, World world) {
        super(pos, world);
    }

    public abstract int getMaxHealth();

    public void damage(int amount) {
        health = health - amount;
    }
}
