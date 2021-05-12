package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.client.GameClient;
import com.cadenkoehl.minecraft2D.entities.Entity;
import com.cadenkoehl.minecraft2D.entities.EntityType;
import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;

import java.io.File;

public class Realm {

    private final String name;
    private final long seed;
    private final Save save;

    private final PlayerEntity player;
    private final Overworld overworld;
    private final Nether nether;

    private World currentWorld;

    public Realm(String name, long seed) {
        this.name = name;
        this.seed = seed;

        save = new Save(name);

        this.nether = new Nether(this);
        this.overworld = new Overworld(this);
        this.currentWorld = this.overworld;

        this.player = currentWorld.spawnEntity(EntityType.PLAYER, new Vec2d(17, 7));
    }

    public Save getSave() {
        return save;
    }

    public void save() {
        overworld.saveChunks();
        nether.saveChunks();
        player.saveData();
    }

    public String getName() {
        return name;
    }

    public long getSeed() {
        return seed;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public Overworld getOverworld() {
        return overworld;
    }

    public Nether getNether() {
        return nether;
    }

    public World getCurrentWorld() {
        return currentWorld;
    }

    public void setCurrentWorld(World currentWorld) {
        this.currentWorld = currentWorld;
    }

    public static class Save {

        private final String name;
        private final File file;

        public Save(String name) {
            this.name = name;
            this.file = new File("saves", name);

            file.mkdirs();
        }

        public String getName() {
            return name;
        }

        public File getFile() {
            return file;
        }
    }
}


















