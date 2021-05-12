package com.cadenkoehl.minecraft2D.entities.mob.hostile;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class ZombieEntity extends HostileEntity {

    public ZombieEntity() {
        super("Zombie");
    }

    @Override
    public int getBaseAttackDamage() {
        return 2;
    }
}
