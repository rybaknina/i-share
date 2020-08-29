package eu.senla.course.test;

import eu.senla.course.controller.AnnotationController;
import eu.senla.course.controller.InjectionController;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.controller.GarageController;
import eu.senla.course.controller.OrderController;
import eu.senla.course.controller.SpotController;

import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Tool;
import eu.senla.course.entity.Spot;
import eu.senla.course.entity.comparator.mechanic.ByAlphabet;
import eu.senla.course.entity.comparator.mechanic.ByBusy;
import eu.senla.course.entity.comparator.order.ByCompleteDate;
import eu.senla.course.entity.comparator.order.ByPlannedDate;
import eu.senla.course.entity.comparator.order.ByPrice;
import eu.senla.course.entity.comparator.order.ByRequestDate;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.exception.AnnotationException;
import eu.senla.course.exception.InjectionException;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.DataCreator;
import eu.senla.course.util.SerializeUtil;
import eu.senla.course.util.exception.SerializeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TestServiceCenter {

    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(" dd.mm.yyyy HH:mm");
    public static void main(String[] args) throws ServiceException, RepositoryException {
        try {
            AnnotationController.getInstance().init();
            InjectionController.getInstance().inject();
            SerializeUtil.deserialize();
        } catch (AnnotationException | InjectionException | SerializeException e) {
            e.printStackTrace();
        }

        DataCreator creator = new DataCreator();

        List<Garage> garages = creator.createGarages();
        List<Tool> tools = creator.createServices();
        List<Mechanic> mechanics = creator.createMechanics();
        List<Order> ordersForPeriod;

        // В автомастерской есть гаражи с местами
        garages.forEach(System.out:: println);

        // Мастерская оказывает услуги
        System.out.println("List of Tools: ");
        for (Tool tool: tools) {
            System.out.print(tool.getName() + "; ");
        }

        // В автомастерской есть мастера
        System.out.println("\n" + MechanicController.getInstance().getMechanics().toString());

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
        List<Spot> freeSpots = GarageController.getInstance().listAvailableSpots(plannedDate, OrderController.getInstance().getOrders());
        printSpot(freeSpots);
        // Количество свободных мест на сервисе на любую дату в будущем
        System.out.println("Number Available Spots on Date: " + GarageController.getInstance().numberAvailableSpots(plannedDate, OrderController.getInstance().getOrders()));

        // выбор свободного места в гараже, например, первое доступное
        Spot spot = freeSpots.size() == 0 ? SpotController.getInstance().getSpots().get(0) : freeSpots.get(0);
        // Создание заказа
        Order order = new Order(LocalDateTime.now(), plannedDate, mechanics.get(0), spot);
        Order order2 = new Order(LocalDateTime.now().minusHours(3), plannedDate.plusHours(4), mechanics.get(0), freeSpots.size() < 1 ? SpotController.getInstance().getSpots().get(1) : freeSpots.get(1));

        List<Order> ordersMechanic = new ArrayList<>();
        ordersMechanic.add(order);
        ordersMechanic.add(order2);
        mechanics.get(0).setOrders(ordersMechanic);

        OrderController.getInstance().addOrder(order);
        OrderController.getInstance().addOrder(order2);

        System.out.println("Your mechanic " + mechanics.get(0).getName());

        // добавили сервисы, но их может не быть
        order.setStartDate(plannedDate.plusHours(1));
        order.setTools(servicesForOrder);
        System.out.println("Request date " + order.getRequestDate().format(timeFormatter));
        System.out.println("Start at " + order.getStartDate().format(timeFormatter));
        System.out.println("Your car will be ready by the date " + order.getCompleteDate().format(timeFormatter));

        // order 2
        order2.setStartDate(plannedDate.plusHours(4));

        List<Tool> servicesListO2 = new ArrayList<>();
        servicesListO2.add(tools.get(1));
        servicesListO2.add(tools.get(3));
        order2.setTools(servicesListO2);

        // заказ сделан
        System.out.println(order.toString());
        System.out.println("Complete date " + order.getCompleteDate().format(timeFormatter));

        // Сместить время выполнения заказов/for example on 2 hours
        int moveHours = 2;
        OrderController.getInstance().changeStartDateOrders(moveHours);
        System.out.println("The estimate for the order has been revised by " + moveHours + " hours");

        // Список заказов по дате подачи
        System.out.println("All Orders by Request Date");
        List<Order> orders = OrderController.getInstance().listOrders(new ByRequestDate());
        printOrders(orders);

        // заказ сделан
        System.out.println("Complete date 1" + order.getCompleteDate().format(timeFormatter));
        System.out.println("Complete date 2" + order2.getCompleteDate().format(timeFormatter));

        // Выставление счета
        OrderController.getInstance().bill(order);
        // Выставление счета 2
        OrderController.getInstance().bill(order2);

        // Заказ, выполняемый конкретным мастером
        System.out.println("Mechanic's exact order: " + OrderController.getInstance().mechanicOrder(mechanics.get(0)).toString());

        // Мастера, выполняющего конкретный заказ
        System.out.println("Order's mechanic: " + OrderController.getInstance().orderMechanic(order));

        // переопределяем доступные места
        printSpot(GarageController.getInstance().listAvailableSpots(plannedDate, OrderController.getInstance().getOrders()));

        System.out.println("Number Available Spots on Date: " + GarageController.getInstance().numberAvailableSpots(plannedDate, OrderController.getInstance().getOrders()));

        // Ближайшая свободная дата
        System.out.println("Next available date " + OrderController.getInstance().nextAvailableDate(LocalDate.now().plusDays(7)).format(timeFormatter));

        // сортировка по занятости
        System.out.println("Sorting Mechanics by busy ");
        MechanicController.getInstance().sortMechanicsBy(new ByBusy());

        // Заказы (выполненные/удаленные/отмененные) за промежуток времени
        System.out.println("Orders in progress for the period");
        ordersForPeriod = OrderController.getInstance().ordersForPeriod(new ByCompleteDate(), OrderStatus.IN_PROGRESS, LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMonths(2));
        printOrders(ordersForPeriod);

        // после выставления счета - закрываем заказ
        System.out.println("Closing first order");
        order.setStatus(OrderStatus.CLOSE);
        System.out.println(order.toString());

        //Список текущих выполняемых заказов
        System.out.println("List of current orders in progress");
        List<Order> currentOrders = OrderController.getInstance().listCurrentOrders(new ByRequestDate());
        printOrders(currentOrders);

        System.out.println("Closing second order");
        order2.setStatus(OrderStatus.CLOSE);
        System.out.println(order2.toString());
        // место сново доступно
        printSpot(GarageController.getInstance().listAvailableSpots(plannedDate, OrderController.getInstance().getOrders()));
        System.out.println("Number Available Spots on Date: " + GarageController.getInstance().numberAvailableSpots(plannedDate, OrderController.getInstance().getOrders()));

        System.out.println("Sorting Mechanics by alphabet ");
        MechanicController.getInstance().sortMechanicsBy(new ByAlphabet());

        // Заказы (выполненные/удаленные/отмененные) за промежуток времени
        System.out.println("Orders by Complete Date");
        ordersForPeriod = OrderController.getInstance().ordersForPeriod(new ByCompleteDate(), OrderStatus.CLOSE, LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMonths(2));
        printOrders(ordersForPeriod);

        System.out.println("Orders by Price");
        ordersForPeriod = OrderController.getInstance().ordersForPeriod(new ByPrice(), OrderStatus.CLOSE, LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMonths(2));
        printOrders(ordersForPeriod);

        System.out.println("Orders by Request Date");
        ordersForPeriod = OrderController.getInstance().ordersForPeriod(new ByRequestDate(), OrderStatus.CLOSE, LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMonths(2));
        printOrders(ordersForPeriod);

        System.out.println("Orders by Planned Date");
        ordersForPeriod = OrderController.getInstance().ordersForPeriod(new ByPlannedDate(), OrderStatus.CLOSE, LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMonths(2));
        printOrders(ordersForPeriod);
    }
    private static void printOrders(List<Order> orders) {
        for (Order order: orders) {
            if (order != null) {
                System.out.println(order.toString());
            }
        }
    }
    private static void printSpot(List<Spot> freeSpots) {
        for (Spot freeSpot: freeSpots) {
            if (freeSpot != null) {
                System.out.println("Available spot: " + freeSpot.toString());
            }
        }
    }
}
