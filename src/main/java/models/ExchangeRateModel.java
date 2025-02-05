package models;

import java.math.BigDecimal;

public class ExchangeRateModel {
    private int ID;
    private CurrencyModel baseCurrency;
    private CurrencyModel targetCurrency;
    private BigDecimal rate;

    public ExchangeRateModel(int ID, CurrencyModel baseCurrency, CurrencyModel targetCurrency, BigDecimal rate) {
        this.ID = ID;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public ExchangeRateModel(CurrencyModel baseCurrency, CurrencyModel targetCurrency, BigDecimal rate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public int getID() {
        return ID;
    }

    public CurrencyModel getBaseCurrency() {
        return baseCurrency;
    }

    public CurrencyModel getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
