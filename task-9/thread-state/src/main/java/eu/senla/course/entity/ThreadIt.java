package eu.senla.course.entity;

public class ThreadIt extends Thread{
    private Object lock;
    private long timeOut;

    public ThreadIt(Object lock, long timeOut) {
        this.lock = lock;
        this.timeOut = timeOut;
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                lock.notifyAll();
                lock.wait(timeOut);
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted error occurred " + e.getMessage());
        }
    }
}
