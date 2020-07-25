package eu.senla.course.controller;

import eu.senla.course.entity.ThreadState;
import eu.senla.course.entity.ThreadWait;

/**
 * 1 вариант с двумя нитями
 * Необходимые состояния:
 * - NEW,
 * - RUNNABLE,
 * - BLOCKED,
 * - WAITING,
 * - TIMED_WAITING,
 * - TERMINATED;
 */

public class ThreadStateController {
    public static void threadState() throws InterruptedException {
        Thread thread = new ThreadState();
        displayState(thread);

        thread.start();
        displayState(thread);

        Thread threadOther = new ThreadWait();
        threadOther.start();

        synchronized (threadOther) {
            Thread.sleep(10);
            displayState(threadOther);
        }

        Thread.sleep(10);
        displayState(threadOther);

        Thread.sleep(10);
        displayState(thread);

        thread.join();
        displayState(thread);

        threadOther.interrupt();
    }
    private static void displayState(Thread thread){
        System.out.println(thread.getName() + ". State: " +  thread.getState());
    }
}
