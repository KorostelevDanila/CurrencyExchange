package servlets;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import dbManager.SQLiteDBManager;
import exceptions.NotFoundInDatabaseException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.ExchangeRateModel;
import org.json.JSONObject;
import repositories.CurrenciesRepository;
import repositories.ExchangeRateRepository;
import utlis.GetParameters;
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

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("PATCH".equalsIgnoreCase(req.getMethod())) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();

        String path = request.getPathInfo();

        Map<String, String> exchangePair = PathChecker.checkExchangeRatePath(path);

        if (exchangePair == null) {
            String message = "Неправильный запрос обеменного курса. Нужно предоставить пару кодов валют, например /USDRUB";
            JSONObject jsonObject = new JSONObject().put("message", message);
            pw.write(jsonObject.toString());
            response.setStatus(400);
        } else {
            try {
                ExchangeRateModel exchangeRateModel = exchangeRateRepository.findByExchangePair(exchangePair);
                JSONObject jsonObject = new JSONObject(exchangeRateModel);
                pw.write(jsonObject.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NotFoundInDatabaseException e) {
                String message = e.getMessage();
                JSONResponser.sendJSONErrorMessage(message, 404, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
    }

    @Override
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        request.setCharacterEncoding("UTF-8");

        String path = request.getPathInfo();

        Map<String, String> params = GetParameters.parseFormBody(request);

        BigDecimal rate = new BigDecimal(params.get("rate"));
        Map<String, String> exchangePair = PathChecker.checkExchangeRatePath(path);
        ExchangeRateModel updatedExchangeRateModel;

        if (exchangePair == null) {
            String message = "Неправильный запрос на обновление обеменного курса. Нужно предоставить пару кодов валют, например /USDRUB";
            JSONResponser.sendJSONErrorMessage(message, 400, response);
        } else {
            try {
                ExchangeRateModel exchangeRateModel = exchangeRateRepository.findByExchangePair(exchangePair);
                updatedExchangeRateModel = exchangeRateRepository.update(
                        new ExchangeRateModel(exchangeRateModel.getID(),
                                exchangeRateModel.getBaseCurrency(),
                                exchangeRateModel.getTargetCurrency(),
                                rate)
                );


                JSONObject jsonObject = new JSONObject(updatedExchangeRateModel);
                pw.write(jsonObject.toString());
            } catch (SQLException e) {
                throw  new RuntimeException(e);
            }
        }
    }

    public void destroy() {
    }
}