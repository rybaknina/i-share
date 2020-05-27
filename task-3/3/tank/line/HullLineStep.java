package tank.line;

import tank.ILineStep;
import tank.IProductPart;
import tank.entity.Hull;

public class HullLineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Hull building ...");
        return new Hull();
    }
}
