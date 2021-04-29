package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.Minecraft2D;

import java.util.ArrayList;
import java.util.List;

public class TitleScreen {

    private final List<MenuButton> buttons;

    public TitleScreen() {
        this.buttons = new ArrayList<>();
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
