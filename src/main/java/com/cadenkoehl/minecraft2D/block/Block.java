package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.item.BlockItem;
import com.cadenkoehl.minecraft2D.item.Item;
import com.cadenkoehl.minecraft2D.item.Items;
import com.cadenkoehl.minecraft2D.item.LootTable;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.sound.SoundEvent;
import com.cadenkoehl.minecraft2D.sound.SoundEvents;

import java.util.ArrayList;
import java.util.List;

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
    private final boolean defaultCanCollide;
    private final boolean affectedByGravity;
    private final LootTable lootTable;

    public Block(Settings settings) {
        this.name = settings.name;
        this.displayName = settings.displayName;
        this.blockItem = Items.register(new BlockItem(new Item.Settings(displayName), this));
        this.texture = new Texture("textures/blocks/" + name + ".png");
        this.breakSpeed = settings.breakSpeed;
        this.breakSound = settings.breakSound;
        this.defaultCanCollide = settings.defaultCanCollide;
        this.affectedByGravity = settings.affectedByGravity;
        List<LootTable.Entry> entries = new ArrayList<>(settings.lootTableEntries);
        entries.add(new LootTable.Entry(blockItem, 1, 1, 1));
        this.lootTable = new LootTable(entries);
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

    public boolean getDefaultCanCollide() {
        return defaultCanCollide;
    }

    public boolean isAffectedByGravity() {
        return affectedByGravity;
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public boolean canBeMined() {
        return breakSpeed >= 0;
    }

    public static Texture getBreakTexture(int breakLevel) {
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

        private boolean defaultCanCollide;
        private boolean affectedByGravity;

        private List<LootTable.Entry> lootTableEntries;

        public Settings(String displayName) {
            this.displayName = displayName;
            this.name = displayName.replace(" ", "_").toLowerCase();
            this.breakSpeed = 3;
            this.breakSound = SoundEvents.STONE_BREAK;
            this.defaultCanCollide = true;
            this.lootTableEntries = new ArrayList<>();
        }

        public Settings breakSpeed(int breakSpeed) {
            this.breakSpeed = breakSpeed;
            return this;
        }

        public Settings breakSound(SoundEvent breakSound) {
            this.breakSound = breakSound;
            return this;
        }

        public Settings defaultCanCollide(boolean defaultCanCollide) {
            this.defaultCanCollide = defaultCanCollide;
            return this;
        }

        public Settings affectedByGravity(boolean affectedByGravity) {
            this.affectedByGravity = affectedByGravity;
            return this;
        }

        public Settings addDrop(LootTable.Entry entry) {
            lootTableEntries.add(entry);
            return this;
        }
    }
}
