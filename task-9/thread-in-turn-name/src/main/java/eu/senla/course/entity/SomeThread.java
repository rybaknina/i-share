package eu.senla.course.entity;

public class SomeThread extends Thread {

    private Object lock;

    public SomeThread(Object lock){
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                try {
                    System.out.println(getName());
                    lock.notify();
                    lock.wait();
                } catch (InterruptedException e) {
                    System.err.println("Interrupted error occurred " + e.getMessage());
                    break;
                }
            }
        }
    }
}
