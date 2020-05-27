package eu.senla.course.tank.line;

import eu.senla.course.tank.ILineStep;
import eu.senla.course.tank.IProductPart;
import eu.senla.course.tank.entity.Turret;

public class TurretLineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Turret building ...");
        return new Turret();
    }
}
