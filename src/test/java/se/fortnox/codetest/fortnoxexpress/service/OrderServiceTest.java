package se.fortnox.codetest.fortnoxexpress.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.fortnox.codetest.fortnoxexpress.controller.OrderController;
import se.fortnox.codetest.fortnoxexpress.dao.IOrderDAO;
import se.fortnox.codetest.fortnoxexpress.exception.BizException;
import se.fortnox.codetest.fortnoxexpress.model.Order;
import se.fortnox.codetest.fortnoxexpress.model.OrderListDTO;

import java.awt.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class OrderServiceTest {

    @MockBean
    IOrderDAO orderDAO;

    @Autowired
    IOrderService orderService;

    @Test
    void getAllOrders() {
        List<Order> orderListFake = new ArrayList<Order>();
        Order order = new Order(
                333,
                "85854422123",
                "hahaha",
                new BigDecimal(1.0),
                "rgb(255, 0, 0)",
                "Sweden",
                new BigDecimal(0.0));
        orderListFake.add(order);

        Mockito.when(orderDAO.getAllOrders()).thenReturn(orderListFake);

        Assertions.assertEquals(orderListFake, orderService.getAllOrders());
    }

    @Test
    void placeAnOrder() {
        Order orderFake = new Order(
                333,
                "",
                "hahaha",
                new BigDecimal(1.0),
                "#FFFF00",
                "Sweden",
                new BigDecimal(0.0));

        Mockito.doReturn(new BigDecimal(5)).when(orderDAO).getCountryMultiplier(any());
        Mockito.doReturn(0).when(orderDAO).placeAnOrder(any());

        Order order = orderService.placeAnOrder(orderFake);
        System.out.println(order.getOrderId());
        Assertions.assertEquals("rgb(255, 255, 0)", order.getColorBox());
        Assertions.assertEquals(new BigDecimal("5.00"), order.getCostShipment());
    }

    @Test
    void placeAnOrder_color() {
        Order orderFake = new Order(
                333,
                "",
                "hahaha",
                new BigDecimal(1.0),
                "#FFEE01",
                "Sweden",
                new BigDecimal(0.0));

        Mockito.doReturn(new BigDecimal(5)).when(orderDAO).getCountryMultiplier(any());
        Mockito.doReturn(0).when(orderDAO).placeAnOrder(any());

        try {
            Order order = orderService.placeAnOrder(orderFake);
        } catch (BizException bizException) {
            Assertions.assertEquals("60004", bizException.getCode());
        }
    }

    @Test
    void validateColor_blue() {
        try {
            Class<?> clazz = Class.forName(OrderService.class.getName());
            //OrderService orderServiceReflect = (OrderService) clazz.newInstance();
            Method validateColorMethod = clazz.getDeclaredMethod("validateColor", String.class);
            validateColorMethod.setAccessible(true);

            try {
                validateColorMethod.invoke(orderService, "#000001");
            } catch (BizException bizException) {
                Assertions.assertEquals("60004", bizException.getCode());
            }
        } catch (Exception e) {}
    }

    @Test
    void validateColor_exsit() throws Exception {
        try {
            Class<?> clazz = Class.forName(OrderService.class.getName());
            Method validateColorMethod = clazz.getDeclaredMethod("validateColor", String.class);
            validateColorMethod.setAccessible(true);

            try {
                validateColorMethod.invoke(orderService, "");
            } catch (BizException bizException) {
                Assertions.assertEquals("60007", bizException.getCode());
            }
        } catch (Exception e) {}
    }

    @Test
    void validateColor_format() throws Exception {
        try {
            Class<?> clazz = Class.forName(OrderService.class.getName());
            Method validateColorMethod = clazz.getDeclaredMethod("validateColor", String.class);
            validateColorMethod.setAccessible(true);

            try {
                validateColorMethod.invoke(orderService, "12345");
            } catch (BizException bizException) {
                Assertions.assertEquals("60005", bizException.getCode());
            }
        } catch (Exception e) {}
    }

    @Test
    void convertColorObject2RGBString() throws Exception {
        Class<?> clazz = Class.forName(OrderService.class.getName());
        Method convertColorObject2RGBStringMethod = clazz.getDeclaredMethod("convertColorObject2RGBString", Color.class);
        convertColorObject2RGBStringMethod.setAccessible(true);

        Color color = Color.decode("#FFFF00");
        Assertions.assertEquals("rgb(255, 255, 0)", convertColorObject2RGBStringMethod.invoke(orderService, color));
    }

    @Test
    void calcCostShipment() throws Exception {
        Class<?> clazz = Class.forName(OrderService.class.getName());
        Method calcCostShipmentMethod = clazz.getDeclaredMethod("calcCostShipment", BigDecimal.class, BigDecimal.class);
        calcCostShipmentMethod.setAccessible(true);

        Assertions.assertEquals(new BigDecimal("3.60"), calcCostShipmentMethod.invoke(orderService, new BigDecimal(2), new BigDecimal(1.8)));
    }
}