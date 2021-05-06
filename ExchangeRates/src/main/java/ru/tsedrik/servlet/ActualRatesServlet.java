package ru.tsedrik.servlet;

import ru.tsedrik.entity.Rate;
import ru.tsedrik.service.ExchangeRateService;
import ru.tsedrik.service.ExchangeRateServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@WebServlet("/actual")
public class ActualRatesServlet extends HttpServlet {

    private ExchangeRateService exchangeRateService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Rate> res = exchangeRateService.getList(LocalDate.now());
        req.setAttribute("actualDate", LocalDate.now());
        req.setAttribute("actualRates", res);
        req.getRequestDispatcher("/actualRates.jsp").forward(req, resp);
    }

    @Override
    public void init() throws ServletException {
        Map<LocalDate, List<Rate>> exchangeRates = (Map<LocalDate, List<Rate>>) getServletContext().getAttribute("exchangeRates");
        exchangeRateService = new ExchangeRateServiceImpl(exchangeRates);
        super.init();
    }
}
