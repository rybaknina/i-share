package eu.senla.course.tank.test;

import eu.senla.course.tank.line.AssemblyLine;
import eu.senla.course.tank.entity.Tank;
import eu.senla.course.tank.line.EngineLineStep;
import eu.senla.course.tank.IAssemblyLine;
import eu.senla.course.tank.entity.Engine;
import eu.senla.course.tank.entity.Hull;
import eu.senla.course.tank.entity.Turret;
import eu.senla.course.tank.line.HullLineStep;
import eu.senla.course.tank.line.TurretLineStep;

/**
 * @author Nina Rybak
 * Программа конструирует сборочную линию со всеми ее шагами
 * и тестирует ее запуском одного продукта на сборку
 */

public class TestLine {
    public static void main(String[] args) {

        Hull hull = (Hull) new HullLineStep().buildProductPart();
        Engine engine = (Engine) new EngineLineStep().buildProductPart();
        Turret turret = (Turret) new TurretLineStep().buildProductPart();

        System.out.println();

        IAssemblyLine line = new AssemblyLine(hull, engine, turret);

        line.assembleProduct(new Tank());
    }
}
