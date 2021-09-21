package se.fortnox.codetest.fortnoxexpress.dao;

import se.fortnox.codetest.fortnoxexpress.model.Order;
import se.fortnox.codetest.fortnoxexpress.model.OrderAddDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IOrderDAO {
    List<Order> getAllOrders();
    int placeAnOrder(Order order);

}
