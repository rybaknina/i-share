package eu.senla.course.tank.entity;

import eu.senla.course.tank.IProduct;
import eu.senla.course.tank.IProductPart;

public class Tank implements IProduct {
    public Hull hull;
    protected Engine engine;
    protected Turret turret;

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
