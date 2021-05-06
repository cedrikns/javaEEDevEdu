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
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@WebServlet("/archive")
public class ArchiveRatesServlet extends HttpServlet {
    private ExchangeRateService exchangeRateService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("archiveForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String date = req.getParameter("date");

        if (date.isEmpty()){
            req.setAttribute("message", "Вы не выбрали никакой даты. Пожалуйста, выберите интересующую вас.");
        } else {
            LocalDate chosenDate;
            try {
                chosenDate = LocalDate.parse(date);
                req.setAttribute("archiveDate", chosenDate);
                List<Rate> res = exchangeRateService.getList(chosenDate);
                if ((res == null) || (res.isEmpty())){
                    req.setAttribute("message", "На заданную дату данные отсутствуют. Пожалуйста, выберите другую.");
                } else {
                    req.setAttribute("archiveRates", res);
                }
            } catch (DateTimeParseException e) {
                req.setAttribute("message", "Что-то пошло не так. Пожалуйста, попробуйте еще раз.");
            }
        }

        req.getRequestDispatcher("archiveRates.jsp").forward(req, resp);
    }

    @Override
    public void init() throws ServletException {
        Map<LocalDate, List<Rate>> exchangeRates = (Map<LocalDate, List<Rate>>) getServletContext().getAttribute("exchangeRates");
        exchangeRateService = new ExchangeRateServiceImpl(exchangeRates);
        super.init();
    }
}
