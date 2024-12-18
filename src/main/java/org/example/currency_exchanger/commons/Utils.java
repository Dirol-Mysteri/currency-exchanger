package org.example.currency_exchanger.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static String getDynamicParameterFromRequest(HttpServletRequest request) {
        // Извлечение динамического параметра из URL
        String pathInfo = request.getPathInfo(); // Получаем часть пути после "/"
        return pathInfo != null && pathInfo.length() > 1 ? pathInfo.substring(1) : null;
    }

    public static boolean isCurrencyCodeCorrect(String currencyCode) {
        if (currencyCode == null || currencyCode.length() != 3) {
            return false;
        }
        return true;
    }

    public static void sendJsonResponse(HttpServletResponse response, int status, String responseJson) throws IOException {
        response.setStatus(status);
        try (PrintWriter out = response.getWriter()) {
            out.print(responseJson);
        }
    }

    public static void sendJsonErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        try (PrintWriter out = response.getWriter()) {
            out.print("{\"message\":\"" + message + "\"}");
        }
    }

    public static Map<String, String> extractBodyFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder body = new StringBuilder();
        String line;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        }

        // Получаем строку запроса
        String requestBody = body.toString();

        // Разделяем параметры на пары ключ-значение
        String[] pairs = requestBody.split("&");

        Map<String, String> parameters = new HashMap<>();

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = URLDecoder.decode(keyValue[0], "UTF-8");
                String value = URLDecoder.decode(keyValue[1], "UTF-8");
                parameters.put(key, value);
            }
        }

        return parameters;
    }

    public static String convertUnicodeSignToSymbol(String sign) {
        if (sign == null || sign.isEmpty()) {
            throw new IllegalArgumentException("Sign cannot be null or empty");
        }

        if (sign.startsWith("U+")) {
            try {
                int codePoint = Integer.parseInt(sign.substring(2), 16);
                return new String(Character.toChars(codePoint));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid Unicode format: " + sign, e);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid Unicode format: " + sign, e);
            }
        }

        return sign;
    }

}
