package servlets;

import java.io.*;
import java.sql.SQLException;
import java.util.Currency;
import java.util.List;

import dbManager.SQLiteDBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONArray;
import org.json.JSONObject;
import models.CurrencyModel;
import org.sqlite.SQLiteErrorCode;
import repositories.CurrenciesRepository;

@WebServlet(name = "CurrenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {
    CurrenciesRepository currenciesRepository;

    public CurrenciesServlet() throws ClassNotFoundException {
        currenciesRepository = new CurrenciesRepository(new SQLiteDBManager());
    }

    // Endpoint: GET /currencies
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();

        try {
            List<CurrencyModel> currencies = currenciesRepository.findAll();
            pw.write((new JSONArray(currencies)).toString());
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            String errorMessage = e.getMessage();

            String jsonErrorMessage = "Ошибка доступа к базе данных";

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", jsonErrorMessage);
            pw.write(jsonObject.toString());

            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
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