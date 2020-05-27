package tank.line;

import tank.IAssemblyLine;
import tank.IProduct;
import tank.entity.Engine;
import tank.entity.Hull;
import tank.entity.Turret;

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
