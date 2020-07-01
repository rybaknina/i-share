package eu.senla.course.util;

import eu.senla.course.api.IEntity;
import eu.senla.course.entity.*;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.repository.*;
import eu.senla.course.util.exception.SerializeException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializeUtil {
    private final static String LOAD_PATH = "load.center";

    public static void serialize() throws SerializeException {
        List<List<? extends IEntity>> entityList = new ArrayList<>();
        try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(PathToFile.getPath(LOAD_PATH)))){

            entityList.add(GarageRepository.getInstance().getAll());
            entityList.add(MechanicRepository.getInstance().getAll());
            entityList.add(OrderRepository.getInstance().getAll());
            entityList.add(SpotRepository.getInstance().getAll());
            entityList.add(ToolRepository.getInstance().getAll());

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
            List<Garage> garages = (List<Garage>) entityList.get(n++);
            for (Garage garage: garages){
                GarageRepository.getInstance().add(garage);
                Garage.getCount().incrementAndGet();
            }
            List<Mechanic> mechanics = (List<Mechanic>) entityList.get(n++);
            for (Mechanic mechanic: mechanics){
                MechanicRepository.getInstance().add(mechanic);
                Mechanic.getCount().incrementAndGet();
            }
            List<Order> orders = (List<Order>) entityList.get(n++);
            for (Order order: orders){
                OrderRepository.getInstance().add(order);
                Order.getCount().incrementAndGet();
            }
            List<Spot> spots = (List<Spot>) entityList.get(n++);
            for (Spot spot: spots){
                SpotRepository.getInstance().add(spot);
                Spot.getCount().incrementAndGet();
            }
            List<Tool> tools = (List<Tool>) entityList.get(n);
            for (Tool tool: tools){
                ToolRepository.getInstance().add(tool);
                Tool.getCount().incrementAndGet();
            }

        } catch (FileNotFoundException e) {
            throw new SerializeException("File with data is not found");
        } catch (IOException | RepositoryException e) {
            throw new SerializeException("Load data from file was interrupted");
        } catch (ClassNotFoundException e) {
            throw new SerializeException("Class for object is not found");
        }
    }
}
