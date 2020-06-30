package eu.senla.course.menu;

import eu.senla.course.action.garage.*;
import eu.senla.course.action.mechanic.*;
import eu.senla.course.action.order.*;
import eu.senla.course.action.spot.*;
import eu.senla.course.action.tool.*;
import eu.senla.course.controller.OrderController;
import eu.senla.course.controller.SpotController;
import eu.senla.course.enums.*;

public class Builder {

    private final static Builder instance = new Builder();

    private Menu rootMenu = new Menu(MainMenu.ROOT.getName());

    private Builder() {
    }

    public static Builder getInstance(){
        return instance;
    }

    public Menu buildMenu(){

        Menu garageMenu = new Menu(MainMenu.SUB.getName());
        Menu toolMenu = new Menu(MainMenu.SUB.getName());
        Menu mechanicMenu = new Menu(MainMenu.SUB.getName());
        Menu orderMenu = new Menu(MainMenu.SUB.getName());
        Menu spotMenu = new Menu(MainMenu.SUB.getName());

        MenuItem mainItem = new MenuItem(MainMenu.ROOT.getName(), rootMenu);
        MenuItem exitItem = new MenuItem(MainMenu.EXIT.getName());

        createGarageMenu(garageMenu, exitItem);
        mainItem.setNextMenu(garageMenu);

        createToolMenu(toolMenu, exitItem);
        mainItem.setNextMenu(toolMenu);

        createMechanicMenu(mechanicMenu, exitItem);
        mainItem.setNextMenu(mechanicMenu);

        createOrderMenu(orderMenu, exitItem);
        mainItem.setNextMenu(orderMenu);

        createSpotMenu(spotMenu, exitItem);
        mainItem.setNextMenu(spotMenu);


        rootMenu.add(new MenuItem(GarageMenu.GARAGE.getName(), garageMenu));
        rootMenu.add(new MenuItem(ToolMenu.TOOL.getName(), toolMenu));
        rootMenu.add(new MenuItem(MechanicMenu.MECHANIC.getName(), mechanicMenu));
        rootMenu.add(new MenuItem(OrderMenu.ORDER.getName(), orderMenu));
        rootMenu.add(new MenuItem(SpotMenu.SPOT.getName(), spotMenu));
        rootMenu.add(exitItem);

        return rootMenu;
    }

    private void createSpotMenu(Menu spotMenu, MenuItem exitItem) {
        if (SpotController.getInstance().isModifySpot()) {
            spotMenu.add(new MenuItem(SpotMenu.ADD.getName(), spotMenu, new AddSpotAction()));
            spotMenu.add(new MenuItem(SpotMenu.DELETE.getName(), spotMenu, new DeleteSpotAction()));
        }
        spotMenu.add(new MenuItem(SpotMenu.GET_ALL.getName(), spotMenu, new GetSpotsAction()));
        spotMenu.add(new MenuItem(SpotMenu.IMPORT.getName(), spotMenu, new ImportSpotsAction()));
        spotMenu.add(new MenuItem(SpotMenu.EXPORT.getName(), spotMenu, new ExportSpotsAction()));

        spotMenu.add(new MenuItem(MainMenu.RETURN.getName(), rootMenu));
        spotMenu.add(exitItem);
    }

