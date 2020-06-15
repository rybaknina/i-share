package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.comparator.order.ByRequestDate;
import eu.senla.course.service.ServiceProvider;

public class CurrentOrdersByRequestDate implements IAction {
    private OrderController controller = new OrderController(ServiceProvider.getInstance().getOrderService());

    @Override
    public void execute() {
        controller.listCurrentOrders(new ByRequestDate()).forEach(System.out::println);
    }
}
