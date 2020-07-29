package eu.senla.course.controller;

import eu.senla.course.entity.SomeThread;

public class ThreadTurnController {
    public static void treadInTurn() throws InterruptedException {
        Object lock = new Object();
        Thread firstThread = new SomeThread(lock, 1000);
        Thread secondThread = new SomeThread(lock, 2000);

        firstThread.start();
        secondThread.start();
        firstThread.join();
        secondThread.join();
    }
}
