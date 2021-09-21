package se.fortnox.codetest.fortnoxexpress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.fortnox.codetest.fortnoxexpress.aspect.WebLog;
import se.fortnox.codetest.fortnoxexpress.exception.ApiResult;
import se.fortnox.codetest.fortnoxexpress.exception.ErrorEnum;
import se.fortnox.codetest.fortnoxexpress.model.CountryDTO;
import se.fortnox.codetest.fortnoxexpress.model.CountryMultiplier;
import se.fortnox.codetest.fortnoxexpress.model.Order;
import se.fortnox.codetest.fortnoxexpress.model.OrderAddDTO;
import se.fortnox.codetest.fortnoxexpress.service.ICountryService;
import se.fortnox.codetest.fortnoxexpress.service.IOrderService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/express")
public class ExpressController {
    private static final Logger logger = LoggerFactory.getLogger(ExpressController.class);

    private final OrderController orderController;
    private final CountryController countryController;

    @Autowired
    public ExpressController(OrderController orderController, CountryController countryController) {
        this.orderController = orderController;
        this.countryController = countryController;
    }

    @WebLog(description = "require list all orders")
    @GetMapping(path = {"", "/", "/listorders"}, produces = "application/json")
    public ApiResult getAllOrders() {
        return this.orderController.getAllOrders();
    }

    @WebLog(description = "require place an order")
    @PostMapping(path = "/placeanorder")
    public ApiResult placeAnOrder(@RequestBody OrderAddDTO orderAddDTO) {
        // {"nameRecv":  "abcd123", "weightBox": 12, "colorBox": "#23EF00", "countryNameDest": "Sweden"}
        return this.orderController.placeAnOrder(orderAddDTO);
    }

    @WebLog(description = "require all country names")
    @GetMapping(path = "/getallcountrynames")
    public ApiResult getAllCountryNames() {
        return this.countryController.getAllCountryNames();
    }


}
