package com.cadenkoehl.minecraft2D.entities.player;

import com.cadenkoehl.minecraft2D.block.BlockState;
import com.cadenkoehl.minecraft2D.client.GameClient;
import com.cadenkoehl.minecraft2D.entities.Entity;
import com.cadenkoehl.minecraft2D.item.*;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;
import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.ListTag;
import net.querz.nbt.tag.StringTag;
import net.querz.nbt.tag.Tag;

import java.io.File;
import java.io.IOException;

public class PlayerEntity extends Entity {

    private final Inventory inventory;
    public boolean isInventoryOpen;
    public BlockState breakingBlock;
    public Texture skin;

    public PlayerEntity() {
        super("Player");
        this.inventory = new Inventory(this);
        this.skin = new Texture("textures/skin.png");
        this.setUuid(GameClient.getUUID());
    }

    @Override
    public void postSpawn() {
        this.loadData();
    }

    public void clickItem(Vec2d clickPos) {

        ItemStack item = inventory.getSelectedItem();
        if(item == null) return;

        Item.ClickResult result = item.getItem().onClick(this, item, clickPos);
        if(result == Item.ClickResult.SHOULD_DECREMENT) item.decrement();
    }

    @Override
    public CompoundTag getCompoundTag() {
        CompoundTag tag = super.getCompoundTag();
        tag.put("Inventory", this.getInventory().getTag());
        return tag;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean placeBlock(BlockState block) {
        if(distanceFrom(block.pos) <= getReach()) {
            if(this.getWorld().setBlock(block)) {
                block.getBlock().getBreakSound().play();
                return true;
            }
        }
        return false;
    }

    public void breakBlock(Vec2d pos) {
        if(distanceFrom(pos) <= getReach()) {
            BlockState block = this.getWorld().breakBlock(this, pos);
            if(block == null) return;

            LootTable lootTable = block.getBlock().getLootTable();
            if(lootTable == null) return;

            inventory.addItem(lootTable.dropItem());
        }
    }

    public void loadData() {
        File file = new File(this.getWorld().getRealm().getSave().getFile(), "playerdata/" + this.getUuid().toString() + ".dat");
        if(!file.exists()) return;

        NamedTag playerData;

        try {
            playerData = NBTUtil.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        CompoundTag playerTag = (CompoundTag) playerData.getTag();

        this.setScreenPos(Vec2d.toScreenPos(new Vec2d(playerTag.getInt("X"), playerTag.getInt("Y"))));
        this.setHealth(playerTag.getInt("Health"));

        ListTag<CompoundTag> inventoryTag = (ListTag<CompoundTag>) playerTag.get("Inventory");
        inventoryTag.forEach(item -> inventory.addItem(new ItemStack(item)));
    }

    public void saveData() {

        File dir = new File(this.getWorld().getRealm().getSave().getFile(), "playerdata/");
        dir.mkdirs();

        try {
            NBTUtil.write(this.getCompoundTag(), new File(dir, this.getUuid().toString() + ".dat"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getMaxDamageCooldown() {
        return 400;
    }

    @Override
    public Texture getTexture() {
        return skin;
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