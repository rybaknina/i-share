package eu.senla.course.repository;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.IOrderRepository;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.controller.SpotController;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Spot;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.enums.sql.SqlOrder;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.util.ConnectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Repository
public class OrderRepository implements IOrderRepository {
    private final static Logger logger = LogManager.getLogger(OrderRepository.class);
    @Override
    public void add(Order order) throws RepositoryException {
        if (order == null) {
            throw new RepositoryException("Order is not exist");
        }
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlOrder.INSERT.getName())) {
            ps.setTimestamp(1, psDateTime(order.getRequestDate()));
            ps.setTimestamp(2, psDateTime(order.getPlannedDate()));
            ps.setInt(3, order.getMechanic().getId());
            ps.setInt(4, order.getSpot().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception " + e.getMessage());
        }
    }

    @Override
    public void delete(Order order) {
        Connection connection = ConnectionUtil.getInstance().connect();

        try (PreparedStatement ps = connection.prepareStatement(SqlOrder.DELETE.getName())) {
            ps.setInt(1, order.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Exception " + e.getMessage());
        }
    }

    @Override
    public Order getById(int id) {
        Order order = null;
        if (id == 0) {
            logger.warn("Wrong Id = " + id);
            return order;
        }
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlOrder.SELECT_BY_ID.getName())) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                order = fillOrder(rs, id);
            }
        } catch (SQLException e) {
            logger.info("SQLException " + e.getMessage());
            order = null;
        }
        return order;
    }

    private void setOrderStatus(String status, Order order) {
        if (status != null && !status.isBlank()) {
            switch (status) {
                case "CLOSE":
                    order.setStatus(OrderStatus.CLOSE);
                    break;
                case "DELETE":
                    order.setStatus(OrderStatus.DELETE);
                    break;
                case "CANCEL":
                    order.setStatus(OrderStatus.CANCEL);
                    break;
                default:
                    order.setStatus(OrderStatus.IN_PROGRESS);
                    break;
            }
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlOrder.SELECT_ALL.getName())) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = null;
                int id = rs.getInt(SqlOrder.ID.getName());
                order = fillOrder(rs, id);

                orders.add(order);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return orders;
    }

    private Order fillOrder(ResultSet rs, int id) throws SQLException {
        Order order;
        LocalDateTime requestDate = rsTimeStamp(rs.getTimestamp(SqlOrder.REQUEST_DATE.getName()));
        LocalDateTime plannedDate = rsTimeStamp(rs.getTimestamp(SqlOrder.PLANNED_DATE.getName()));
        LocalDateTime startDate = rsTimeStamp(rs.getTimestamp(SqlOrder.START_DATE.getName()));
        LocalDateTime completeDate  = rsTimeStamp(rs.getTimestamp(SqlOrder.COMPLETE_DATE.getName()));
        BigDecimal price = rs.getBigDecimal(SqlOrder.PRICE.getName());
        String status = rs.getString(SqlOrder.STATUS.getName());
        int mechanicId = rs.getInt(SqlOrder.MECHANIC_ID.getName());
        int spotId = rs.getInt(SqlOrder.SPOT_ID.getName());

        Mechanic mechanic = MechanicController.getInstance().getMechanicById(mechanicId);
        Spot spot = SpotController.getInstance().getSpotById(spotId);

        order = new Order(id, requestDate, plannedDate, mechanic, spot);
        order.setStartDate(startDate);
        order.setCompleteDate(completeDate);
        order.setPrice(price);
        setOrderStatus(status, order);
        return order;
    }

    private LocalDateTime rsTimeStamp(Timestamp timestamp) {
        if (timestamp != null) {
            return timestamp.toLocalDateTime();
        }
        return null;
    }
    private Timestamp psDateTime(LocalDateTime dateTime) {
        if (dateTime != null) {
            return Timestamp.valueOf(dateTime);
        }
        return null;
    }
    public void update(Order order) throws RepositoryException {
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlOrder.UPDATE.getName())) {
            ps.setTimestamp(1, psDateTime(order.getRequestDate()));
            ps.setTimestamp(2, psDateTime(order.getPlannedDate()));
            ps.setTimestamp(3, psDateTime(order.getStartDate()));
            ps.setTimestamp(4, psDateTime(order.getCompleteDate()));
            ps.setBigDecimal(5, order.getPrice());
            ps.setString(6, order.getStatus().toString());
            ps.setInt(7, order.getMechanic().getId());
            ps.setInt(8, order.getSpot().getId());
            ps.setInt(9, order.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception " + e.getMessage());
        }
    }
    public void setAll(List<Order> orders) {
        Connection connection = ConnectionUtil.getInstance().connect();

        try (PreparedStatement deleleAll = connection.prepareStatement(SqlOrder.DELETE_ALL.getName()); PreparedStatement reset = connection.prepareStatement(SqlOrder.RESET.getName()); PreparedStatement insert = connection.prepareStatement(SqlOrder.INSERT.getName())) {
            connection.setAutoCommit(false);

            deleleAll.executeUpdate();
            reset.executeUpdate();

            for (Order order: orders) {
                insert.setTimestamp(1, psDateTime(order.getRequestDate()));
                insert.setTimestamp(2, psDateTime(order.getPlannedDate()));
                insert.setInt(3, order.getMechanic().getId());
                insert.setInt(4, order.getSpot().getId());
                insert.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("Rollback exception " + e.getMessage());
            }
            logger.error("Exception " + e.getMessage());
        }
    }
    public void addAll(List<Order> orders) {
        Connection connection = ConnectionUtil.getInstance().connect();

        try (PreparedStatement insert = connection.prepareStatement(SqlOrder.INSERT.getName())) {
            connection.setAutoCommit(false);

            for (Order order: orders) {
                insert.setTimestamp(1, psDateTime(order.getRequestDate()));
                insert.setTimestamp(2, psDateTime(order.getPlannedDate()));
                insert.setInt(3, order.getMechanic().getId());
                insert.setInt(4, order.getSpot().getId());
                insert.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("Rollback exception " + e.getMessage());
            }
            logger.error("Exception " + e.getMessage());
        }
    }
}
