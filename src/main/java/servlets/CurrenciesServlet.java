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
import utlis.JSONResponser;

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

            JSONResponser.sendJSONErrorMessage(jsonErrorMessage, HttpServletResponse.SC_SERVICE_UNAVAILABLE, response);
        }
    }

    // Endpoint: POST /currencies
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();

        String code, fullName, sign;

        code = request.getParameter("code");
        fullName = request.getParameter("fullName");
        sign = request.getParameter("sign");

        CurrencyModel newCurrencyForPosting = new CurrencyModel(code, fullName, sign);

        CurrencyModel newCurrencyInRepository = currenciesRepository.insert(newCurrencyForPosting);
        JSONObject jsonObject = new JSONObject(newCurrencyInRepository);
        pw.write(jsonObject.toString());
    }

    public void destroy() {
    }
}