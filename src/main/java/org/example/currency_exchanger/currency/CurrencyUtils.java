package org.example.currency_exchanger.currency;

import jakarta.servlet.http.HttpServletRequest;
import org.example.currency_exchanger.currency.exceptions.CurrencyCodeWasntProvided;

public class CurrencyUtils {

    public static String extractCurrencyCodeFromRequest(HttpServletRequest request) {
        // Извлечение динамического параметра из URL
        String pathInfo = request.getPathInfo(); // Получаем часть пути после "/currency"

        if (pathInfo == null || pathInfo.isEmpty()) {
            throw new CurrencyCodeWasntProvided();
        }

        return pathInfo.substring(1);
    }

}
