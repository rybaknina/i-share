package eu.senla.course.controller;

import eu.senla.course.entity.ThreadIt;

/**
 * 1 вариант с одной нитью и двумя вызовами в main
 * Необходимые состояния:
 * - NEW,
 * - RUNNABLE,
 * - BLOCKED,
 * - WAITING,
 * - TIMED_WAITING,
 * - TERMINATED;
 */

public class ThreadItController {
    public static void threadIt(long timeOut) throws InterruptedException {
        Object lock = new Object();
        Thread thread = new ThreadIt(lock, timeOut);
        displayState(thread);

        synchronized (lock){
            thread.start();
            displayState(thread);

            lock.wait();
            displayState(thread);

            lock.notifyAll();
            displayState(thread);
        }

        try {
            thread.join();
        } catch (InterruptedException e) {
            System.err.println("Interrupted error occurred " + e.getMessage());
        }
        displayState(thread);

        thread.interrupt();

    }
    private static void displayState(Thread thread){
        System.out.println(thread.getName() + ". State: " +  thread.getState());
    }
}
