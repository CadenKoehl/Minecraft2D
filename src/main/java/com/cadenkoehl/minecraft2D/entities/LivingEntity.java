package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;


public abstract class LivingEntity extends Tile {

    private int health = this.getMaxHealth();

    public LivingEntity(Vec2d pos, World world) {
        super(pos, world);
    }

    @Override
    public void tick() {
        super.tick();

    }

    @Override
    public boolean isAffectedByGravity() {
        return true;
    }

    public abstract int getMaxHealth();

    public void damage(int amount) {
        health = health - amount;
        if(health < 0) kill();
    }

    public void kill() {

    }
}
