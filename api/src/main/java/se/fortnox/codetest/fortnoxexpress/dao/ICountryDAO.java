package se.fortnox.codetest.fortnoxexpress.dao;

import se.fortnox.codetest.fortnoxexpress.model.CountryMultiplier;
import se.fortnox.codetest.fortnoxexpress.model.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ICountryDAO {
    List<CountryMultiplier> getAllCountries();
    List<Map<String, Object>> getCountryNameDest();
    BigDecimal getCountryMultiplier(String countryNameDest);
}
