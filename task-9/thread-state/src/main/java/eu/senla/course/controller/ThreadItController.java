package eu.senla.course.controller;

import eu.senla.course.entity.ThreadIt;

/**
 * Вариант с одной нитью и двумя объектами
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
        Thread thread = new ThreadIt(lock, 0);
        Thread threadOther = new ThreadIt(lock, timeOut); // second object

        synchronized (lock){
            displayState(thread);

            thread.start();
            threadOther.start();
            displayState(thread);

            lock.wait(); // WAITING
            displayState(thread);

            lock.notifyAll();
            displayState(thread);

            lock.wait(1000); // TIMED_WAITING
            displayState(threadOther);

            lock.notify();
        }

        try {
            thread.join();
        } catch (InterruptedException e) {
            System.err.println("Interrupted error occurred " + e.getMessage());
        }

        displayState(thread);
        thread.interrupt();
        threadOther.interrupt();

    }
    private static void displayState(Thread thread){
        System.out.println(thread.getName() + ". State: " +  thread.getState());
    }
}
