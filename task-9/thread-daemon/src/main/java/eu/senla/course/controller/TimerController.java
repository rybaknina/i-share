package eu.senla.course.controller;

import eu.senla.course.entity.TimerThread;

public class TimerController {
    public static void everyNSecondsTimer(int seconds, long timeOut) throws InterruptedException {
        Thread thread = new Thread(new TimerThread(seconds));
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(timeOut);
    }
}
