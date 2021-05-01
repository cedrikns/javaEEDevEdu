package ru.tsedrik.service;

import ru.tsedrik.dao.ExchangeRateDAO;
import ru.tsedrik.dao.map.ExchangeRateDAOImpl;
import ru.tsedrik.entity.Rate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateDAO exchangeRateDAO;

    public ExchangeRateServiceImpl(Map<LocalDate, List<Rate>> map) {
        this.exchangeRateDAO = new ExchangeRateDAOImpl(map);
    }

    @Override
    public List<Rate> getList(LocalDate localDate) {
        return exchangeRateDAO.getList(localDate);
    }

}
