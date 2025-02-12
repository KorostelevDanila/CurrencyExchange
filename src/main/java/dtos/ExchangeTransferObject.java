package dtos;

import models.CurrencyModel;

import java.math.BigDecimal;

public class ExchangeTransferObject {
    public CurrencyModel baseCurrency;
    public CurrencyModel targetCurrency;
    public BigDecimal amount;
    public BigDecimal rate;
    public BigDecimal convertedAmount;

    public ExchangeTransferObject(CurrencyModel baseCurrency, CurrencyModel targetCurrency, BigDecimal amount, BigDecimal rate, BigDecimal convertedAmount) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
        this.rate = rate;
        this.convertedAmount = convertedAmount;
    }

    public CurrencyModel getBaseCurrency() {
        return baseCurrency;
    }

    public CurrencyModel getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }
}
