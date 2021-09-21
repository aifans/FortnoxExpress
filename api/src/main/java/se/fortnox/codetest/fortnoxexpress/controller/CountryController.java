package se.fortnox.codetest.fortnoxexpress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import se.fortnox.codetest.fortnoxexpress.aspect.WebLog;
import se.fortnox.codetest.fortnoxexpress.exception.ApiResult;
import se.fortnox.codetest.fortnoxexpress.exception.BizException;
import se.fortnox.codetest.fortnoxexpress.exception.ErrorEnum;
import se.fortnox.codetest.fortnoxexpress.model.*;
import se.fortnox.codetest.fortnoxexpress.service.ICountryService;
import se.fortnox.codetest.fortnoxexpress.service.IOrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CountryController {
    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    private final ICountryService countryService;

    @Autowired
    public CountryController(ICountryService countryService) {
        this.countryService = countryService;
    }

    public ApiResult getAllCountryNames() {

        List<CountryMultiplier> countryList = this.countryService.getAllCountries();

        logger.debug("orders fetched: {}", countryList.toString());

        List<CountryDTO> countryDTOList = this.convertCountry2CountryDTO(countryList);
        return ApiResult.success(countryDTOList);

    }

    private List<CountryDTO> convertCountry2CountryDTO(List<CountryMultiplier> countryList) {
        List<CountryDTO> countryDTOList = new ArrayList<>();

        countryList.forEach(order -> {
            CountryDTO countryDTO = new CountryDTO();

            countryDTO.setId(order.getId());
            countryDTO.setCountryDest(order.getCountryDest());
            countryDTO.setCountryMultiplier(order.getCountryMultiplier());

            countryDTOList.add(countryDTO);
        });

        return countryDTOList;

    }


}
