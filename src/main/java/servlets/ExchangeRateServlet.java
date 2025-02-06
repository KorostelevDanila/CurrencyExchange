package servlets;

import java.io.*;

import dbManager.SQLiteDBManager;
import exceptions.NotFoundInDatabaseException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.ExchangeRateModel;
import org.json.JSONObject;
import repositories.CurrenciesRepository;
import repositories.ExchangeRateRepository;
import utlis.JSONResponser;
import utlis.PathChecker;

@WebServlet(name = "ExchangeRateServlet", value = "/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    ExchangeRateRepository exchangeRateRepository;
    CurrenciesRepository currenciesRepository;

    public ExchangeRateServlet() throws ClassNotFoundException {
        this.currenciesRepository = new CurrenciesRepository(new SQLiteDBManager());
        this.exchangeRateRepository = new ExchangeRateRepository(new SQLiteDBManager(), currenciesRepository);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
    }

    public void destroy() {
    }
}