package eu.senla.course.entity;

import java.util.LinkedList;

public class Consumer implements Runnable {
    private final LinkedList<Double> buffer;

    public Consumer(LinkedList<Double> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Consumed: " + consume());
            } catch (InterruptedException e) {
                System.err.println("Interrupted error occurred " + e.getMessage());
                break;
            }
        }
    }
    private Double consume() throws InterruptedException {
        synchronized (buffer) {
            if (buffer.isEmpty()) {
                System.out.println("Buffer is empty. Consumer is waiting");
                buffer.wait();
            }

            buffer.notifyAll();
            return buffer.poll();
        }
    }
}
