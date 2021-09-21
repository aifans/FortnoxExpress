package se.fortnox.codetest.fortnoxexpress.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO {

    private int id;
    private String countryDest;
    private BigDecimal countryMultiplier;

}
