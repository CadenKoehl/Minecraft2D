package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.GameClient;
import com.cadenkoehl.minecraft2D.GameState;
import com.cadenkoehl.minecraft2D.physics.Vec2d;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuButton {

    private final String text;
    private final Vec2d pos;
    private final int width;
    private final int height;
    private Runnable onClick;

    public MenuButton(String text, int x, int y, int width, int height) {
        this.text = text;
        this.pos = new Vec2d(x, y);
        this.width = width;
        this.height = height;
        GameWindow.INSTANCE.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(onClick != null) {
                    if(e.getX() >= x && e.getX() <= x + width && e.getY() >= y && e.getY() <= y + height && GameClient.getState() == GameState.TITLE_SCREEN) onClick.run();
                }
            }
        });
    }

    public void render() {
        GameWindow.GRAPHICS.setColor(Color.DARK_GRAY);
        GameWindow.GRAPHICS.fillRect(pos.x, pos.y, width, height);
        GameWindow.GRAPHICS.setFont(new Font("Minecrafter", Font.BOLD, width / 10));
        GameWindow.GRAPHICS.setColor(Color.BLACK);
        GameWindow.GRAPHICS.drawString(text, (pos.x + width / 8) - 2, (pos.y + height / 2 + height / 10) - 2);
        GameWindow.GRAPHICS.setColor(Color.WHITE);
        GameWindow.GRAPHICS.drawRect(pos.x, pos.y, width, height);
        GameWindow.GRAPHICS.drawString(text, pos.x + width / 8, pos.y + height / 2 + height / 10);
    }

    public void onClick(Runnable onClick) {
        this.onClick = onClick;
    }
}
