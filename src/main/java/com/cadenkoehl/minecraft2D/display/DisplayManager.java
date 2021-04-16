package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.entities.PlayerEntity;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.util.LogLevel;
import com.cadenkoehl.minecraft2D.util.Logger;
import com.cadenkoehl.minecraft2D.world.Overworld;
import com.cadenkoehl.minecraft2D.world.gen.TerrainGenerator;

public class DisplayManager {

    private GameFrame frame;

    public DisplayManager() {
        Logger.log(LogLevel.INFO, "Starting game!");
    }

    public void createDisplay() {
        Blocks.registerBlocks();
        this.frame = new GameFrame();
    }
    public void closeDisplay() {
        frame.setVisible(false);
    }
}
