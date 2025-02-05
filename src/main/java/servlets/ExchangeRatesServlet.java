package servlets;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import dbManager.SQLiteDBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.ExchangeRateModel;
import org.json.JSONArray;
import repositories.CurrenciesRepository;
import repositories.ExchangeRateRepository;

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
            throw new RuntimeException(e); //TODO implement SQLException handling
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
    }

    public void destroy() {
    }
}