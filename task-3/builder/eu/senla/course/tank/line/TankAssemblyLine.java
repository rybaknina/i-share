package eu.senla.course.tank.line;

import eu.senla.course.tank.IAssemblyLine;
import eu.senla.course.tank.IProduct;
import eu.senla.course.tank.IProductPart;

public class TankAssemblyLine implements IAssemblyLine {
    private IProductPart hull;
    private IProductPart engine;
    private IProductPart turret;

    public TankAssemblyLine(IProductPart hull, IProductPart engine, IProductPart turret) {
        this.hull = hull;
        this.engine = engine;
        this.turret = turret;
    }

    @Override
    public IProduct assembleProduct(IProduct tank) {

        System.out.println("\nStart of tank building.\n");

        tank.installFirstPart(hull);
        tank.installSecondPart(engine);
        tank.installThirdPart(turret);

        System.out.println("\nComplete the tank.");

        return tank;
    }
}
