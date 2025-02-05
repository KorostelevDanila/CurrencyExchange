package repositories;

import dbManager.DBManager;
import models.CurrencyModel;
import models.ExchangeRateModel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateRepository extends Repository<ExchangeRateModel> {
    CurrenciesRepository currenciesRepository;

    public ExchangeRateRepository(DBManager dbManager, CurrenciesRepository currenciesRepository) {
        this.dbManager = dbManager;
        this.currenciesRepository = currenciesRepository;
    }

    @Override
    public List<ExchangeRateModel> findAll() throws SQLException {
        String mainQuery = "SELECT * FROM ExchangeRates";
        Connection conn = dbManager.getConnection();
        List<ExchangeRateModel> exchangeRates = new ArrayList<>();

        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(mainQuery);
            while (resultSet.next()) {
                ExchangeRateModel exchangeRate = getExchangeRateModel(resultSet);
                exchangeRates.add(exchangeRate);
            }
        } catch (SQLException e) {
            throw e;
        }

        return exchangeRates;
    }

    @Override
    public ExchangeRateModel findById(Long id) throws SQLException {
        return null;
    }

    @Override
    public ExchangeRateModel insert(ExchangeRateModel object) throws SQLException {
        return null;
    }

    private ExchangeRateModel getExchangeRateModel(ResultSet resultSet) throws SQLException {
        int ID = resultSet.getInt("ID");
        long baseCurrencyId = resultSet.getInt("BaseCurrencyId");
        long targetCurrencyId = resultSet.getInt("TargetCurrencyId");
        BigDecimal rate = resultSet.getBigDecimal("Rate");
        CurrencyModel baseCurrency = currenciesRepository.findById(baseCurrencyId);
        CurrencyModel targetCurrency = currenciesRepository.findById(targetCurrencyId);
        return new ExchangeRateModel(ID, baseCurrency, targetCurrency, rate);
    }
}
