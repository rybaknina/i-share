package eu.senla.course.entity;

public class ThreadState extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.err.println(this.getName() + " interrupted");
        }
    }

}
