package eu.senla.course.tank.line;

import eu.senla.course.tank.IAssemblyLine;
import eu.senla.course.tank.IProduct;
import eu.senla.course.tank.entity.Engine;
import eu.senla.course.tank.entity.Hull;
import eu.senla.course.tank.entity.Turret;


public class AssemblyLine implements IAssemblyLine {
    private Hull hull;
    private Engine engine;
    private Turret turret;

    public AssemblyLine(Hull hull, Engine engine, Turret turret) {
        this.hull = hull;
        this.engine = engine;
        this.turret = turret;
    }

    @Override
    public IProduct assembleProduct(IProduct iProduct) {

        System.out.println("Start of tank building.\n");

        iProduct.installFirstPart(hull);
        iProduct.installSecondPart(engine);
        iProduct.installThirdPart(turret);

        System.out.println("\nComplete the tank.");

        return iProduct;
    }
}
