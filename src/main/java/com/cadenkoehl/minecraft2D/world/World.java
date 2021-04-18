package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.util.LogLevel;
import com.cadenkoehl.minecraft2D.util.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class World {

    /**
     * Controls if this world should generate like the nether
     */
    private boolean isCaveWorld = false;

    private int groundHeight = 700;

    private final List<Block> blocks = new ArrayList<>();

    public abstract String getDisplayName();
    public abstract Block getSurfaceBlock();


    /**
     * This method is called when the world loads
     */
    public void load() {}

    /**
     * @return a block from a given vec2d
     */
    public Block getBlock(Vec2d vec2d) {
        for(Block block : blocks) {
            if(block.getLocation() == vec2d) return block;
        }
        return null;
    }

    public List<Block> getBlocks() {
        return blocks;
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
        return this.getBlock(new Vec2d(x, y));
    }

    public void setBlock(Block block) {
        block = block.copy();
        block.setWorld(this);
        if(block.pos == null) throw new IllegalStateException("Block has no state yet!");
        blocks.add(block);
    }

    public void setBlock(Block block, Vec2d pos) {
        block.setPos(pos);
        this.setBlock(block);
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
