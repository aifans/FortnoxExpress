package se.fortnox.codetest.fortnoxexpress.dao;

import com.github.yitter.idgen.YitIdHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import se.fortnox.codetest.fortnoxexpress.exception.BizException;
import se.fortnox.codetest.fortnoxexpress.exception.ErrorEnum;
import se.fortnox.codetest.fortnoxexpress.model.Order;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDAO implements IOrderDAO {
    private static final Logger logger = LoggerFactory.getLogger(OrderDAO.class);

    private final JdbcTemplate db;

    @Autowired
    public OrderDAO(JdbcTemplate db) {
        this.db = db;
    }

    @Override
    public List<Order> getAllOrders() {
        try {
            List<Map<String, Object>> orderMapList = this.db.queryForList("SELECT * FROM order_shipment");

            logger.debug("data from db: {}", orderMapList.toString());

            List<Order> orderList = this.convertOrderMap2Order(orderMapList);

            logger.info("fetch data from order_shipment success.");
            return orderList;

        } catch (Exception e) {
            logger.error("read order_shipment error: {}", e.getLocalizedMessage());
            throw new BizException(ErrorEnum.SELECT_FAILURE);

        }
    }

    @Override
    public int placeAnOrder(Order order) {

        String sql = "INSERT INTO order_shipment (order_id, name_recv, weight_box, color_box, country_name_dest, cost_shipment) VALUES (?, ?, ?, ?, ?, ?)";

        logger.info("prepare to save order to db...");

        int result = this.db.update(connection -> {
            PreparedStatement ps = connection
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getOrderId());
            ps.setString(2, order.getNameRecv());
            ps.setBigDecimal(3, order.getWeightBox());
            ps.setString(4, order.getColorBox());
            ps.setString(5, order.getCountryNameDest());
            ps.setBigDecimal(6, order.getCostShipment());
            return ps;
        });

        if (result > 0) {
            logger.info("save order to db success. ret: {}", result);
            return result;
        } else {
            logger.warn("save order to db failure. ret: {}", result);
            throw new BizException(ErrorEnum.INSERT_FAILURE);
        }
    }

    @Override
    public List<Map<String, Object>> getCountryNameDest() {
        try {
            String sql = "SELECT country_name_dest FROM country_multiplier";

            List<Map<String, Object>> countryNameDest = this.db.queryForList(sql);

            logger.info("fetch all country names from country_multiplier success.");
            return countryNameDest;
        } catch (Exception e) {
            logger.error("read country_multiplier error: {}", e.getLocalizedMessage());
            throw new BizException(ErrorEnum.SELECT_FAILURE);

        }
    }

    @Override
    public BigDecimal getCountryMultiplier(String countryNameDest) {

        try {
            String sql = "SELECT country_multiplier FROM country_multiplier WHERE country_name_dest = ?";

            BigDecimal country_multiplier = this.db.queryForObject(sql, BigDecimal.class, countryNameDest);

            logger.info("read multiplier from country_multiplier success.");
            return country_multiplier;
        } catch (Exception e) {
            logger.error("read multiplier from country_multiplier error: {}", e.getLocalizedMessage());
            throw new BizException(ErrorEnum.SELECT_FAILURE);

        }
    }

    private List<Order> convertOrderMap2Order(List<Map<String, Object>> orderMapList) {

        List<Order> orderList = new ArrayList<>();

        orderMapList.forEach(orderMap -> {
            Order order = new Order();

            order.setId(Integer.parseInt(orderMap.get("id").toString()));
            order.setOrderId(orderMap.get("order_id").toString());
            order.setNameRecv(orderMap.get("name_recv").toString());
            order.setWeightBox(new BigDecimal(orderMap.get("weight_box").toString()));
            order.setColorBox(orderMap.get("color_box").toString());
            order.setCountryNameDest(orderMap.get("country_name_dest").toString());
            order.setCostShipment(new BigDecimal(orderMap.get("cost_shipment").toString()));

            orderList.add(order);
        });

        return orderList;
    }

}