    private void createOrderMenu(Menu orderMenu, MenuItem exitItem) {
        orderMenu.add(new MenuItem(OrderMenu.ADD.getName(), orderMenu, new AddOrderAction()));
        if (OrderController.getInstance().isDeleteOrder()) {
            orderMenu.add(new MenuItem(OrderMenu.DELETE.getName(), orderMenu, new DeleteOrderAction()));
        }
        orderMenu.add(new MenuItem(OrderMenu.ADD_TOOLS.getName(), orderMenu, new AddToolsToOrderAction()));
        orderMenu.add(new MenuItem(OrderMenu.GET_ALL.getName(), orderMenu, new GetAllOrdersAction()));
        if (OrderController.getInstance().isShiftTime()) {
            orderMenu.add(new MenuItem(OrderMenu.SHIFT_TIME.getName(), orderMenu, new ChangeStartDateOrdersAction()));
        }

        orderMenu.add(new MenuItem(OrderMenu.CANCEL_STATUS.getName(), orderMenu, new SetCancelStatusOrderAction()));
        orderMenu.add(new MenuItem(OrderMenu.CLOSE_STATUS.getName(), orderMenu, new SetCloseStatusOrderAction()));
        orderMenu.add(new MenuItem(OrderMenu.DELETE_STATUS.getName(), orderMenu, new SetDeleteStatusOrderAction()));
        orderMenu.add(new MenuItem(OrderMenu.IN_PROGRESS_STATUS.getName(), orderMenu, new SetInProgressStatusOrderAction()));
        orderMenu.add(new MenuItem(OrderMenu.FIND_MECHANIC.getName(), orderMenu, new FindOrderMechanicAction()));
        orderMenu.add(new MenuItem(OrderMenu.FIND_ORDER.getName(), orderMenu, new FindMechanicOrderAction()));

        orderMenu.add(new MenuItem(OrderMenu.CURRENT_BY_COMPLETE.getName(), orderMenu, new CurrentOrdersByCompleteDate()));
        orderMenu.add(new MenuItem(OrderMenu.CURRENT_BY_PLANNED.getName(), orderMenu, new CurrentOrdersByPlannedDate()));
        orderMenu.add(new MenuItem(OrderMenu.CURRENT_BY_REQUEST.getName(), orderMenu, new CurrentOrdersByRequestDate()));
        orderMenu.add(new MenuItem(OrderMenu.CURRENT_BY_PRICE.getName(), orderMenu, new CurrentOrdersByPrice()));

        orderMenu.add(new MenuItem(OrderMenu.IN_PROGRESS_BY_COMPLETE.getName(), orderMenu, new InProgressOrdersForPeriodByCompleteDate()));
        orderMenu.add(new MenuItem(OrderMenu.IN_PROGRESS_BY_PLANNED.getName(), orderMenu, new InProgressOrdersForPeriodByPlannedDate()));
        orderMenu.add(new MenuItem(OrderMenu.IN_PROGRESS_BY_REQUEST.getName(), orderMenu, new InProgressOrdersForPeriodByRequestDate()));
        orderMenu.add(new MenuItem(OrderMenu.IN_PROGRESS_BY_PRICE.getName(), orderMenu, new InProgressOrdersForPeriodByPrice()));

        orderMenu.add(new MenuItem(OrderMenu.CANCEL_BY_COMPLETE.getName(), orderMenu, new CanceledOrdersForPeriodByCompleteDate()));
        orderMenu.add(new MenuItem(OrderMenu.CANCEL_BY_PLANNED.getName(), orderMenu, new CanceledOrdersForPeriodByPlannedDate()));
        orderMenu.add(new MenuItem(OrderMenu.CANCEL_BY_REQUEST.getName(), orderMenu, new CanceledOrdersForPeriodByRequestDate()));
        orderMenu.add(new MenuItem(OrderMenu.CANCEL_BY_PRICE.getName(), orderMenu, new CanceledOrdersForPeriodByPrice()));

        orderMenu.add(new MenuItem(OrderMenu.CLOSE_BY_COMPLETE.getName(), orderMenu, new ClosedOrdersForPeriodByCompleteDate()));
        orderMenu.add(new MenuItem(OrderMenu.CLOSE_BY_PLANNED.getName(), orderMenu, new ClosedOrdersForPeriodByPlannedDate()));
        orderMenu.add(new MenuItem(OrderMenu.CLOSE_BY_REQUEST.getName(), orderMenu, new ClosedOrdersForPeriodByRequestDate()));
        orderMenu.add(new MenuItem(OrderMenu.CLOSE_BY_PRICE.getName(), orderMenu, new ClosedOrdersForPeriodByPrice()));

        orderMenu.add(new MenuItem(OrderMenu.DELETE_BY_COMPLETE.getName(), orderMenu, new DeletedOrdersForPeriodByCompleteDate()));
        orderMenu.add(new MenuItem(OrderMenu.DELETE_BY_PLANNED.getName(), orderMenu, new DeletedOrdersForPeriodByPlannedDate()));
        orderMenu.add(new MenuItem(OrderMenu.DELETE_BY_REQUEST.getName(), orderMenu, new DeletedOrdersForPeriodByRequestDate()));
        orderMenu.add(new MenuItem(OrderMenu.DELETE_BY_PRICE.getName(), orderMenu, new DeletedOrdersForPeriodByPrice()));

        orderMenu.add(new MenuItem(OrderMenu.NEXT_DATE.getName(), orderMenu, new NextAvailableDateAction()));
        orderMenu.add(new MenuItem(OrderMenu.BILL.getName(), orderMenu, new BillAction()));

        orderMenu.add(new MenuItem(OrderMenu.IMPORT.getName(), orderMenu, new ImportOrdersAction()));
        orderMenu.add(new MenuItem(OrderMenu.EXPORT.getName(), orderMenu, new ExportOrdersAction()));

        orderMenu.add(new MenuItem(MainMenu.RETURN.getName(), rootMenu));
        orderMenu.add(exitItem);
    }

