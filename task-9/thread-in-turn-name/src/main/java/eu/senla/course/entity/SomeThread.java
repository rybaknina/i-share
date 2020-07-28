package eu.senla.course.entity;

public class SomeThread extends Thread {

    private Object lock;
    private long timeOut;

    public SomeThread(Object lock, long timeOut){
        this.lock = lock;
        this.timeOut = timeOut;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                try {

                    System.out.println(getName());
                    Thread.sleep(timeOut); // for presentation purposes
                    lock.wait(100);

                } catch (InterruptedException e) {
                    System.err.println("Interrupted error occurred " + e.getMessage());
                    break;
                }
            }
        }
    }
}
