package eu.senla.course.entity.comparator.mechanic;

import eu.senla.course.entity.Mechanic;

import java.util.Comparator;

public class ByAlphabet implements Comparator<Mechanic> {
    @Override
    public int compare(Mechanic o1, Mechanic o2) {
        if (o1 != null && o2 != null){
            return (o1.getName().compareTo(o2.getName()));
        }
        else if (o1 != null) {
            return 1;
        }
        return -1;
    }
}
