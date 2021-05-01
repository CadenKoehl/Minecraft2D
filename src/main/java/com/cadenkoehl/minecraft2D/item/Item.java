package com.cadenkoehl.minecraft2D.item;

import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Texture;

public class Item {

    public static final int ITEM_SIZE = 32;

    private final int maxStackSize;
    private final String displayName;
    private final String name;
    private final Texture texture;

    public Item(Settings settings) {
        this.maxStackSize = settings.maxStackSize;
        this.displayName = settings.displayName;
        this.name = settings.name;
        this.texture = new Texture("textures/items/" + name + ".png", 1);
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public ClickResult onClick(PlayerEntity player, Vec2d clickPos) {
        return ClickResult.FAILED;
    }

    public Texture getTexture() {
        return texture;
    }

    public enum ClickResult {
        SUCCESS,
        SHOULD_DECREMENT,
        FAILED,
    }

    public static class Settings {

        private int maxStackSize;
        private String displayName;
        private String name;

        public Settings(String displayName) {
            this.maxStackSize = 64;
            this.displayName = displayName;
            this.name = displayName.replace(" ", "_").toLowerCase();
        }

        public Settings maxStackSize(int maxStackSize) {
            if(maxStackSize > 64) throw new IllegalArgumentException("maxStackSize cannot be larger than 64!");
            this.maxStackSize = maxStackSize;
            return this;
        }
        public Settings displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }
        public Settings name(String name) {
            this.name = name;
            return this;
        }
    }
}