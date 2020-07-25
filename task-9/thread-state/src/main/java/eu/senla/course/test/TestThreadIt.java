package eu.senla.course.test;

import eu.senla.course.controller.ThreadItController;

public class TestThreadIt {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("With WAITING");
        ThreadItController.threadIt(0);

        System.out.println("\nWith TIMED_WAITING");
        ThreadItController.threadIt(300);
    }
}
