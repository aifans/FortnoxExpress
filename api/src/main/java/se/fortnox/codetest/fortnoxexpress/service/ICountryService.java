package se.fortnox.codetest.fortnoxexpress.service;

import se.fortnox.codetest.fortnoxexpress.model.CountryMultiplier;
import se.fortnox.codetest.fortnoxexpress.model.Order;

import java.util.List;

public interface ICountryService {
    List<CountryMultiplier> getAllCountries();
}
