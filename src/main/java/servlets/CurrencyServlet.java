package servlets;import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "CurrencyServlet", value = "/currency")
public class CurrencyServlet extends HttpServlet {

    // Endpoint: GET /currency/value
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
    }

    public void destroy() {
    }
}