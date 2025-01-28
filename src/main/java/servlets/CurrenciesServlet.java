package servlets;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "CurrenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {

    // Endpoint: GET /currencies
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
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