package eu.senla.course.test;

import eu.senla.course.controller.BillService;
import eu.senla.course.entity.*;
import eu.senla.course.util.DataCreator;

import java.time.LocalDateTime;

public class TestServiceCenter {

    public static void main(String[] args) {
        DataCreator creator = new DataCreator();
        Garage[] garages = creator.createGarages();
        Service[] services = creator.createServices();
        Mechanic[] mechanics = creator.createMechanics();

        // В автомастерской есть гаражи с местами
        for (Garage garage: garages){
            System.out.println(garage);
        }

        // Мастерская оказывает услуги
        for (Service service: services){
            System.out.println(service.getName());
        }

        // В автомастерской есть мастера
        for (Mechanic mechanic: mechanics){
            System.out.println(mechanic.getName());
        }

        // Далее действия по услугам и заказу - безличное

        // выбор услуг
        Service[] servicesForOrder = new Service[]{services[0], services[2]};
        for (Service service: servicesForOrder){
            System.out.println(service.toString());
        }
        // Выбор даты
        LocalDateTime plannedDate = LocalDateTime.now().plusHours(2);

        // выбор свободного места в гараже - to be continue
        // выбор свободного механика - to be continue

        // Выбор гаража
        Garage garage = garages[0]; // пока так
        // выбор места
        Spot spot = garage.getSpots()[0]; // to be continue.. for example nextAvailableSpot
        // Создание заказа - to be continue - нужно как-то свободные даты выбирать
        Order order = new Order(1, plannedDate, mechanics[0], garage, spot);
        order.setServices(servicesForOrder); // добавили сервисы, но их может не быть

        // заказ сделан
        System.out.println(order.toString());

        // Выставление счета
        new BillService(order).bill(order.getServices());

        // после выставления счета - закрываем заказ
        order.setStatus(OrderStatus.CLOSE);
        System.out.println(order.toString());
        // вся сортировка и сдвиг по времени - to be continue
    }

}
