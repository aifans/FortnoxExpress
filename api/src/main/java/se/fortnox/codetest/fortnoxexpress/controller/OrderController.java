package se.fortnox.codetest.fortnoxexpress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import se.fortnox.codetest.fortnoxexpress.aspect.WebLog;
import se.fortnox.codetest.fortnoxexpress.exception.ApiResult;
import se.fortnox.codetest.fortnoxexpress.exception.BizException;
import se.fortnox.codetest.fortnoxexpress.exception.ErrorEnum;
import se.fortnox.codetest.fortnoxexpress.model.Order;
import se.fortnox.codetest.fortnoxexpress.model.OrderAddDTO;
import se.fortnox.codetest.fortnoxexpress.model.OrderListDTO;
import se.fortnox.codetest.fortnoxexpress.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    public ApiResult getAllOrders() {
        List<Order> orderList = this.orderService.getAllOrders();

        logger.debug("orders fetched: {}", orderList.toString());

        List<OrderListDTO> orderDTOList = this.convertOrder2OrderListDTO(orderList);
        return ApiResult.success(orderDTOList);
    }

    public ApiResult getOrders(int pageIdx, int quantity) {
        List<Order> orderList = this.orderService.getAllOrders();

        logger.debug("orders fetched: {}", orderList.toString());

        List<OrderListDTO> orderDTOList = this.convertOrder2OrderListDTO(orderList);
        return ApiResult.success(orderDTOList);
    }

    public ApiResult placeAnOrder(OrderAddDTO orderAddDTO) {
        Order order = this.convertOrderAddDTO2Order(orderAddDTO);
        Order order2Add = this.orderService.placeAnOrder(order);
        if (order2Add.getOrderId().isEmpty())
            return ApiResult.error(ErrorEnum.PLACE_ORDER_FAILURE, order2Add);
        else
            return ApiResult.success(order2Add);

    }

    private List<OrderListDTO> convertOrder2OrderListDTO(List<Order> orderList) {
        List<OrderListDTO> orderListDTOList = new ArrayList<>();

        orderList.forEach(order -> {
            OrderListDTO orderListDTO = new OrderListDTO();

            orderListDTO.setOrderId(order.getOrderId());
            orderListDTO.setNameRecv(order.getNameRecv());
            orderListDTO.setWeightBox(order.getWeightBox());
            orderListDTO.setColorBox(order.getColorBox());
            orderListDTO.setCostShipment(order.getCostShipment());

            orderListDTOList.add(orderListDTO);
        });

        return orderListDTOList;

    }

    private Order convertOrderAddDTO2Order(OrderAddDTO orderAddDTO) {

        this.validateOrderAddDTO(orderAddDTO);

        return this.doConvertOrderAddDTO2Order(orderAddDTO);

    }

    private void validateOrderAddDTO(OrderAddDTO orderAddDTO) {
        validateNameRecv(orderAddDTO.getNameRecv());
        validateWeightBox(orderAddDTO.getWeightBox());
        validateColorBox(orderAddDTO.getColorBox().trim());
        validateCountryNameDest(orderAddDTO.getCountryNameDest());
    }

    private Order doConvertOrderAddDTO2Order(OrderAddDTO orderAddDTO) {
        Order order = new Order();
        order.setNameRecv(orderAddDTO.getNameRecv());
        order.setWeightBox(orderAddDTO.getWeightBox());
        order.setColorBox(orderAddDTO.getColorBox());
        order.setCountryNameDest(orderAddDTO.getCountryNameDest());
        order.setCostShipment(new BigDecimal(0));

        return order;
    }

    private void validateNameRecv(String name) {
        if (!StringUtils.hasText(name)) {
            throw new BizException(ErrorEnum.INVALID_ORDER_NAME);
        }
    }

    private void validateWeightBox(BigDecimal weight) {
        if (weight.compareTo(new BigDecimal(0)) == -1) {
            throw new BizException(ErrorEnum.INVALID_ORDER_WEIGHT);
        }
    }

    private void validateColorBox(String color) {
        if (!StringUtils.hasText(color)) {
            throw new BizException(ErrorEnum.INVALID_ORDER_COLOR_EXIST);
        }
    }

    private void validateCountryNameDest(String country) {
        if (!StringUtils.hasText(country)) {
            throw new BizException(ErrorEnum.INVALID_ORDER_COUNTRY);
        }
    }


}
