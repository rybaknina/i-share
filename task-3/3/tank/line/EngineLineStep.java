package tank.line;

import tank.ILineStep;
import tank.IProductPart;
import tank.entity.Engine;

public class EngineLineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Engine building ...");
        return new Engine();
    }
}
