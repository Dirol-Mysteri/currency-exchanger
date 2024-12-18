package org.example.currency_exchanger.exchangeRate;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchanger.commons.Utils;
import org.example.currency_exchanger.exchangeRate.exceptions.InvalidExchangeRateCode;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import static org.example.currency_exchanger.commons.Utils.*;
import static org.example.currency_exchanger.exchangeRates.ExchangeRatesUtils.getCurrencyCodesFromExchangeRate;

@WebServlet(name = "exchangeRateServlet", value = "/exchangeRate/*")
public class ExchangeRateController extends HttpServlet {
    private Gson gson;
    private ExchangeRateService exchangeRateService;

    @Override
    public void init() {
        this.exchangeRateService = new ExchangeRateService();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String exchangeRateCode = getDynamicParameterFromRequest(request);

        try {
            String[] currencyCodes = getCurrencyCodesFromExchangeRate(exchangeRateCode);
            ExchangeRateResponseDTO exchangeRateResponseDTO = exchangeRateService.getExchangeRate(currencyCodes[0], currencyCodes[1]);
            String json = gson.toJson(exchangeRateResponseDTO);
            sendJsonResponse(response, HttpServletResponse.SC_OK, json);
        } catch (InvalidExchangeRateCode e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (RuntimeException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_CONFLICT, e.getMessage());
        } catch (SQLException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    @Override
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> bodyParameters = Utils.extractBodyFromRequest(request);
        String rate = bodyParameters.get("rate");
        String exchangeRateCode = getDynamicParameterFromRequest(request);

        try {
            if (rate == null) {
                sendJsonErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Rate field is required");
                return;
            }
            String[] currencyCodes = getCurrencyCodesFromExchangeRate(exchangeRateCode);
            ExchangeRateResponseDTO exchangeRateResponseDTO = exchangeRateService.getExchangeRate(currencyCodes[0], currencyCodes[1]);
            ExchangeRateResponseDTO updatedExchangeRateResponseDTO = exchangeRateService.updateExchangeRate(exchangeRateResponseDTO, new BigDecimal(rate));
            sendJsonResponse(response, HttpServletResponse.SC_OK, gson.toJson(updatedExchangeRateResponseDTO));
        } catch (InvalidExchangeRateCode e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (RuntimeException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_CONFLICT, e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void destroy() {
    }
}