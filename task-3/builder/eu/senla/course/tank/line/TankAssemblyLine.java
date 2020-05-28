package eu.senla.course.tank.line;

import eu.senla.course.tank.IAssemblyLine;
import eu.senla.course.tank.IProduct;
import eu.senla.course.tank.entity.Engine;
import eu.senla.course.tank.entity.Hull;
import eu.senla.course.tank.entity.Tank;
import eu.senla.course.tank.entity.Turret;


public class TankAssemblyLine implements IAssemblyLine {
    private Hull hull;
    private Engine engine;
    private Turret turret;

    public TankAssemblyLine(Hull hull, Engine engine, Turret turret) {
        this.hull = hull;
        this.engine = engine;
        this.turret = turret;
    }

    @Override
    public Tank assembleProduct(IProduct tank) {

        System.out.println("Start of tank building.\n");

        tank.installFirstPart(hull);
        tank.installSecondPart(engine);
        tank.installThirdPart(turret);

        System.out.println("\nComplete the tank.");

        return (Tank) tank;
    }
}
