package eu.senla.course.util;

import eu.senla.course.api.IEntity;

import java.util.List;

public class ListUtil {
    public static <T extends IEntity> void shiftIndex(List<T> list){
        for (T element : list) {
            int index = list.indexOf(element) + 1;
            if (element.getId() != index) {
                element.setId(index);
            }
        }
    }
}
