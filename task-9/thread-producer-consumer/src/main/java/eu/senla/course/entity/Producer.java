package eu.senla.course.entity;

import java.util.LinkedList;
import java.util.Random;

public class Producer implements Runnable{
    private final LinkedList<Double> buffer;
    private final int size;

    public Producer(LinkedList<Double> buffer, int size) {
        this.buffer = buffer;
        this.size = size;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Produced: " + produce());
            } catch (InterruptedException e) {
                System.err.println("Interrupted error occurred " + e.getMessage());
                break;
            }
        }
    }

    private double produce() throws InterruptedException {
        synchronized (buffer) {
            while (buffer.size() == size) {
                System.out.println("Buffer is full. Producer is waiting");
                buffer.wait();
            }

            double random = new Random().nextDouble();
            buffer.add(random);
            buffer.notifyAll();

            return random;
        }
    }
}
