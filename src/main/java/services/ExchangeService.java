package services;

import dbManager.SQLiteDBManager;
import dtos.ExchangeTransferObject;
import exceptions.NotFoundInDatabaseException;
import models.CurrencyModel;
import models.ExchangeRateModel;
import repositories.CurrenciesRepository;
import repositories.ExchangeRateRepository;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ExchangeService {
    ExchangeRateRepository exchangeRateRepository;
    CurrenciesRepository currenciesRepository;

    public ExchangeService() throws ClassNotFoundException {
        this.currenciesRepository = new CurrenciesRepository(new SQLiteDBManager());
        this.exchangeRateRepository = new ExchangeRateRepository(new SQLiteDBManager(), this.currenciesRepository);

    }

    // TODO implement method
    public ExchangeTransferObject exchange(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) throws SQLException {

        // TODO get exchange rate from database

        Map<String, String> exchangePair = new HashMap<>();
        ExchangeRateModel exchangeRateInfo;
        CurrencyModel baseCurrency, targetCurrency;
        exchangePair.put("from", baseCurrencyCode);
        exchangePair.put("to", targetCurrencyCode);
        ExchangeTransferObject exchangeTransferObject = null;


        try {
            baseCurrency = currenciesRepository.findByCode(baseCurrencyCode);
            targetCurrency = currenciesRepository.findByCode(targetCurrencyCode);
        } catch (NotFoundInDatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw e;
        }

        try {
            exchangeRateInfo = exchangeRateRepository.findByExchangePair(exchangePair);
            BigDecimal convertedAmount = amount.multiply(exchangeRateInfo.getRate());
            exchangeTransferObject = new ExchangeTransferObject(baseCurrency, targetCurrency, amount, exchangeRateInfo.getRate(), convertedAmount);
        } catch (NotFoundInDatabaseException e) {
            try {
                exchangePair.put("from", targetCurrencyCode);
                exchangePair.put("to", baseCurrencyCode);
                exchangeRateInfo = exchangeRateRepository.findByExchangePair(exchangePair);
                BigDecimal convertedAmount = amount.divide(exchangeRateInfo.getRate(), MathContext.DECIMAL128);
                exchangeTransferObject = new ExchangeTransferObject(baseCurrency, targetCurrency, amount, exchangeRateInfo.getRate(), convertedAmount);
            } catch (NotFoundInDatabaseException e1) {
                try {
                    exchangePair.put("from", "USD");
                    exchangePair.put("to", baseCurrencyCode);
                    ExchangeRateModel fromUSDToBase = exchangeRateRepository.findByExchangePair(exchangePair);

                    exchangePair.put("to", targetCurrencyCode);
                    ExchangeRateModel fromUSDToTarget = exchangeRateRepository.findByExchangePair(exchangePair);

                    BigDecimal newExchangeRate = fromUSDToTarget.getRate().divide(fromUSDToBase.getRate(), MathContext.DECIMAL128);
                    BigDecimal convertedAmount = amount.multiply(newExchangeRate);
                    exchangeTransferObject = new ExchangeTransferObject(baseCurrency, targetCurrency, amount, newExchangeRate, convertedAmount);
                } catch (NotFoundInDatabaseException e2) {
                    throw e;
                }
            }
            //throw e;
        } catch (SQLException e) {
            throw e;
        }

        return exchangeTransferObject;
    }
}
