package org.example.currency_exchanger.exchangeRates;

import org.example.currency_exchanger.exchangeRate.ExchangeRate;
import org.example.currency_exchanger.exchangeRate.ExchangeRateMapper;
import org.example.currency_exchanger.exchangeRate.ExchangeRateResponseDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesService {
    private final ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();
    private final ExchangeRatesRepository exchangeRatesRepository = new ExchangeRatesRepository();

    public List<ExchangeRateResponseDTO> getExchangeRates() throws SQLException {
        List<ExchangeRateResponseDTO> exchangeRateResponseDTOS = new ArrayList<>();
        List<ExchangeRate> exchangeRates = exchangeRatesRepository.getExchangeRates();

        for (ExchangeRate exchangeRate : exchangeRates) {
            exchangeRateResponseDTOS.add(exchangeRateMapper.exchangeRateToResponseDTO(exchangeRate));
        }

        return exchangeRateResponseDTOS;
    }
}
