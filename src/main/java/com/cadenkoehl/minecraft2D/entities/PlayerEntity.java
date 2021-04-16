package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.display.GamePanel;
import com.cadenkoehl.minecraft2D.physics.Location;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.world.World;

import java.awt.*;

public class PlayerEntity extends Entity {

    public char direction = 'X';
    private final World world;

    public PlayerEntity(Location location, World world) {
        super(location);
        this.world = world;
    }

    public PlayerEntity(int x, int y, World world) {
        super(new Location(x, y));
        this.world = world;
    }

    @Override
    public void render() {
        super.render();

        if(this.isCollidingWithBlock(world)) return;

        if(this.direction == 'L') {
            GamePanel.INSTANCE.repaint(new Rectangle(getPosX(), getPosY(), getWidth(), getHeight()));
            setPosX(this.getPosX() - 10);
            return;
        }

        if(this.direction == 'R') {
            GamePanel.INSTANCE.repaint(new Rectangle(getPosX(), getPosY(), getWidth() + 40, getHeight() + 40));
            setPosX(this.getPosX() + 10);
        }
    }

    @Override
    public Texture getTexture() {
        return new Texture("textures/skin.png");
    }

    @Override
    public int getMaxHealth() {
        return 20;
    }

    @Override
    public String getDisplayName() {
        return "Player";
    }
}