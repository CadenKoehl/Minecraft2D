package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.item.BlockItem;
import com.cadenkoehl.minecraft2D.item.Item;
import com.cadenkoehl.minecraft2D.item.Items;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.sound.SoundEvent;
import com.cadenkoehl.minecraft2D.sound.SoundEvents;

public class Block {

    private static final Texture BREAK_0 = new Texture("textures/blocks/destroy_stage_0.png");
    private static final Texture BREAK_1 = new Texture("textures/blocks/destroy_stage_1.png");
    private static final Texture BREAK_2 = new Texture("textures/blocks/destroy_stage_2.png");
    private static final Texture BREAK_3 = new Texture("textures/blocks/destroy_stage_3.png");
    private static final Texture BREAK_4 = new Texture("textures/blocks/destroy_stage_4.png");
    private static final Texture BREAK_5 = new Texture("textures/blocks/destroy_stage_5.png");
    private static final Texture BREAK_6 = new Texture("textures/blocks/destroy_stage_6.png");
    private static final Texture BREAK_7 = new Texture("textures/blocks/destroy_stage_7.png");
    private static final Texture BREAK_8 = new Texture("textures/blocks/destroy_stage_8.png");
    private static final Texture BREAK_9 = new Texture("textures/blocks/destroy_stage_9.png");

    private final String name;
    private final String displayName;
    private final Texture texture;
    private final Item blockItem;
    private final int breakSpeed;
    private final SoundEvent breakSound;

    public Block(Settings settings) {
        this.name = settings.name;
        this.displayName = settings.displayName;
        this.blockItem = new BlockItem(new Item.Settings(displayName), this);
        this.texture = new Texture("textures/blocks/" + name + ".png");
        this.breakSpeed = settings.breakSpeed;
        this.breakSound = settings.breakSound;
        Items.register(blockItem);
    }

    public Texture getTexture() {
        return texture;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Item getBlockItem() {
        return blockItem;
    }

    public int getBreakSpeed() {
        return breakSpeed;
    }

    public SoundEvent getBreakSound() {
        return breakSound;
    }

    public boolean canBeMined() {
        return breakSpeed >= 0;
    }

    public static Texture breakTexture(int breakLevel) {
        return switch (breakLevel) {
            case 0 -> BREAK_0;
            case 1 -> BREAK_1;
            case 2 -> BREAK_2;
            case 3 -> BREAK_3;
            case 4 -> BREAK_4;
            case 5 -> BREAK_5;
            case 6 -> BREAK_6;
            case 7 -> BREAK_7;
            case 8 -> BREAK_8;
            default -> BREAK_9;
        };
    }

    public static class Settings {

        private final String displayName;
        private final String name;

        private int breakSpeed;
        private SoundEvent breakSound;

        public Settings(String displayName) {
            this.displayName = displayName;
            this.name = displayName.replace(" ", "_").toLowerCase();
            this.breakSpeed = 3;
            this.breakSound = SoundEvents.STONE_BREAK;
        }

        public Settings breakSpeed(int breakSpeed) {
            this.breakSpeed = breakSpeed;
            return this;
        }

        public Settings breakSound(SoundEvent breakSound) {
            this.breakSound = breakSound;
            return this;
        }
    }
}
