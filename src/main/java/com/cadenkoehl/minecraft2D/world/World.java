package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.physics.Location;
import com.cadenkoehl.minecraft2D.util.LogLevel;
import com.cadenkoehl.minecraft2D.util.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class World {

    /**
     * Controls if this world should generate like the nether
     */
    private boolean isCaveWorld = false;

    private int groundHeight = 700;

    public Map<Location, Block> blocks = new HashMap<>();

    public abstract String getDisplayName();
    public abstract Block getSurfaceBlock();


    /**
     * This method is called when the world loads
     */
    public void load() {}

    /**
     * @return a block from a given location
     */
    public Block getBlock(Location location) {
        return blocks.get(location);
    }

    /**
     * @return a lowercase version of this world's name with spaces replaced by underscores and
     */
    public String getName() {
        return this.getDisplayName().toLowerCase().replace(" ", "_");
    }


    /**
     * @return the file of this world
     */
    public File getFile() {
        File file = new File(this.getName() + ".json");
        try {
            if(file.createNewFile()) {
                Logger.log(LogLevel.INFO, "Created world file for " + this.getDisplayName());
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return file;
    }

    /**
     * @return a block from a given location
     */
    public Block getBlock(int x, int y) {
        return this.getBlock(new Location(x, y));
    }


    public void setCaveWorld(boolean caveWorld) {
        this.isCaveWorld = caveWorld;
    }

    public boolean isCaveWorld() {
        return this.isCaveWorld;
    }

    public void setGroundHeight(int groundHeight) {
        this.groundHeight = groundHeight;
    }

    public int getGroundHeight() {
        return this.groundHeight;
    }
}
