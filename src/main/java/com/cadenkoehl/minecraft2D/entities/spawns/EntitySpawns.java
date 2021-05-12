package com.cadenkoehl.minecraft2D.entities.spawns;

import com.cadenkoehl.minecraft2D.entities.Entity;
import com.cadenkoehl.minecraft2D.entities.EntityType;

import java.util.ArrayList;
import java.util.List;

public class EntitySpawns<E extends Entity> {

    private static final List<EntitySpawns<?>> SPAWNS = new ArrayList<>();

    private final EntityType<E> entityType;
    private final SpawnSettings settings;

    private EntitySpawns(EntityType<E> entityType, SpawnSettings settings) {
        this.entityType = entityType;
        this.settings = settings;
    }


    public static class SpawnSettings {

        public final SpawnGroup spawnGroup;
        public final int weight;
        public final int minGroupSize;
        public final int maxGroupSize;

        public SpawnSettings(SpawnGroup spawnGroup, int weight, int minGroupSize, int maxGroupSize) {
            this.spawnGroup = spawnGroup;
            this.weight = weight;
            this.minGroupSize = minGroupSize;
            this.maxGroupSize = maxGroupSize;
        }
    }

    public enum SpawnGroup {

        MONSTER;

        List<EntityType<?>> entities;
        SpawnGroup() {
            this.entities = new ArrayList<>();
        }

        public <T extends Entity> void addEntity(EntityType<T> entity) {
            entities.add(entity);
        }

        public List<EntityType<?>> getEntities() {
            return entities;
        }
    }
}
