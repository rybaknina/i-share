package eu.senla.course.controller;

import eu.senla.course.entity.Consumer;
import eu.senla.course.entity.Producer;

import java.util.LinkedList;

public class BufferController {

    public static void emulator(int size) {
        LinkedList<Double> buffer = new LinkedList<>();

        new Thread(new Producer(buffer, size)).start();
        new Thread(new Consumer(buffer)).start();
    }
}
