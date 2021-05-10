package com.cadenkoehl.minecraft2D.item;

import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;

public class FoodItem extends Item {

    private final int restoredHealth;

    public FoodItem(FoodItem.Settings settings) {
        super(settings);
        this.restoredHealth = settings.restoredHealth;
    }

    @Override
    public ClickResult onClick(PlayerEntity player, ItemStack stack, Vec2d clickPos) {
        if(player.heal(restoredHealth)) return ClickResult.SHOULD_DECREMENT;
        else return ClickResult.FAILED;
    }

    public static class Settings extends Item.Settings {

        private int restoredHealth;

        public Settings(String displayName) {
            super(displayName);
        }

        public Settings restoredHealth(int restoredHealth) {
            this.restoredHealth = restoredHealth;
            return this;
        }
    }
}
