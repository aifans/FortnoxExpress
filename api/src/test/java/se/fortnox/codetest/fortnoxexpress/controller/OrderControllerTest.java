package se.fortnox.codetest.fortnoxexpress.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.server.ResponseStatusException;
import se.fortnox.codetest.fortnoxexpress.exception.ApiResult;
import se.fortnox.codetest.fortnoxexpress.exception.ErrorEnum;
import se.fortnox.codetest.fortnoxexpress.model.Order;
import se.fortnox.codetest.fortnoxexpress.model.OrderAddDTO;
import se.fortnox.codetest.fortnoxexpress.model.OrderListDTO;
import se.fortnox.codetest.fortnoxexpress.service.IOrderService;
import se.fortnox.codetest.fortnoxexpress.service.OrderService;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IOrderService orderService;

    @Autowired
    OrderController orderController;

    @Test
    void shouldAlwaysPassed() {
        Assertions.assertTrue(true);
    }

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

        Mockito.when(orderService.getAllOrders()).thenReturn(orderListFake);

        List<OrderListDTO> orderDTOList = new ArrayList<>();
        OrderListDTO orderListDTO = new OrderListDTO(
                order.getId(),
                order.getNameRecv(),
                order.getWeightBox(),
                order.getColorBox(),
                order.getCostShipment()
        );
        orderDTOList.add(orderListDTO);

        Assertions.assertEquals(orderDTOList, orderController.getAllOrders().getResult());
    }

    @Test
    void getAllOrders_exception() throws Exception {
        //Mockito.when(orderService.getAllOrders()).thenThrow(RuntimeException.class);
        Mockito.when(orderService.getAllOrders()).thenAnswer(I -> 1/0);

        mockMvc.perform(get("/express/listorders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500));

    }

    @Test
    void placeAnOrder() {
        OrderAddDTO orderAddDTOFake = new OrderAddDTO(
                "hahaha",
                new BigDecimal(1.0),
                "#FFFE00",
                "Sweden");

        Order orderFake = new Order(
                333,
                "85854422123",
                orderAddDTOFake.getNameRecv(),
                orderAddDTOFake.getWeightBox(),
                orderAddDTOFake.getColorBox(),
                orderAddDTOFake.getCountryNameDest(),
                new BigDecimal(0.0));

        Mockito.doReturn(orderFake).when(orderService).placeAnOrder(any());

        Assertions.assertEquals(orderFake, orderController.placeAnOrder(orderAddDTOFake).getResult());
    }

    @Test
    void placeAnOrder_name() throws Exception {
        OrderAddDTO orderAddDTOFake = new OrderAddDTO(
                "",
                new BigDecimal(1.0),
                "#FFFE00",
                "Sweden");

        mockMvc.perform(post("/express/placeanorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(orderAddDTOFake)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(60001));

    }

    @Test
    void placeAnOrder_weight() throws Exception {
        OrderAddDTO orderAddDTOFake = new OrderAddDTO(
                "1abcd",
                new BigDecimal(-1.0),
                "#FFFE00",
                "Sweden");

        mockMvc.perform(post("/express/placeanorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(orderAddDTOFake)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(60002));

    }

    @Test
    void placeAnOrder_color() throws Exception {
        OrderAddDTO orderAddDTOFake = new OrderAddDTO(
                "1abcd",
                new BigDecimal(1.0),
                "",
                "Sweden");

        mockMvc.perform(post("/express/placeanorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(orderAddDTOFake)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(60007));

    }

    @Test
    void placeAnOrder_country() throws Exception {
        OrderAddDTO orderAddDTOFake = new OrderAddDTO(
                "1abcd",
                new BigDecimal(1.0),
                "#FFFE00",
                "");

        mockMvc.perform(post("/express/placeanorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(orderAddDTOFake)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(60003));

    }

    @Test
    void getAllCountryNames() {

    }

}