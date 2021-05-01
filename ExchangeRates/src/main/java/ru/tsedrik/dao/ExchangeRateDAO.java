package ru.tsedrik.dao;

import ru.tsedrik.entity.Rate;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRateDAO {
    List<Rate> getList(LocalDate localDate);
}
