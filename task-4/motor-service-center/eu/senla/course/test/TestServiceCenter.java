package eu.senla.course.test;

import eu.senla.course.controller.BillService;
import eu.senla.course.controller.MechanicManager;
import eu.senla.course.controller.Workshop;
import eu.senla.course.entity.*;
import eu.senla.course.entity.comparator.mechanic.ByAlphabet;
import eu.senla.course.entity.comparator.mechanic.ByBusy;
import eu.senla.course.entity.comparator.order.ByCompleteDate;
import eu.senla.course.entity.comparator.order.ByPlannedDate;
import eu.senla.course.entity.comparator.order.ByPrice;
import eu.senla.course.entity.comparator.order.ByRequestDate;
import eu.senla.course.util.DataCreator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestServiceCenter {
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("d.MM.uuuu HH:mm");
    public static void main(String[] args) {



        Workshop workshop = new Workshop();
        MechanicManager mechanicManager = new MechanicManager();
        DataCreator creator = new DataCreator();

        Garage[] garages = creator.createGarages(workshop);
        Service[] services = creator.createServices(workshop);
        Mechanic[] mechanics = creator.createMechanics(mechanicManager);
        Order[] ordersForPeriod;

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
        printSpot(freeSpots);
        // Количество свободных мест на сервисе на любую дату в будущем
        System.out.println("Number Available Spots on Date: " + workshop.numberAvailableSpots(plannedDate));

        // выбор свободного места в гараже, например, первое доступное
        Spot spot = freeSpots[0];
        // Создание заказа
        Order order = new Order(1, LocalDateTime.now(), plannedDate, mechanics[0], spot);
        Order order2 = new Order(2, LocalDateTime.now().minusHours(3), plannedDate.plusHours(4), mechanics[0], freeSpots[1]);

        Order[] ordersMechanic = new Order[2];
        ordersMechanic[0] = order;
        ordersMechanic[1] = order2;
        mechanics[0].setOrders(ordersMechanic);

        workshop.addOrder(order);
        workshop.addOrder(order2);

        System.out.println("Your mechanic " + mechanics[0].getName());

        // добавили сервисы, но их может не быть
        order.setStartDate(plannedDate.plusHours(1));
        order.setServices(servicesForOrder);
        System.out.println("Request date " + order.getRequestDate().format(timeFormatter));
        System.out.println("Start at " + order.getStartDate().format(timeFormatter));
        System.out.println("Your car will be ready by the date " + order.getCompleteDate().format(timeFormatter));

        // order 2
        order2.setStartDate(plannedDate.plusHours(4));
        order2.setServices(new Service[]{services[1], services[3]});

        // заказ сделан
        System.out.println(order.toString());
        System.out.println("Complete date " + order.getCompleteDate().format(timeFormatter));

        // Сместить время выполнения заказов/for example on 2 hours
        int moveHours = 2;
        workshop.changeStartDateOrders(moveHours);
        System.out.println("The estimate for the order has been revised by " + moveHours + " hours");

        // Список заказов по дате подачи
        System.out.println("All Orders by Request Date");
        Order[] orders = workshop.listOrders(new ByRequestDate());
        printOrders(orders);

        // заказ сделан
        System.out.println("Complete date 1" + order.getCompleteDate().format(timeFormatter));
        System.out.println("Complete date 2" + order2.getCompleteDate().format(timeFormatter));

        // Выставление счета
        new BillService().bill(order);
        // Выставление счета 2
        new BillService().bill(order2);

        // Заказ, выполняемый конкретным мастером
        System.out.println("Mechanic's exact order: " + workshop.mechanicOrder(mechanics[0]).toString());

        // Мастера, выполняющего конкретный заказ
        System.out.println("Order's mechanic: " + workshop.orderMechanic(order));

        // переопределяем доступные места
        printSpot(workshop.listAvailableSpots(plannedDate));

        System.out.println("Number Available Spots on Date: " + workshop.numberAvailableSpots(plannedDate));

        // Ближайшая свободная дата
        System.out.println("Next available date " + workshop.nextAvailableDate().format(timeFormatter));

        // сортировка по занятости
        System.out.println("Sorting Mechanics by busy ");
        mechanicManager.sortMechanicsBy(new ByBusy());

        // Заказы (выполненные/удаленные/отмененные) за промежуток времени
        System.out.println("Orders in progress for the period");
        ordersForPeriod = workshop.ordersForPeriod(new ByCompleteDate(), OrderStatus.IN_PROGRESS,LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMonths(2));
        printOrders(ordersForPeriod);

        // после выставления счета - закрываем заказ
        System.out.println("Closing first order");
        order.setStatus(OrderStatus.CLOSE);
        System.out.println(order.toString());

        //Список текущих выполняемых заказов
        System.out.println("List of current orders in progress");
        Order[] currentOrders = workshop.listCurrentOrders(new ByRequestDate());
        printOrders(currentOrders);

        System.out.println("Closing second order");
        order2.setStatus(OrderStatus.CLOSE);
        System.out.println(order2.toString());
        // место сново доступно
        printSpot(workshop.listAvailableSpots(plannedDate));
        System.out.println("Number Available Spots on Date: " + workshop.numberAvailableSpots(plannedDate));

        System.out.println("Sorting Mechanics by alphabet ");
        mechanicManager.sortMechanicsBy(new ByAlphabet());

        // Заказы (выполненные/удаленные/отмененные) за промежуток времени
        System.out.println("Orders by Complete Date");
        ordersForPeriod = workshop.ordersForPeriod(new ByCompleteDate(), OrderStatus.CLOSE,LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMonths(2));
        printOrders(ordersForPeriod);

        System.out.println("Orders by Price");
        ordersForPeriod = workshop.ordersForPeriod(new ByPrice(), OrderStatus.CLOSE,LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMonths(2));
        printOrders(ordersForPeriod);

        System.out.println("Orders by Request Date");
        ordersForPeriod = workshop.ordersForPeriod(new ByRequestDate(), OrderStatus.CLOSE,LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMonths(2));
        printOrders(ordersForPeriod);

        System.out.println("Orders by Planned Date");
        ordersForPeriod = workshop.ordersForPeriod(new ByPlannedDate(), OrderStatus.CLOSE,LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMonths(2));
        printOrders(ordersForPeriod);


    }
    private static void printOrders(Order[] orders){
        for (Order order: orders){
            if (order != null){
                System.out.println(order.toString());
                //System.out.println(order.toString() + " " + order.getCompleteDate().format(timeFormatter) + " " + order.getPrice());
            }
        }
    }
    private static void printSpot(Spot[] freeSpots){
        for (Spot freeSpot: freeSpots){
            if (freeSpot != null) {
                System.out.println("Available spot: " + freeSpot.toString());
            }
        }
    }
}
