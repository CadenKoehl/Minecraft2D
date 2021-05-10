package com.cadenkoehl.minecraft2D.entities.spawns;

public class SpawnSettings {

    public SpawnGroup spawnGroup;
    public int weight;
    public int minGroupSize;
    public int maxGroupSize;

    public SpawnSettings() {
        spawnGroup = SpawnGroup.NONE;
    }

    public SpawnSettings weight(int weight) {
        this.weight = weight;
        return this;
    }

    public SpawnSettings minGroupSize(int minGroupSize) {
        this.minGroupSize = minGroupSize;
        return this;
    }

    public SpawnSettings maxGroupSize(int maxGroupSize) {
        this.maxGroupSize = maxGroupSize;
        return this;
    }
}
