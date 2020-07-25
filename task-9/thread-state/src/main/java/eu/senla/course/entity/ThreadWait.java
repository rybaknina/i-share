package eu.senla.course.entity;

public class ThreadWait extends Thread {
    @Override
    public void run() {
        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException e) {
            System.err.println(this.getName() + " interrupted");
        }
    }

}
