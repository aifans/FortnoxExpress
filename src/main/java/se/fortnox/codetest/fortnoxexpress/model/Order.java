package se.fortnox.codetest.fortnoxexpress.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private int id;
    private String orderId;
    private String nameRecv;
    private BigDecimal weightBox;
    private String colorBox;
    private String countryNameDest;
    private BigDecimal costShipment;

}
