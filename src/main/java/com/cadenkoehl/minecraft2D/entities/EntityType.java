package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;

public class EntityType<E extends Entity> {

    //public static final EntityType<PlayerEntity> PLAYER = new EntityType<PlayerEntity>(PlayerEntity::new, "Player");

    private final Builder<E> builder;
    private final String displayName;

    public EntityType(Builder<E> builder, String displayName) {
        this.builder = builder;
        this.displayName = displayName;
    }

    public E getEntity() {
        return builder.build();
    }

    public interface Builder<E extends Entity> {
        E build();
    }
}
