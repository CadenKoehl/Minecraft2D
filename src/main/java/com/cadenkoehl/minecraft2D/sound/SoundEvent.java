package com.cadenkoehl.minecraft2D.sound;

import com.cadenkoehl.minecraft2D.util.Util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SoundEvent {

    private final String id;
    private Clip clip;

    public SoundEvent(String id) {
        this.id = id;
    }

    public void play() {
        File file = new File("sound/" + id + ".wav");
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            this.clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        clip.stop();
        clip.close();
    }
}
