package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.Game;
import com.cadenkoehl.minecraft2D.GameState;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.entities.mob.LivingEntity;
import com.cadenkoehl.minecraft2D.item.Item;
import com.cadenkoehl.minecraft2D.item.ItemStack;
import com.cadenkoehl.minecraft2D.item.crafting.Recipe;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Renderer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Map;

public class InputManager {
    private final Game game;

    public InputManager(Game game) {
        this.game = game;
    }

    public void onKeyPressed(KeyEvent event) {
        if(game.state != GameState.GAME) return;

        boolean isNumberKey;
        int numberKey = 0;

        try {
            numberKey = Integer.parseInt(String.valueOf(event.getKeyChar()));
            isNumberKey = true;
        }
        catch (NumberFormatException ex) {
            isNumberKey = false;
        }

        if(isNumberKey) {
            game.player.getInventory().setSelectedItemSlot(numberKey - 1);
            return;
        }

        switch (event.getKeyCode()) {

            //Movement
            case KeyEvent.VK_A -> {
                if(game.player.isInventoryOpen) return;
                if (event.isAltDown()) game.player.setVelocityX(-2);
                else game.player.setVelocityX(-1);
            }
            case KeyEvent.VK_D -> {
                if(game.player.isInventoryOpen) return;

                if (event.isAltDown()) game.player.setVelocityX(2);
                else game.player.setVelocityX(1);
            }
            case KeyEvent.VK_SPACE -> {
                if(game.player.isInventoryOpen) return;
                game.player.jump();
            }

            //Inventory
            case KeyEvent.VK_E -> game.player.isInventoryOpen = !game.player.isInventoryOpen;

            //Debug Controls
            case KeyEvent.VK_F3 -> Hud.f3 = !Hud.f3;
            case KeyEvent.VK_F2 -> game.getWindow().takeScreenshot();
        }
    }

    public void onKeyReleased(KeyEvent event) {
        if(game.state != GameState.GAME) return;

        switch (event.getKeyCode()) {
            case KeyEvent.VK_A, KeyEvent.VK_D -> game.player.setVelocityX(0);
        }
    }

    public void onMouseClicked(MouseEvent event) {
        if(game.state != GameState.GAME) return;

        Vec2d pos = Vec2d.toGamePos(new Vec2d(event.getX() + Renderer.CAMERA.offset.x, event.getY() + Renderer.CAMERA.offset.y));

        switch (event.getButton()) {
            case MouseEvent.BUTTON3 -> {
                if(!game.player.isInventoryOpen) game.player.clickItem(pos);
            }
            case MouseEvent.BUTTON1 -> {

                if(game.player.isInventoryOpen) {

                    for(Map.Entry<Vec2d, Recipe> recipeEntry : game.hud.displayingCraftableItems.entrySet()) {

                        Recipe recipe = recipeEntry.getValue();
                        Item item = recipe.getResult();

                        if(event.getX() >= recipeEntry.getKey().x
                                && event.getX() <= recipeEntry.getKey().x + item.getTexture().getWidth()
                                && event.getY() >= recipeEntry.getKey().y
                                && event.getY() <= recipeEntry.getKey().y + item.getTexture().getHeight()) {

                            game.player.getInventory().addItem(new ItemStack(item, recipe.getResultCount()));
                            for(Item ingredient : recipe.getRequiredItems()) {
                                game.player.getInventory().decrement(ingredient);
                            }
                        }
                    }
                }
                else {
                    Tile entity = game.currentWorld.getEntity(pos);

                    if (entity == null) {
                        entity = game.currentWorld.getEntity(new Vec2d(pos.x - 1, pos.y));
                    }

                    if (entity instanceof LivingEntity) {
                        game.player.tryAttack((LivingEntity) entity);
                        return;
                    }
                    game.player.breakBlock(pos);
                }
            }
        }
    }

    public Vec2d getMousePosition() {
        Point pos = GameWindow.INSTANCE.getMousePosition();
        if (pos == null) return null;
        return new Vec2d(pos.x, pos.y);
    }
}
