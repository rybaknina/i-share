package tank.test;

import tank.IAssemblyLine;
import tank.entity.Engine;
import tank.entity.Hull;
import tank.entity.Tank;
import tank.entity.Turret;
import tank.line.AssemblyLine;
import tank.line.EngineLineStep;
import tank.line.HullLineStep;
import tank.line.TurretLineStep;

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
