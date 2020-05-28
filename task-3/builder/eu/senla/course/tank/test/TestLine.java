package eu.senla.course.tank.test;

import eu.senla.course.tank.IProductPart;
import eu.senla.course.tank.line.TankAssemblyLine;
import eu.senla.course.tank.entity.Tank;
import eu.senla.course.tank.line.EngineLineStep;
import eu.senla.course.tank.IAssemblyLine;
import eu.senla.course.tank.line.HullLineStep;
import eu.senla.course.tank.line.TurretLineStep;

/**
 * @author Nina Rybak
 * Программа конструирует сборочную линию со всеми ее шагами
 * и тестирует ее запуском одного продукта на сборку
 */

public class TestLine {
    public static void main(String[] args) {

        IProductPart hull = new HullLineStep().buildProductPart();
        IProductPart engine = new EngineLineStep().buildProductPart();
        IProductPart turret = new TurretLineStep().buildProductPart();

        IAssemblyLine line = new TankAssemblyLine(hull, engine, turret);

        line.assembleProduct(new Tank());
    }
}
