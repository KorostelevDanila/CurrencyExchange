package servlets;

import java.io.*;
import java.util.Currency;
import java.util.List;

import dbManager.SQLiteDBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.CurrencyModel;
import repositories.CurrenciesRepository;

@WebServlet(name = "CurrenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {
    CurrenciesRepository currenciesRepository;

    public CurrenciesServlet() throws ClassNotFoundException {
        currenciesRepository = new CurrenciesRepository(new SQLiteDBManager());
    }

    // Endpoint: GET /currencies
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<CurrencyModel> currencies = currenciesRepository.findAll();

        response.setContentType("text/html");

        PrintWriter pw = response.getWriter();

        for (var a : currencies) {
            pw.println(a.toString() + "\n");
        }

        //response.setContentType("application/json");

    }

    // Endpoint: POST /currencies
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        super.doPost(request, response);
    }

    public void destroy() {
    }
}