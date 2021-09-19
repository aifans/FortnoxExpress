package se.fortnox.codetest.fortnoxexpress.service;

import se.fortnox.codetest.fortnoxexpress.model.Order;

import java.util.List;

public interface IOrderService {
    List<Order> getAllOrders();
    Order placeAnOrder(Order order);
}
