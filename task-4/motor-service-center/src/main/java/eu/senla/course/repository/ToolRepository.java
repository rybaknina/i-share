package eu.senla.course.repository;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.IToolRepository;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Tool;
import eu.senla.course.enums.sql.SqlTool;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.util.ConnectionUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ToolRepository implements IToolRepository {

    @Override
    public void add(Tool tool) throws RepositoryException {
        if (tool == null){
            throw new RepositoryException("Tool is not exist");
        }
        Connection connection = ConnectionUtil.getInstance().connect();
        try ( PreparedStatement ps = connection.prepareStatement(SqlTool.INSERT.getName())){
            ps.setString(1, tool.getName());
            ps.setInt(2, tool.getHours());
            ps.setBigDecimal(3, tool.getHourlyPrice());
            ps.setInt(4, tool.getOrder().getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException("Exception " + e.getMessage());
        }
    }

    @Override
    public void delete(Tool tool)  {
        Connection connection = ConnectionUtil.getInstance().connect();

        try (PreparedStatement ps = connection.prepareStatement(SqlTool.DELETE.getName())){

            ps.setInt(1, tool.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Exception " + e.getMessage());
        }
    }

    @Override
    public Tool getById(int id) {
        Tool tool = null;
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlTool.SELECT_BY_ID.getName())){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                String name = rs.getString(SqlTool.NAME.getName());
                int hours = rs.getInt(SqlTool.HOURS.getName());
                BigDecimal hourlyPrice = rs.getBigDecimal(SqlTool.HOURLY_PRICE.getName());
                int orderId = rs.getInt(SqlTool.ORDER_ID.getName());
                Order order = OrderController.getInstance().getOrderById(orderId);
                tool = new Tool(id, name, hours, hourlyPrice, order);
            }

        } catch (SQLException e) {
            tool = null;
        }
        return tool;
    }

    @Override
    public List<Tool> getAll() {
        List<Tool> tools = new ArrayList<>();
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlTool.SELECT_ALL.getName())){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tool tool = null;

                int id = rs.getInt(SqlTool.ID.getName());
                String name = rs.getString(SqlTool.NAME.getName());
                int hours = rs.getInt(SqlTool.HOURS.getName());
                BigDecimal hourlyPrice = rs.getBigDecimal(SqlTool.HOURLY_PRICE.getName());
                int orderId = rs.getInt(SqlTool.ORDER_ID.getName());
                Order order = OrderController.getInstance().getOrderById(orderId);
                tool = new Tool(id, name, hours, hourlyPrice, order);

                tools.add(tool);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tools;
    }

    public void update(Tool tool) throws RepositoryException{
        Connection connection = ConnectionUtil.getInstance().connect();
        try ( PreparedStatement ps = connection.prepareStatement(SqlTool.UPDATE.getName())) {

            ps.setString(1, tool.getName());
            ps.setInt(2, tool.getHours());
            ps.setBigDecimal(3, tool.getHourlyPrice());
            ps.setInt(4, tool.getOrder().getId());
            ps.setInt(5, tool.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException("Exception " + e.getMessage());
        }
    }
    public void setAll(List<Tool> tools){
       // this.tools = tools;
    }
    public void addAll(List<Tool> tools){
       // this.tools.addAll(tools);
    }
}
