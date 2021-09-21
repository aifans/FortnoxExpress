package se.fortnox.codetest.fortnoxexpress.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import se.fortnox.codetest.fortnoxexpress.controller.OrderController;
import se.fortnox.codetest.fortnoxexpress.dao.ICountryDAO;
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

    @MockBean
    ICountryDAO countryDAO;


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

        Mockito.doReturn(new BigDecimal(5)).when(countryDAO).getCountryMultiplier(any());
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

        Mockito.doReturn(new BigDecimal(5)).when(countryDAO).getCountryMultiplier(any());
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
            ReflectionTestUtils.invokeMethod(orderService, "validateColor", "#000001");
        } catch (BizException bizException) {
            Assertions.assertEquals("60004", bizException.getCode());
        }

    }

    @Test
    void validateColor_exsit() {
        try {
            ReflectionTestUtils.invokeMethod(orderService, "validateColor", "");
        } catch (BizException bizException) {
            Assertions.assertEquals("60007", bizException.getCode());
        }

    }

    @Test
    void validateColor_format() {
        try {
            ReflectionTestUtils.invokeMethod(orderService, "validateColor", "12G345");
        } catch (BizException bizException) {
            Assertions.assertEquals("60005", bizException.getCode());
        }

    }

    @Test
    void convertColorObject2RGBString() {
        Color color = Color.decode("#FFFF00");
        Assertions.assertEquals("rgb(255, 255, 0)",
                ReflectionTestUtils.invokeMethod(orderService, "convertColorObject2RGBString", color));
    }

    @Test
    void calcCostShipment() {
        Assertions.assertEquals(new BigDecimal("3.60"),
                ReflectionTestUtils.invokeMethod(orderService, "calcCostShipment", new BigDecimal(2), new BigDecimal(1.8)));
    }

}