    private void createMechanicMenu(Menu mechanicMenu, MenuItem exitItem) {
        mechanicMenu.add(new MenuItem(MechanicMenu.ADD.getName(), mechanicMenu, new AddMechanicAction()));
        mechanicMenu.add(new MenuItem(MechanicMenu.DELETE.getName(), mechanicMenu, new DeleteMechanicAction()));
        mechanicMenu.add(new MenuItem(MechanicMenu.GET_ALL.getName(), mechanicMenu, new GetMechanicsAction()));
        mechanicMenu.add(new MenuItem(MechanicMenu.SORT_BY_ALPHABET.getName(), mechanicMenu, new SortMechanicsByAlphabetAction()));
        mechanicMenu.add(new MenuItem(MechanicMenu.SORT_BY_BUSY.getName(), mechanicMenu, new SortMechanicsByBusyAction()));

        mechanicMenu.add(new MenuItem(MechanicMenu.IMPORT.getName(), mechanicMenu, new ImportMechanicsAction()));
        mechanicMenu.add(new MenuItem(MechanicMenu.EXPORT.getName(), mechanicMenu, new ExportMechanicsAction()));

        mechanicMenu.add(new MenuItem(MainMenu.RETURN.getName(), rootMenu));
        mechanicMenu.add(exitItem);
    }

    private void createToolMenu(Menu toolMenu, MenuItem exitItem) {
        toolMenu.add(new MenuItem(ToolMenu.ADD.getName(), toolMenu, new AddToolAction()));
        toolMenu.add(new MenuItem(ToolMenu.DELETE.getName(), toolMenu, new DeleteToolAction()));
        toolMenu.add(new MenuItem(ToolMenu.GET_ALL.getName(), toolMenu, new GetToolsAction()));
        toolMenu.add(new MenuItem(ToolMenu.IMPORT.getName(), toolMenu, new ImportToolsAction()));
        toolMenu.add(new MenuItem(ToolMenu.EXPORT.getName(), toolMenu, new ExportToolsAction()));

        toolMenu.add(new MenuItem(MainMenu.RETURN.getName(), rootMenu));
        toolMenu.add(exitItem);
    }

    private void createGarageMenu(Menu garageMenu, MenuItem exitItem){
        garageMenu.add(new MenuItem(GarageMenu.ADD.getName(), garageMenu, new AddGarageAction()));
        garageMenu.add(new MenuItem(GarageMenu.DELETE.getName(), garageMenu, new DeleteGarageAction()));
        garageMenu.add(new MenuItem(GarageMenu.GET_ALL.getName(), garageMenu, new GetGaragesAction()));
        garageMenu.add(new MenuItem(GarageMenu.LIST_FREE_SPOTS.getName(), garageMenu, new ListAvailableSpotsAction()));
        garageMenu.add(new MenuItem(GarageMenu.NUMBER_FREE_SPOTS.getName(), garageMenu, new NumberAvailableSpotsAction()));
        garageMenu.add(new MenuItem(GarageMenu.IMPORT.getName(), garageMenu, new ImportGaragesAction()));
        garageMenu.add(new MenuItem(GarageMenu.EXPORT.getName(), garageMenu, new ExportGaragesAction()));

        garageMenu.add(new MenuItem(MainMenu.RETURN.getName(), rootMenu));
        garageMenu.add(exitItem);
    }

    public Menu getRootMenu() {
        return rootMenu;
    }
}
