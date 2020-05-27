package tank.line;

import tank.ILineStep;
import tank.IProductPart;
import tank.entity.Turret;

public class TurretLineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Turret building ...");
        return new Turret();
    }
}
