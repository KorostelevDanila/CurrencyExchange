package servlets;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbManager.SQLiteDBManager;
import exceptions.NotFoundInDatabaseException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.CurrencyModel;
import models.ExchangeRateModel;
import org.json.JSONArray;
import org.json.JSONObject;
import repositories.CurrenciesRepository;
import repositories.ExchangeRateRepository;
import utlis.JSONResponser;

@WebServlet(name = "ExchangeRatesServlet", value = "/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    ExchangeRateRepository exchangeRateRepository;
    CurrenciesRepository currenciesRepository;

    public ExchangeRatesServlet() throws ClassNotFoundException {
        currenciesRepository = new CurrenciesRepository(new SQLiteDBManager());
        exchangeRateRepository = new ExchangeRateRepository(new SQLiteDBManager(), currenciesRepository);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();

        try {
            List<ExchangeRateModel> exchangeRates = exchangeRateRepository.findAll();
            JSONArray jsonArray = new JSONArray(exchangeRates);
            pw.write(jsonArray.toString());
        } catch (SQLException e) {
            String jsonErrorMessage = "Ошибка доступа к базе данных.";
            JSONResponser.sendJSONErrorMessage(jsonErrorMessage, HttpServletResponse.SC_SERVICE_UNAVAILABLE, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();

        String baseCurrencyCode, targetCurrencyCode;
        BigDecimal rate;

        try {
            baseCurrencyCode = request.getParameter("baseCurrencyCode");
            targetCurrencyCode = request.getParameter("targetCurrencyCode");

            if (baseCurrencyCode == null || baseCurrencyCode.isBlank() ||
                    targetCurrencyCode == null || targetCurrencyCode.isBlank() ||
                    request.getParameter("rate") == null) {
                String jsonErrorMessage = "Все поля должны быть заполнены.";
                JSONResponser.sendJSONErrorMessage(jsonErrorMessage, HttpServletResponse.SC_BAD_REQUEST, response);
                return;
            }

            rate = new BigDecimal(request.getParameter("rate"));

            CurrencyModel baseCurrency = currenciesRepository.findByCode(baseCurrencyCode);
            CurrencyModel targetCurrency = currenciesRepository.findByCode(targetCurrencyCode);

            try {
                Map<String, String> exchangePair = new HashMap<String, String>();
                exchangePair.put("from", baseCurrencyCode);
                exchangePair.put("to", targetCurrencyCode);
                ExchangeRateModel exchangeRate = exchangeRateRepository.findByExchangePair(exchangePair);
                String jsonErrorMessage = "Пара обмена уже существует в базе данных.";
                JSONResponser.sendJSONErrorMessage(jsonErrorMessage, HttpServletResponse.SC_CONFLICT, response);
            } catch (NotFoundInDatabaseException e) {
                ExchangeRateModel exchangeRate = exchangeRateRepository.insert(new ExchangeRateModel(baseCurrency, targetCurrency, rate));

                JSONObject jsonObject = new JSONObject(exchangeRate);
                pw.write(jsonObject.toString());
            }
        } catch (SQLException e) {
            String jsonErrorMessage = "Ошибка доступа к базе данных";
            JSONResponser.sendJSONErrorMessage(jsonErrorMessage, HttpServletResponse.SC_SERVICE_UNAVAILABLE, response);
        } catch (NotFoundInDatabaseException e) {
            String jsonErrorMessage = e.getMessage();
            JSONResponser.sendJSONErrorMessage(jsonErrorMessage, HttpServletResponse.SC_NOT_FOUND, response);
        }
    }

    public void destroy() {
    }
}