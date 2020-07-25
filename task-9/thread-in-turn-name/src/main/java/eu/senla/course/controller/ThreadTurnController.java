package eu.senla.course.controller;

import eu.senla.course.entity.SomeThread;

public class ThreadTurnController {
    public static void treadInTurn(){
        Object lock = new Object();
        new SomeThread(lock).start();
        new SomeThread(lock).start();
    }
}
