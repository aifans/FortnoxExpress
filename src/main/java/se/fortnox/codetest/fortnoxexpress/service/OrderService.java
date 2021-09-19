package se.fortnox.codetest.fortnoxexpress.service;

import com.github.yitter.idgen.YitIdHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import se.fortnox.codetest.fortnoxexpress.exception.BizException;
import se.fortnox.codetest.fortnoxexpress.exception.ErrorEnum;
import se.fortnox.codetest.fortnoxexpress.model.Order;
import se.fortnox.codetest.fortnoxexpress.dao.IOrderDAO;

@Service
public class OrderService implements IOrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final IOrderDAO orderDAO;

    @Autowired
    public OrderService(IOrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public List<Order> getAllOrders() {
        return this.orderDAO.getAllOrders();
    }

    @Override
    public Order placeAnOrder(Order order) {

        this.validateOrder(order);

        logger.info("place order, get multiplier...");

        order.setColorBox(this.convertColorObject2RGBString(Color.decode(order.getColorBox().trim())));

        BigDecimal country_multiplier = this.orderDAO.getCountryMultiplier(order.getCountryNameDest());
        order.setCostShipment(this.calcCostShipment(order.getWeightBox(), country_multiplier));

        logger.info("place order, generate order id...");

        String orderId = this.genOrderId();
        order.setOrderId(orderId);

        logger.info("place order, save order to db...");

        int nret = this.orderDAO.placeAnOrder(order);

        if (nret !=  1) {
            logger.error("place order, save order to db error. retcode = {}", nret);
            order.setOrderId("");
        }
        return order;
    }

    private void validateOrder(Order order) {
        this.validateColor(order.getColorBox().trim());
    }

    private void validateColor(String colorHEXString) {
        Color color;

        try {
            color = Color.decode(colorHEXString);
        } catch (NumberFormatException e) {
            throw new BizException(ErrorEnum.INVALID_ORDER_COLOR_INVALID);
        } catch (NullPointerException e) {
            throw new BizException(ErrorEnum.INVALID_ORDER_COLOR_NULL);
        }

        if (color.getBlue() != 0) {
            throw new BizException(ErrorEnum.INVALID_ORDER_COLOR);
        }
    }

    private String convertColorObject2RGBString(Color color) {
        return String.format("rgb(%d, %d, %d)", color.getRed(), color.getGreen(), color.getBlue());
    }

    private String genOrderId() {
        long orderId = YitIdHelper.nextId();
        return String.valueOf(orderId);
    }

    private BigDecimal calcCostShipment(BigDecimal weightBox, BigDecimal country_multiplier) {
        return weightBox.multiply(country_multiplier).setScale(2, RoundingMode.HALF_EVEN);
    }

}
