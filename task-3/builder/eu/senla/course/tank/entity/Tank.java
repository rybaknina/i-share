package eu.senla.course.tank.entity;

import eu.senla.course.tank.IProduct;
import eu.senla.course.tank.IProductPart;

public class Tank implements IProduct {
    private IProductPart hull;
    private IProductPart engine;
    private IProductPart turret;

    @Override
    public void installFirstPart(IProductPart firstProductPart) {
        this.hull = firstProductPart;
        System.out.println("Hull was installed...");
    }

    @Override
    public void installSecondPart(IProductPart secondProductPart) {
        this.engine = secondProductPart;
        System.out.println("Engine was installed...");
    }

    @Override
    public void installThirdPart(IProductPart thirdProductPart) {
        this.turret = thirdProductPart;
        System.out.println("Turret was installed...");
    }
}
