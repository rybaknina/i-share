package eu.senla.course.entity.comparator.mechanic;

import eu.senla.course.entity.Mechanic;

import java.util.Comparator;

public class ByBusy implements Comparator<Mechanic> {
    @Override
    public int compare(Mechanic o1, Mechanic o2) {
        if (o1 != null && o2 != null) {
            if ((o1.isMechanicFree() && o2.isMechanicFree()) || (!o1.isMechanicFree() && !o2.isMechanicFree())) {
                return 0;
            } else if (!o1.isMechanicFree() && o2.isMechanicFree()) {
                return 1;
            } else {
                return -1;
            }
        } else if (o1 != null) {
            return 1;
        }
        return -1;
    }
}
