package org.example.currency_exchanger.exchange;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchanger.exchangeRates.exceptions.NoExchangeRatesException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import static org.example.currency_exchanger.commons.Utils.sendJsonErrorResponse;
import static org.example.currency_exchanger.commons.Utils.sendJsonResponse;
import static org.example.currency_exchanger.exchange.ExchangeUtils.checkExchangeParams;

@WebServlet(name = "exchangeServlet", value = "/exchange")
public class ExchangeController extends HttpServlet {
    private Gson gson;
    private ExchangeService exchangeService;

    @Override
    public void init() {
        this.gson = new Gson();
        this.exchangeService = new ExchangeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String baseCurrencyCode = request.getParameter("from");
        String targetCurrencyCode = request.getParameter("to");
        String amount = request.getParameter("amount");
        try {
            checkExchangeParams(baseCurrencyCode, targetCurrencyCode, amount);
            ExchangeResponseDTO exchangeResponseDTO = exchangeService.getExchange(baseCurrencyCode, targetCurrencyCode, new BigDecimal(amount));
            String json = gson.toJson(exchangeResponseDTO);
            sendJsonResponse(response, HttpServletResponse.SC_OK, json);
        } catch (NoExchangeRatesException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (SQLException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (RuntimeException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_CONFLICT, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    @Override
    public void destroy() {
    }
}