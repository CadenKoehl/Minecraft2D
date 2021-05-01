package com.cadenkoehl.minecraft2D.util;

import java.util.Timer;
import java.util.TimerTask;

public class Util {
    public static void scheduleTask(Runnable task, long delay) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, delay);
    }
    /**
     * @param chance The probability that this method will return true
     * @return true or false
     */
    public static boolean random(int chance) {
        int random = (int) Math.round(Math.random() * Math.abs(chance));
        return random == 1;
    }
}
