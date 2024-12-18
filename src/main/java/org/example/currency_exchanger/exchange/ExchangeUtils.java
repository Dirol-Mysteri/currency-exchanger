package org.example.currency_exchanger.exchange;

import org.example.currency_exchanger.currency.exceptions.CurrencyCodeWrongException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.example.currency_exchanger.commons.Utils.isCurrencyCodeCorrect;

public class ExchangeUtils {

    public static void checkExchangeParams(String from, String to, String amount) {
        if (!isCurrencyCodeCorrect(from)) {
            throw new CurrencyCodeWrongException(from);
        }
        if (!isCurrencyCodeCorrect(to)) {
            throw new CurrencyCodeWrongException(to);
        }
        try {
            BigDecimal bigDecimalRate = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid amount value. Please provide a valid amount value.");
        }
    }

    public static BigDecimal convertAmount(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate);
    }

    public static BigDecimal reverseConvertAmount(BigDecimal amount, BigDecimal rate) {
        return amount.divide(rate, RoundingMode.UP);
    }
}
