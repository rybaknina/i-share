package eu.senla.course.tank.line;

import eu.senla.course.tank.ILineStep;
import eu.senla.course.tank.IProductPart;
import eu.senla.course.tank.entity.Engine;

public class EngineLineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Engine building ...");
        return new Engine();
    }
}
