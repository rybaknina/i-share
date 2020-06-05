package eu.senla.course.test;

import eu.senla.course.controller.BillService;
import eu.senla.course.controller.MechanicManager;
import eu.senla.course.controller.Workshop;
import eu.senla.course.entity.*;
import eu.senla.course.entity.comparator.mechanic.ByAlphabet;
import eu.senla.course.entity.comparator.mechanic.ByBusy;
import eu.senla.course.util.DataCreator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestServiceCenter {

    public static void main(String[] args) {

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("d.MM.uuuu HH:mm");

        Workshop workshop = new Workshop();
        MechanicManager mechanicManager = new MechanicManager();
        DataCreator creator = new DataCreator();

        Garage[] garages = creator.createGarages(workshop);
        Service[] services = creator.createServices(workshop);
        Mechanic[] mechanics = creator.createMechanics(mechanicManager);
        Order[] orders = workshop.listOrders();

        // В автомастерской есть гаражи с местами
        for (Garage garage: garages){
            System.out.print(garage + "; ");
        }

        // Мастерская оказывает услуги
        System.out.println("\nList of Services: ");
        for (Service service: services){
            System.out.print(service.getName() + "; ");
        }

        // В автомастерской есть мастера
        System.out.println(mechanicManager.toString());

        // Далее действия по услугам и заказу - безличное

        // выбор услуг
        System.out.println("Your choice of services: ");
        int hours = 0;
        Service[] servicesForOrder = new Service[]{services[0], services[2]};
        for (Service service: servicesForOrder){
            System.out.println(service.toString());
        }
        // Выбор даты
        LocalDateTime plannedDate = LocalDateTime.now().plusHours(1);
        System.out.println("Planned date: " + plannedDate.format(timeFormatter));

        // места на определенную дату workshop.listAvailableSpots
        Spot[] freeSpots = workshop.listAvailableSpots(plannedDate);
        printSpot(workshop, plannedDate, freeSpots);
        // Количество свободных мест на сервисе на любую дату в будущем
        System.out.println("Number Available Spots on Date: " + workshop.numberAvailableSpots(plannedDate));
        // TODO: выбор свободного механика

        // выбор свободного места в гараже, например, первое доступное
        Spot spot = freeSpots[0];
        // Создание заказа
        Order order = new Order(1, LocalDateTime.now(), plannedDate, mechanics[0], spot);
        Order[] ordersMechanic = new Order[1];
        ordersMechanic[0] = order;

        mechanics[0].setOrders(ordersMechanic);

        workshop.addOrder(order);

        System.out.println("Your mechanic " + mechanics[0].getName());

        // добавили сервисы, но их может не быть
        order.setStartDate(plannedDate.plusHours(1));
        order.setServices(servicesForOrder);
        System.out.println("Request date " + order.getRequestDate().format(timeFormatter));
        System.out.println("Start at " + order.getStartDate().format(timeFormatter));
        System.out.println("Your car will be ready by the date " + order.getCompleteDate().format(timeFormatter));

        // заказ сделан
        System.out.println(order.toString());
        System.out.println("Complete date " + order.getCompleteDate().format(timeFormatter));

        // TODO: Сместить время выполнения заказов/for example on 2 hours
        workshop.changeStartDateOrders(2);
        System.out.println("Order has been replaced or not ");
        // заказ сделан
        System.out.println("Complete date " + order.getCompleteDate().format(timeFormatter));

        // Выставление счета
        new BillService().bill(order);

        // Заказ, выполняемый конкретным мастером
        System.out.println("Mechanic's exact order: " + workshop.mechanicOrder(mechanics[0]).toString());

        // Мастера, выполняющего конкретный заказ
        System.out.println("Order's mechanic: " + workshop.orderMechanic(order));

        // переопределяем доступные места
        printSpot(workshop, plannedDate,  workshop.listAvailableSpots(plannedDate));

        System.out.println("Number Available Spots on Date: " + workshop.numberAvailableSpots(plannedDate));
        // после выставления счета - закрываем заказ
        order.setStatus(OrderStatus.CLOSE);
        System.out.println(order.toString());

        // место сново доступно
        printSpot(workshop, plannedDate,  workshop.listAvailableSpots(plannedDate));
        System.out.println("Number Available Spots on Date: " + workshop.numberAvailableSpots(plannedDate));
        // TODO: вся сортировка и сдвиг по времени (есть начало) - changeStartDateOrders

        System.out.println("Sorting Mechanics by alphabet ");
        mechanicManager.sortMechanicsBy(new ByAlphabet());
        //mechanicManager.sortMechanicsBy(new ByBusy());

    }
    private static void printSpot(Workshop workshop, LocalDateTime plannedDate, Spot[] freeSpots){
        for (Spot freeSpot: freeSpots){
            if (freeSpot != null) {
                System.out.println("Available spot: " + freeSpot.toString());
            }
        }
    }
}
