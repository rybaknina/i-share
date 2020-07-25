package eu.senla.course.entity;

import java.util.Calendar;

public class TimerThread implements Runnable{

    private int seconds;

    public TimerThread(int seconds) {
        this.seconds = seconds * 1000;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(Calendar.getInstance().getTime());
            try {
                Thread.sleep(seconds);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
