package ru.tsedrik.service;

import ru.tsedrik.entity.Rate;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRateService {

    List<Rate> getList(LocalDate localDate);
}
