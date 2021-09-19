package se.fortnox.codetest.fortnoxexpress.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAddDTO {

    private String nameRecv;
    private BigDecimal weightBox;
    private String colorBox;            // format: #ff0000
    private String countryNameDest;     // Sweden

}
