package eu.senla.course.entity.comparator.mechanic;

import eu.senla.course.dto.mechanic.MechanicDto;

import java.util.Comparator;

public class ByAlphabet implements Comparator<MechanicDto> {
    @Override
    public int compare(MechanicDto o1, MechanicDto o2) {
        if (o1 != null && o2 != null) {
            return (o1.getName().compareTo(o2.getName()));
        } else if (o1 != null) {
            return 1;
        }
        return -1;
    }
}
