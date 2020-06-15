package eu.senla.course.test;

import eu.senla.course.service.ServiceProvider;
import eu.senla.course.entity.*;
import eu.senla.course.entity.comparator.mechanic.ByAlphabet;
import eu.senla.course.entity.comparator.mechanic.ByBusy;
import eu.senla.course.entity.comparator.order.ByCompleteDate;
import eu.senla.course.entity.comparator.order.ByPlannedDate;
import eu.senla.course.entity.comparator.order.ByPrice;
import eu.senla.course.entity.comparator.order.ByRequestDate;
import eu.senla.course.util.DataCreator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TestServiceCenter {
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(" dd.mm.yyyy HH:mm");
    public static void main(String[] args) {

        ServiceProvider provider  = ServiceProvider.getInstance();

        DataCreator creator = new DataCreator();

        List<Garage> garages = creator.createGarages();
        List<Tool> tools = creator.createServices();
        List<Mechanic> mechanics = creator.createMechanics();
        List<Order> ordersForPeriod;

        // В автомастерской есть гаражи с местами
        garages.forEach(System.out:: println);

        // Мастерская оказывает услуги
        System.out.println("List of Tools: ");
        for (Tool tool: tools){
            System.out.print(tool.getName() + "; ");
        }

        // В автомастерской есть мастера
        System.out.println("\n"+provider.getMechanicManager().getMechanics().toString());

        // Далее действия по услугам и заказу - безличное

        // выбор услуг
        System.out.println("Your choice of tools: ");

        List<Tool> servicesForOrder = new ArrayList<>();
        servicesForOrder.add(tools.get(0));
        servicesForOrder.add(tools.get(2));

        servicesForOrder.forEach(System.out::println);

        // Выбор даты
        LocalDateTime plannedDate = LocalDateTime.now().plusHours(1);
        System.out.println("Planned date: " + plannedDate.format(timeFormatter));

        // места на определенную дату workshop.listAvailableSpots
        List<Spot> freeSpots = provider.getGarageManager().listAvailableSpots(plannedDate, provider.getOrderManager().getOrders());
        printSpot(freeSpots);
        // Количество свободных мест на сервисе на любую дату в будущем
        System.out.println("Number Available Spots on Date: " + provider.getGarageManager().numberAvailableSpots(plannedDate, provider.getOrderManager().getOrders()));

        // выбор свободного места в гараже, например, первое доступное
        Spot spot = freeSpots.get(0);
        // Создание заказа
        Order order = new Order(1, LocalDateTime.now(), plannedDate, mechanics.get(0), spot);
        Order order2 = new Order(2, LocalDateTime.now().minusHours(3), plannedDate.plusHours(4), mechanics.get(0), freeSpots.get(1));

        List<Order> ordersMechanic = new ArrayList<>();
        ordersMechanic.add(order);
        ordersMechanic.add(order2);
        mechanics.get(0).setOrders(ordersMechanic);

        provider.getOrderManager().addOrder(order);
        provider.getOrderManager().addOrder(order2);

        System.out.println("Your mechanic " + mechanics.get(0).getName());

        // добавили сервисы, но их может не быть
        order.setStartDate(plannedDate.plusHours(1));
        order.setServices(servicesForOrder);
        System.out.println("Request date " + order.getRequestDate().format(timeFormatter));
        System.out.println("Start at " + order.getStartDate().format(timeFormatter));
        System.out.println("Your car will be ready by the date " + order.getCompleteDate().format(timeFormatter));

        // order 2
        order2.setStartDate(plannedDate.plusHours(4));

        List<Tool> servicesListO2 = new ArrayList<>();
        servicesListO2.add(tools.get(1));
        servicesListO2.add(tools.get(3));
        order2.setServices(servicesListO2);

        // заказ сделан
        System.out.println(order.toString());
        System.out.println("Complete date " + order.getCompleteDate().format(timeFormatter));

        // Сместить время выполнения заказов/for example on 2 hours
        int moveHours = 2;
        provider.getOrderManager().changeStartDateOrders(moveHours);
        System.out.println("The estimate for the order has been revised by " + moveHours + " hours");

        // Список заказов по дате подачи
        System.out.println("All Orders by Request Date");
        List<Order> orders = provider.getOrderManager().listOrders(new ByRequestDate());
        printOrders(orders);

        // заказ сделан
        System.out.println("Complete date 1" + order.getCompleteDate().format(timeFormatter));
        System.out.println("Complete date 2" + order2.getCompleteDate().format(timeFormatter));

        // Выставление счета
        provider.getOrderManager().bill(order);
        // Выставление счета 2
        provider.getOrderManager().bill(order2);

        // Заказ, выполняемый конкретным мастером
        System.out.println("Mechanic's exact order: " + provider.getOrderManager().mechanicOrder(mechanics.get(0)).toString());

        // Мастера, выполняющего конкретный заказ
        System.out.println("Order's mechanic: " + provider.getOrderManager().orderMechanic(order));

        // переопределяем доступные места
        printSpot(provider.getGarageManager().listAvailableSpots(plannedDate, provider.getOrderManager().getOrders()));

        System.out.println("Number Available Spots on Date: " + provider.getGarageManager().numberAvailableSpots(plannedDate, provider.getOrderManager().getOrders()));

        // Ближайшая свободная дата
        System.out.println("Next available date " + provider.getOrderManager().nextAvailableDate(provider.getGarageManager(), LocalDate.now().plusDays(7)).format(timeFormatter));

        // сортировка по занятости
        System.out.println("Sorting Mechanics by busy ");
        provider.getMechanicManager().sortMechanicsBy(new ByBusy());

        // Заказы (выполненные/удаленные/отмененные) за промежуток времени
        System.out.println("Orders in progress for the period");
        ordersForPeriod = provider.getOrderManager().ordersForPeriod(new ByCompleteDate(), OrderStatus.IN_PROGRESS,LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMonths(2));
        printOrders(ordersForPeriod);

        // после выставления счета - закрываем заказ
        System.out.println("Closing first order");
        order.setStatus(OrderStatus.CLOSE);
        System.out.println(order.toString());

        //Список текущих выполняемых заказов
        System.out.println("List of current orders in progress");
        List<Order> currentOrders = provider.getOrderManager().listCurrentOrders(new ByRequestDate());
        printOrders(currentOrders);

        System.out.println("Closing second order");
        order2.setStatus(OrderStatus.CLOSE);
        System.out.println(order2.toString());
        // место сново доступно
        printSpot(provider.getGarageManager().listAvailableSpots(plannedDate, provider.getOrderManager().getOrders()));
        System.out.println("Number Available Spots on Date: " + provider.getGarageManager().numberAvailableSpots(plannedDate, provider.getOrderManager().getOrders()));

        System.out.println("Sorting Mechanics by alphabet ");
        provider.getMechanicManager().sortMechanicsBy(new ByAlphabet());

        // Заказы (выполненные/удаленные/отмененные) за промежуток времени
        System.out.println("Orders by Complete Date");
        ordersForPeriod = provider.getOrderManager().ordersForPeriod(new ByCompleteDate(), OrderStatus.CLOSE,LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMonths(2));
        printOrders(ordersForPeriod);

        System.out.println("Orders by Price");
        ordersForPeriod = provider.getOrderManager().ordersForPeriod(new ByPrice(), OrderStatus.CLOSE,LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMonths(2));
        printOrders(ordersForPeriod);

        System.out.println("Orders by Request Date");
        ordersForPeriod = provider.getOrderManager().ordersForPeriod(new ByRequestDate(), OrderStatus.CLOSE,LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMonths(2));
        printOrders(ordersForPeriod);

        System.out.println("Orders by Planned Date");
        ordersForPeriod = provider.getOrderManager().ordersForPeriod(new ByPlannedDate(), OrderStatus.CLOSE,LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMonths(2));
        printOrders(ordersForPeriod);


    }
    private static void printOrders(List<Order> orders){
        for (Order order: orders){
            if (order != null){
                System.out.println(order.toString());
            }
        }
    }
    private static void printSpot(List<Spot> freeSpots){
        for (Spot freeSpot: freeSpots){
            if (freeSpot != null) {
                System.out.println("Available spot: " + freeSpot.toString());
            }
        }
    }
}
