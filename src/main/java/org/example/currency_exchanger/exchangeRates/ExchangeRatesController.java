package org.example.currency_exchanger.exchangeRates;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchanger.currency.exceptions.ProvidedCurrencyDoesntExistException;
import org.example.currency_exchanger.exchangeRate.ExchangeRateResponseDTO;
import org.example.currency_exchanger.exchangeRate.ExchangeRateService;
import org.example.currency_exchanger.exchangeRate.exceptions.ExistingExchangeRateException;
import org.example.currency_exchanger.exchangeRates.exceptions.FormFieldNotFoundException;
import org.example.currency_exchanger.exchangeRates.exceptions.NoExchangeRatesException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.example.currency_exchanger.commons.Utils.sendJsonErrorResponse;
import static org.example.currency_exchanger.commons.Utils.sendJsonResponse;
import static org.example.currency_exchanger.exchangeRates.ExchangeRatesUtils.checkNewExchangeParams;

@WebServlet(name = "exchangeRatesServlet", value = "/exchangeRates")
public class ExchangeRatesController extends HttpServlet {
    private ExchangeRatesService exchangeRatesService;
    private ExchangeRateService exchangeRateService;
    private Gson gson;

    @Override
    public void init() {
        this.exchangeRatesService = new ExchangeRatesService();
        this.exchangeRateService = new ExchangeRateService();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<ExchangeRateResponseDTO> exchangeRateResponseDTOList = exchangeRatesService.getExchangeRates();
            String json = gson.toJson(exchangeRateResponseDTOList);
            sendJsonResponse(response, HttpServletResponse.SC_OK, json);
        } catch (SQLException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (NoExchangeRatesException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String baseCurrencyCode = request.getParameter("baseCurrencyCode");
        String targetCurrencyCode = request.getParameter("targetCurrencyCode");
        String rate = request.getParameter("rate");

        try {
            checkNewExchangeParams(baseCurrencyCode, targetCurrencyCode, rate);
            ExchangeRateResponseDTO exchangeRateResponseDTO = exchangeRateService.addExchangeRate(baseCurrencyCode, targetCurrencyCode, new BigDecimal(rate));
            String json = gson.toJson(exchangeRateResponseDTO);
            sendJsonResponse(response, HttpServletResponse.SC_CREATED, json);
        } catch (FormFieldNotFoundException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (ExistingExchangeRateException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_CONFLICT, e.getMessage());
        } catch (ProvidedCurrencyDoesntExistException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (SQLException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (RuntimeException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void destroy() {
    }
}