package com.cadenkoehl.minecraft2D.util;

import java.util.Timer;
import java.util.TimerTask;

public class TimeUtil {
    public static void scheduleTask(Runnable task, long delay) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, delay);
    }
}
