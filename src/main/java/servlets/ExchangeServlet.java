package servlets;

import java.io.*;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Map;

import dtos.ExchangeTransferObject;
import exceptions.NotFoundInDatabaseException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;
import services.ExchangeService;
import utlis.GetParameters;
import utlis.JSONResponser;

@WebServlet(name = "ExchangeServlet", value = "/exchange/*")
public class ExchangeServlet extends HttpServlet {
    ExchangeService exchangeService;

    public ExchangeServlet() throws ClassNotFoundException {
        exchangeService = new ExchangeService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();

        String baseCurrencyCode, targetCurrencyCode;
        BigDecimal amount;

        Map<String, String> params = GetParameters.parseFormBody(request);

        baseCurrencyCode = params.get("from");
        targetCurrencyCode = params.get("to");
        amount = new BigDecimal(params.get("amount"));

        try {
            ExchangeTransferObject exchangeResult = exchangeService.exchange(baseCurrencyCode, targetCurrencyCode, amount);
            JSONObject jsonObject = new JSONObject(exchangeResult);
            pw.write(jsonObject.toString());
        } catch (NotFoundInDatabaseException e) {
            String message = e.getMessage();
            JSONResponser.sendJSONErrorMessage(message, 400, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void destroy() {
    }
}