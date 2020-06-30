package eu.senla.course.util;

import eu.senla.course.api.IEntity;
import eu.senla.course.entity.*;
import eu.senla.course.service.*;
import eu.senla.course.util.exception.SerializeException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializeUtil {
    private final static String LOAD_PATH = "load.center";

    public static void serialize(List<Garage> garages, List<Mechanic> mechanics, List<Order> orders, List<Spot> spots, List<Tool> tools) throws SerializeException {
        List<List<? extends IEntity>> entityList = new ArrayList<>();
        try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(PathToFile.getPath(LOAD_PATH)))){

            entityList.add(garages);
            entityList.add(mechanics);
            entityList.add(orders);
            entityList.add(spots);
            entityList.add(tools);

            os.writeObject(entityList);

        } catch (FileNotFoundException e) {
            throw new SerializeException("File with data is not found");
        } catch (IOException e) {
            throw new SerializeException("Load data to file was interrupted");
        }
    }

    @SuppressWarnings("unchecked")
    public static void deserialize() throws SerializeException {
        try(ObjectInputStream is = new ObjectInputStream(new FileInputStream(PathToFile.getPath(LOAD_PATH)))){
            List<List<? extends IEntity>> entityList = (List<List<? extends IEntity>>) is.readObject();
            int n = 0;
            GarageService.getInstance().setGarages((List<Garage>) entityList.get(n++));
            MechanicService.getInstance().setMechanics((List<Mechanic>) entityList.get(n++));
            OrderService.getInstance().setOrders((List<Order>) entityList.get(n++));
            SpotService.getInstance().setSpots((List<Spot>) entityList.get(n++));
            ToolService.getInstance().setTools((List<Tool>) entityList.get(n));

        } catch (FileNotFoundException e) {
            throw new SerializeException("File with data is not found");
        } catch (IOException e) {
            throw new SerializeException("Load data from file was interrupted");
        } catch (ClassNotFoundException e) {
            throw new SerializeException("Class for object is not found");
        }
    }
}
