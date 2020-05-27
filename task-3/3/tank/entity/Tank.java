package tank.entity;

import tank.IProduct;
import tank.IProductPart;

public class Tank implements IProduct {
    private Hull hull;
    private Engine engine;
    private Turret turret;

    @Override
    public void installFirstPart(IProductPart firstProductPart) {
        this.hull = (Hull) firstProductPart;
        System.out.println("Hull was installed...");
    }

    @Override
    public void installSecondPart(IProductPart secondProductPart) {
        this.engine = (Engine) secondProductPart;
        System.out.println("Engine was installed...");
    }

    @Override
    public void installThirdPart(IProductPart thirdProductPart) {
        this.turret = (Turret) thirdProductPart;
        System.out.println("Turret was installed...");
    }
}
