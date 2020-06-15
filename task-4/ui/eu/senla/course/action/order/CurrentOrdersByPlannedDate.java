package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.comparator.order.ByPlannedDate;
import eu.senla.course.service.ServiceProvider;

public class CurrentOrdersByPlannedDate implements IAction {
    private OrderController controller = new OrderController(ServiceProvider.getInstance().getOrderService());

    @Override
    public void execute() {
        controller.listCurrentOrders(new ByPlannedDate()).forEach(System.out::println);
    }
}
