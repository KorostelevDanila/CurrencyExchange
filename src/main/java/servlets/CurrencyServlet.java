package servlets;import java.io.*;
import java.sql.SQLException;

import dbManager.SQLiteDBManager;
import exceptions.NotFoundInDatabaseException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.CurrencyModel;
import org.json.JSONArray;
import org.json.JSONObject;
import repositories.CurrenciesRepository;
import utlis.JSONResponser;
import utlis.PathChecker;

@WebServlet(name = "CurrencyServlet", value = "/currency/*")
public class CurrencyServlet extends HttpServlet {
    CurrenciesRepository currenciesRepository;

    public CurrencyServlet() throws ClassNotFoundException {
        this.currenciesRepository = new CurrenciesRepository(new SQLiteDBManager());
    }

    // Endpoint: GET /currency/value
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();

        String pathInfo = request.getPathInfo();

        String code = PathChecker.checkCurrencyPath(pathInfo);

        if (code != null) {
            try {
                CurrencyModel currency = currenciesRepository.findByCode(code);
                JSONObject jsonObject = new JSONObject(currency);
                pw.write(jsonObject.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NotFoundInDatabaseException e) {
                String message = "Не удалось найти валюту.";
                JSONResponser.sendJSONErrorMessage(message, 404, response);
            }
        } else {
            String message = "Неправильный запрос валюты. Нужно предоставить корректный код валюты, например USD";
            JSONResponser.sendJSONErrorMessage(message, 400, response);
        }

    }


    public void destroy() {

    }
}