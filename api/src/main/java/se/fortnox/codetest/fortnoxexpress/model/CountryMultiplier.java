package se.fortnox.codetest.fortnoxexpress.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CountryMultiplier {

    private int id = -1;
    private String countryDest;
    private BigDecimal countryMultiplier;

}
