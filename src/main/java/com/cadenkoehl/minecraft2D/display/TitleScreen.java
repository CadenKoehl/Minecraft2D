package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.client.GameClient;
import com.cadenkoehl.minecraft2D.client.Minecraft2D;

import java.util.ArrayList;
import java.util.List;

public class TitleScreen {

    private final List<MenuButton> buttons;

    public TitleScreen(GameClient game) {
        this.buttons = new ArrayList<>();

        MenuButton singleplayer = new MenuButton("Singleplayer", GameFrame.WIDTH / 3, GameFrame.HEIGHT / 3, 300, 100);
        singleplayer.onClick(game::init);
        MenuButton multiplayer = new MenuButton("Multiplayer", GameFrame.WIDTH / 3, (GameFrame.HEIGHT / 3) + 100, 300, 100);

        this.add(singleplayer);
        this.add(multiplayer);
    }

    public void add(MenuButton button) {
        buttons.add(button);
    }

    public void render() {
        Minecraft2D.LOGO.getIcon().paintIcon(GameWindow.INSTANCE, GameWindow.GRAPHICS, 170, 20);
        for(MenuButton button : buttons) {
            button.render();
        }
    }
}
