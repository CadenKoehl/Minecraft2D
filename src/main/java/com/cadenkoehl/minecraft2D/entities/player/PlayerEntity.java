package com.cadenkoehl.minecraft2D.entities.player;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.display.Hud;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.entities.mob.LivingEntity;
import com.cadenkoehl.minecraft2D.item.Inventory;
import com.cadenkoehl.minecraft2D.item.Item;
import com.cadenkoehl.minecraft2D.item.ItemStack;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

import java.util.List;

public class PlayerEntity extends LivingEntity {

    public final Vec2d originalPos;
    private final Inventory inventory;
    public boolean isInventoryOpen;

    public PlayerEntity(String username, Vec2d vec2d, World world) {
        super(vec2d, world, username);
        this.originalPos = vec2d;
        this.inventory = new Inventory(this);
    }

    public void clickItem(Vec2d clickPos) {

        ItemStack item = inventory.getSelectedItem();
        if(item == null) return;

        Item.ClickResult result = item.getItem().onClick(this, clickPos);
        if(result == Item.ClickResult.SHOULD_DECREMENT) item.decrement();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean placeBlock(Vec2d pos, Block block) {
        if(distanceFrom(pos) <= getReach()) {
            return this.getWorld().setBlock(block, pos, block.canCollide());
        }
        return false;
    }

    public void breakBlock(Vec2d pos) {
        if(distanceFrom(pos) <= getReach()) {
            Block block = this.getWorld().breakBlock(this, pos);
            if(block == null) return;

            inventory.addItem(new ItemStack(block.getItem()));
        }
    }

    @Override
    public int getMaxDamageCooldown() {
        return 400;
    }

    @Override
    public Texture getTexture() {
        return new Texture("textures/skin.png");
    }

    @Override
    public int getMaxHealth() {
        return 10;
    }

    @Override
    public int getBaseAttackDamage() {
        return 1;
    }

    @Override
    public String getDisplayName() {
        return "player";
    }
}