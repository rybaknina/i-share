package eu.senla.course.util;

import eu.senla.course.annotation.property.ConfigProperty;
import eu.senla.course.api.entity.IEntity;
import eu.senla.course.controller.GarageController;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.controller.OrderController;
import eu.senla.course.controller.SpotController;
import eu.senla.course.controller.ToolController;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Spot;
import eu.senla.course.entity.Tool;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.exception.SerializeException;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SerializeUtil {
    @ConfigProperty(key = "load.center")
    private static String loadPath;

    public static void serialize() throws SerializeException {
        List<List<? extends IEntity>> entityList = new ArrayList<>();
        try (OutputStream stream = new FileOutputStream(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(loadPath)).getFile())) {
            try (ObjectOutputStream os = new ObjectOutputStream(stream)) {

                entityList.add(GarageController.getInstance().getGarages());
                entityList.add(MechanicController.getInstance().getMechanics());
                entityList.add(OrderController.getInstance().getOrders());
                entityList.add(SpotController.getInstance().getSpots());
                entityList.add(ToolController.getInstance().getTools());

                os.writeObject(entityList);
            }
        } catch (FileNotFoundException e) {
            throw new SerializeException("File with data is not found");
        } catch (IOException e) {
            throw new SerializeException("Load data to file was interrupted");
        }
    }

    @SuppressWarnings("unchecked")
    public static void deserialize() throws SerializeException {

        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(loadPath)) {
            try (ObjectInputStream is = new ObjectInputStream(stream)) {
                List<List<? extends IEntity>> entityList = (List<List<? extends IEntity>>) is.readObject();
                int n = 0;
                int max = 0;
                List<Garage> garages = (List<Garage>) entityList.get(n++);
                for (Garage garage : garages) {
                    if (max < garage.getId()) {
                        max = garage.getId();
                    }
                    GarageController.getInstance().addGarage(garage);
                }

                max = 0;
                List<Mechanic> mechanics = (List<Mechanic>) entityList.get(n++);
                for (Mechanic mechanic : mechanics) {
                    if (max < mechanic.getId()) {
                        max = mechanic.getId();
                    }
                    MechanicController.getInstance().addMechanic(mechanic);
                }

                max = 0;
                List<Order> orders = (List<Order>) entityList.get(n++);
                for (Order order : orders) {
                    if (max < order.getId()) {
                        max = order.getId();
                    }
                    OrderController.getInstance().addOrder(order);
                }

                max = 0;
                List<Spot> spots = (List<Spot>) entityList.get(n++);
                for (Spot spot : spots) {
                    if (max < spot.getId()) {
                        max = spot.getId();
                    }
                    SpotController.getInstance().addSpot(spot);
                }

                max = 0;
                List<Tool> tools = (List<Tool>) entityList.get(n);
                for (Tool tool : tools) {
                    if (max < tool.getId()) {
                        max = tool.getId();
                    }
                    ToolController.getInstance().addTool(tool);
                }
            }
        } catch (FileNotFoundException e) {
            throw new SerializeException("File with data is not found");
        } catch (IOException | ServiceException e) {
            throw new SerializeException("Load data from file was interrupted");
        } catch (ClassNotFoundException e) {
            throw new SerializeException("Class for object is not found");
        }
    }
}
