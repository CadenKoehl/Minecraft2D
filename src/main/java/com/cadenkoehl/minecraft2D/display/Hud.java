package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.GameClient;
import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.item.Item;
import com.cadenkoehl.minecraft2D.item.ItemStack;
import com.cadenkoehl.minecraft2D.item.crafting.Recipe;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.render.Texture;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cadenkoehl.minecraft2D.display.GameWindow.*;

public class Hud {

    public static final Texture HEART = new Texture("textures/gui/heart.png");
    public static boolean f3 = false;

    private final PlayerEntity player;
    public final Map<Vec2d, Recipe> displayingCraftableItems;

    public Hud(PlayerEntity player) {
        this.player = player;
        this.displayingCraftableItems = new HashMap<>();
    }

    public void update() {
        updateHP();
        updateF3();
        updateInventory();
    }

    private void updateHP() {
        for(int i = 1; i <= player.health; i++) {
            Renderer.render(HEART, i * 20, 10);
        }
    }

    private void updateInventory() {

        int i = 1;
        for(ItemStack stack : new ArrayList<>(player.getInventory().getItems())) {

            String count = stack == player.getInventory().getSelectedItem() ? stack.getCount() + " <" : String.valueOf(stack.getCount());

            Renderer.render(stack.getItem().getTexture(), 20, i * (Item.ITEM_SIZE + 10));
            GRAPHICS.setFont(new Font(null, Font.PLAIN, 10));
            GRAPHICS.drawString(count, 20 + Item.ITEM_SIZE, (i * (Item.ITEM_SIZE + 10)) + (Item.ITEM_SIZE / 2) + 4);
            i++;
        }

        if(player.isInventoryOpen) {

            GRAPHICS.setFont(new Font("Minecrafter", Font.BOLD, 40));
            GRAPHICS.drawString("Craftable Items", (GameFrame.WIDTH / 2) - 200, 100);

            displayingCraftableItems.clear();
            int j = 1;
            for(Recipe recipe : player.getInventory().getCraftableItems()) {
                int x = (j * (Item.ITEM_SIZE + 10)) + (GameFrame.WIDTH / 2) - 200;
                int y = 120;
                Renderer.render(recipe.getResult().getTexture(), x, y);
                displayingCraftableItems.put(new Vec2d(x, y), recipe);
                j++;
            }
            if(j == 1) {
                GRAPHICS.drawString("<NONE>", (GameFrame.WIDTH / 2) - 100, 200);
            }
        }

        else {
            ItemStack item = player.getInventory().getSelectedItem();
            if (item == null) return;

            Vec2d mouse = GameClient.getMousePos();

            if (mouse == null) return;

            Renderer.render(item.getItem().getTexture().rescale(16), mouse.x, mouse.y);
        }
    }

    private void updateF3() {
        if(f3) {
            GRAPHICS.drawString("fps: " + GameClient.getFPS(), 25, 50);
            GRAPHICS.drawString("x: " + player.pos.x, 25, 70);
            GRAPHICS.drawString("y: " + ((-player.pos.y) + 10), 25, 90);
            GRAPHICS.drawString("entities: " + (player.getWorld().getEntities().size()), 25, 110);
            GRAPHICS.drawString("day " + (GameClient.getOverworld().days), 25, 130);
        }
    }
}