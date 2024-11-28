package org.example.currency_exchange.currencies;

import java.io.*;
import java.util.List;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "currenciesServlet", value = "/currencies")
public class CurrenciesController extends HttpServlet {
    CurrenciesService currenciesService;

    public void init() {
        currenciesService = new CurrenciesService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        List<CurrencyResponseDTO> currencies = currenciesService.getCurrencies();
        for (CurrencyResponseDTO dto : currencies) {
            out.println(dto);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1></h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}