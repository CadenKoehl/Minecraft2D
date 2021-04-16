package com.cadenkoehl.minecraft2D.entities;

import com.cadenkoehl.minecraft2D.physics.Location;

public class Camera {

    public static final Camera CAMERA = new Camera(0, 0);

    private Location location;

    public Camera(int posX, int posY) {
        location = new Location(posX, posY);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setLocation(int posX, int posY) {
        setLocation(new Location(posX, posY));
    }

    public Location getLocation() {
        return this.location;
    }

    public int getPosX() {
        return this.location.getX();
    }

    public int getPosY() {
        return this.location.getY();
    }

    public void centerOn(Entity entity) {
        this.setLocation(entity.getPosX(), entity.getPosY());
    }
}