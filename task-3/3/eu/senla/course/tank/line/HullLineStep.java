package eu.senla.course.tank.line;

import eu.senla.course.tank.ILineStep;
import eu.senla.course.tank.IProductPart;
import eu.senla.course.tank.entity.Hull;

public class HullLineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Hull building ...");
        return new Hull();
    }
}
