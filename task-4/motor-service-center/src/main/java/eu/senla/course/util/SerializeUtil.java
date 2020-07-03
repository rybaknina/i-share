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
            int max = 0;
            List<Garage> garages = (List<Garage>) entityList.get(n++);
            for (Garage garage: garages){
                if (max < garage.getId()){
                    max = garage.getId();
                }
                GarageRepository.getInstance().add(garage);
            }

            while (Garage.getCount().get() < max){
                Garage.getCount().incrementAndGet();
            }

            max = 0;
            List<Mechanic> mechanics = (List<Mechanic>) entityList.get(n++);
            for (Mechanic mechanic: mechanics){
                if (max < mechanic.getId()){
                    max = mechanic.getId();
                }
                MechanicRepository.getInstance().add(mechanic);
            }
            while (Mechanic.getCount().get() < max){
                Mechanic.getCount().incrementAndGet();
            }

            max = 0;
            List<Order> orders = (List<Order>) entityList.get(n++);
            for (Order order: orders){
                if (max < order.getId()){
                    max = order.getId();
                }
                OrderRepository.getInstance().add(order);
            }
            while (Order.getCount().get() < max){
                Order.getCount().incrementAndGet();
            }

            max = 0;
            List<Spot> spots = (List<Spot>) entityList.get(n++);
            for (Spot spot: spots){
                if (max < spot.getId()){
                    max = spot.getId();
                }
                SpotRepository.getInstance().add(spot);
            }
            while (Spot.getCount().get() < max){
                Spot.getCount().incrementAndGet();
            }

            max = 0;
            List<Tool> tools = (List<Tool>) entityList.get(n);
            for (Tool tool: tools){
                if (max < tool.getId()){
                    max = tool.getId();
                }
                ToolRepository.getInstance().add(tool);
            }
            while (Tool.getCount().get() < max){
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
