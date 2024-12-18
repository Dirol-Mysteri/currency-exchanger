package org.example.currency_exchanger.currency;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchanger.currency.exceptions.CurrencyCodeWasntProvided;
import org.example.currency_exchanger.currency.exceptions.ProvidedCurrencyDoesntExistException;

import java.io.IOException;
import java.sql.SQLException;

import static org.example.currency_exchanger.commons.Utils.sendJsonErrorResponse;
import static org.example.currency_exchanger.commons.Utils.sendJsonResponse;
import static org.example.currency_exchanger.currency.CurrencyUtils.extractCurrencyCodeFromRequest;

@WebServlet(name = "currencyServlet", value = "/currency/*")
public class CurrencyController extends HttpServlet {
    private CurrencyService currencyService;
    private Gson gson;

    @Override
    public void init() {
        currencyService = new CurrencyService();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String userInput = extractCurrencyCodeFromRequest(request);
            Currency currency = currencyService.getCurrencyByCode(userInput);
            String json = gson.toJson(currency);
            sendJsonResponse(response, HttpServletResponse.SC_OK, json);
        } catch (CurrencyCodeWasntProvided e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (ProvidedCurrencyDoesntExistException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (SQLException e) {
            sendJsonErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}