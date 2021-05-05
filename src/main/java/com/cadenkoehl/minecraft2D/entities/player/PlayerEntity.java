package com.cadenkoehl.minecraft2D.entities.player;

import com.cadenkoehl.minecraft2D.block.BlockState;
import com.cadenkoehl.minecraft2D.entities.mob.LivingEntity;
import com.cadenkoehl.minecraft2D.item.Inventory;
import com.cadenkoehl.minecraft2D.item.Item;
import com.cadenkoehl.minecraft2D.item.ItemStack;
import com.cadenkoehl.minecraft2D.item.Items;
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

public class PlayerEntity extends LivingEntity {

    public final Vec2d originalPos;
    private final Inventory inventory;
    public boolean isInventoryOpen;
    public BlockState breakingBlock;
    public Texture skin;

    public PlayerEntity(String username, Vec2d vec2d, World world) {
        super(vec2d, world, username);
        this.originalPos = vec2d;
        this.inventory = new Inventory(this);
        this.skin = new Texture("textures/skin.png");
        this.loadInventory();
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

    public boolean placeBlock(BlockState block) {
        if(distanceFrom(block.pos) <= getReach()) {
            if(this.getWorld().setBlock(block)) {
                block.getBlock().getBreakSound().play();
            }
            return true;
        }
        return false;
    }

    public void breakBlock(Vec2d pos) {
        if(distanceFrom(pos) <= getReach()) {
            BlockState block = this.getWorld().breakBlock(this, pos);
            if(block == null) return;

            inventory.addItem(new ItemStack(block.getItem()));
        }
    }

    public void loadInventory() {

        File file = new File("data/inventory.dat");
        if(!file.exists()) return;

        Tag<?> invTag;

        try {
            invTag = NBTUtil.read(file).getTag();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if(invTag instanceof CompoundTag) {
            CompoundTag tag = (CompoundTag) invTag;
            for(StringTag stringTag : (ListTag<StringTag>) tag.get("Items")) {

                String[] splitTag = stringTag.getValue().split(":");

                inventory.addItem(new ItemStack(Items.valueOf(splitTag[0]), Integer.parseInt(splitTag[1])));
            }
        }
    }

    public void saveInventory() {

        CompoundTag tag = new CompoundTag();
        ListTag<StringTag> items = new ListTag<>(StringTag.class);

        for(ItemStack stack : inventory) {
            StringTag name = new StringTag();
            name.setValue(stack.getItem().getName() + ":" + stack.getCount());
            items.add(name);
        }

        tag.put("Items", items);

        try {
            NBTUtil.write(tag, "data/inventory.dat");
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