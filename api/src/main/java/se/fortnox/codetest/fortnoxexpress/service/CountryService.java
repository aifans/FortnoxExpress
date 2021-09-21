package se.fortnox.codetest.fortnoxexpress.service;

import com.github.yitter.idgen.YitIdHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import se.fortnox.codetest.fortnoxexpress.dao.ICountryDAO;
import se.fortnox.codetest.fortnoxexpress.dao.IOrderDAO;
import se.fortnox.codetest.fortnoxexpress.exception.BizException;
import se.fortnox.codetest.fortnoxexpress.exception.ErrorEnum;
import se.fortnox.codetest.fortnoxexpress.model.CountryMultiplier;
import se.fortnox.codetest.fortnoxexpress.model.Order;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CountryService implements ICountryService {
    private static final Logger logger = LoggerFactory.getLogger(CountryService.class);

    private final ICountryDAO countryDAO;

    @Autowired
    public CountryService(ICountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    @Override
    public List<CountryMultiplier> getAllCountries() {
        return this.countryDAO.getAllCountries();
    }

}
