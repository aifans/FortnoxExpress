package se.fortnox.codetest.fortnoxexpress.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import se.fortnox.codetest.fortnoxexpress.exception.BizException;
import se.fortnox.codetest.fortnoxexpress.exception.ErrorEnum;
import se.fortnox.codetest.fortnoxexpress.model.CountryMultiplier;
import se.fortnox.codetest.fortnoxexpress.model.Order;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CountryDAO implements ICountryDAO {
    private static final Logger logger = LoggerFactory.getLogger(CountryDAO.class);

    private final JdbcTemplate db;

    @Autowired
    public CountryDAO(JdbcTemplate db) {
        this.db = db;
    }

    @Override
    public List<CountryMultiplier> getAllCountries() {
        try {
            List<Map<String, Object>> countryMapList = this.db.queryForList("SELECT * FROM country_multiplier");

            logger.debug("data from db: {}", countryMapList.toString());

            List<CountryMultiplier> countryList = this.convertCountryMap2Country(countryMapList);

            logger.info("fetch data from country_multiplier success.");
            return countryList;

        } catch (Exception e) {
            logger.error("read country_multiplier error: {}", e.getLocalizedMessage());
            throw new BizException(ErrorEnum.SELECT_FAILURE);

        }
    }

    private List<CountryMultiplier> convertCountryMap2Country(List<Map<String, Object>> countryMapList) {

        List<CountryMultiplier> countryList = new ArrayList<>();

        countryMapList.forEach(countryMap -> {
            CountryMultiplier countryMultiplier = new CountryMultiplier();

            countryMultiplier.setId(Integer.parseInt(countryMap.get("id").toString()));
            countryMultiplier.setCountryDest(countryMap.get("country_name_dest").toString());
            countryMultiplier.setCountryMultiplier(new BigDecimal(countryMap.get("country_multiplier").toString()));

            countryList.add(countryMultiplier);
        });

        return countryList;
    }

}
