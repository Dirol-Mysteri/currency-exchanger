package org.example.currency_exchanger.currencies;

import org.example.currency_exchanger.currency.CurrencyMapper;
import org.example.currency_exchanger.currency.CurrencyResponseDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrenciesService {
    private final CurrencyMapper currenciesMapper = new CurrencyMapper();
    private final CurrenciesRepository currenciesRepository = new CurrenciesRepository();

    public List<CurrencyResponseDTO> getCurrencies() throws SQLException {
        List<CurrencyResponseDTO> currencyResponseDTOS = new ArrayList<>();

        currenciesRepository.getCurrencies().forEach(currency -> {
            currencyResponseDTOS.add(currenciesMapper.currencyToResponseDTO(currency));
        });

        return currencyResponseDTOS;
    }
}
