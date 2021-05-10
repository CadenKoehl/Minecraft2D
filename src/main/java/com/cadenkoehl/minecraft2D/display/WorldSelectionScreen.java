package com.cadenkoehl.minecraft2D.display;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorldSelectionScreen {

    private final List<File> worldFiles = new ArrayList<>();

    public WorldSelectionScreen() {
        this.loadWorlds();
    }

    public void render() {

        for(File file : worldFiles) {

        }
    }

    private void loadWorlds() {
        File saves = new File("saves");
        if(!saves.exists()) return;

        for(String fileName : saves.list()) {
            File file = new File(saves, fileName);
            if(!file.isDirectory()) continue;

            worldFiles.add(file);
        }
    }

    public List<File> getWorldFiles() {
        return worldFiles;
    }
}
