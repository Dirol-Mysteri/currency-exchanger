package org.example.currency_exchanger.currencies;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchanger.currency.Currency;
import org.example.currency_exchanger.currency.CurrencyResponseDTO;
import org.example.currency_exchanger.currency.CurrencyService;
import org.example.currency_exchanger.currency.exceptions.CurrencyAlreadyExistsException;
import org.example.currency_exchanger.exchangeRates.exceptions.FormFieldNotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.example.currency_exchanger.commons.Utils.sendJsonErrorResponse;
import static org.example.currency_exchanger.commons.Utils.sendJsonResponse;
import static org.example.currency_exchanger.currencies.CurrenciesUtils.checkQueryParamsForCorrectness;

@WebServlet(name = "currenciesServlet", value = "/currencies")
public class CurrenciesController extends HttpServlet {

    private CurrenciesService currenciesService;
    private CurrencyService currencyService;
    private Gson gson;

    @Override
    public void init() {
        currenciesService = new CurrenciesService();
        currencyService = new CurrencyService();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<CurrencyResponseDTO> currencyResponseDTOS = currenciesService.getCurrencies();
            String json = gson.toJson(currencyResponseDTOS);
            sendJsonResponse(response, HttpServletResponse.SC_OK, json);
        } catch (SQLException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String sign = request.getParameter("sign");

        try {
            checkQueryParamsForCorrectness(name, code, sign);
            Currency currency = currencyService.addCurrency(code, name, sign);
            String json = gson.toJson(currency);
            sendJsonResponse(response, HttpServletResponse.SC_CREATED, json);
        } catch (FormFieldNotFoundException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (CurrencyAlreadyExistsException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_CONFLICT, e.getMessage());
        } catch (SQLException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}