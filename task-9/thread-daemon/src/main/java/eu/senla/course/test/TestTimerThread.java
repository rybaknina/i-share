package eu.senla.course.test;

import eu.senla.course.controller.TimerController;

public class TestTimerThread {
    public static void main(String[] args) throws InterruptedException {
        TimerController.everyNSecondsTimer(2, 30000);
    }
}
