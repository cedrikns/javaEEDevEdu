package ru.tsedrik.dao.map;

import ru.tsedrik.dao.ExchangeRateDAO;
import ru.tsedrik.entity.Rate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ExchangeRateDAOImpl implements ExchangeRateDAO {

    Map<LocalDate, List<Rate>> exchangeRates;

    public ExchangeRateDAOImpl(Map<LocalDate, List<Rate>> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    @Override
    public List<Rate> getList(LocalDate localDate) {
        return exchangeRates.get(localDate);
    }


}
