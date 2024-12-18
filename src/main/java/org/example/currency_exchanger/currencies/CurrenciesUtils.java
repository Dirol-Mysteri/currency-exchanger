package org.example.currency_exchanger.currencies;

import org.example.currency_exchanger.exchangeRates.exceptions.FormFieldNotFoundException;

public class CurrenciesUtils {

    public static void checkQueryParamsForCorrectness(String name, String code, String sign) {

        if ((name == null || name.isEmpty())) {
            throw new FormFieldNotFoundException(name);
        }

        if ((code == null || code.isEmpty())) {
            throw new FormFieldNotFoundException(code);
        }

        if ((sign == null || sign.isEmpty())) {
            throw new FormFieldNotFoundException(sign);
        }
    }
}
