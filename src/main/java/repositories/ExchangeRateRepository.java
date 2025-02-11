package repositories;

import dbManager.DBManager;
import exceptions.NotFoundInDatabaseException;
import models.CurrencyModel;
import models.ExchangeRateModel;

import javax.swing.plaf.nimbus.State;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        StringBuilder query = new StringBuilder("SELECT * FROM ExchangeRates WHERE ID = " + id);

        Connection conn = dbManager.getConnection();

        ExchangeRateModel exchangeRateModel = null;

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                exchangeRateModel = getExchangeRateModel(resultSet);
            }
        } catch (SQLException e) {
            throw e;
        }

        if (exchangeRateModel == null) {
            throw new NotFoundInDatabaseException("Валютная пара обмена не найдена");
        }
        return exchangeRateModel;
    }

    public ExchangeRateModel findByExchangePair(Map<String, String> exchangePair) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT * FROM ExchangeRates WHERE");

        Connection conn = dbManager.getConnection();

        ExchangeRateModel exchangeRateModel = null;

        try {
            int baseCurrencyId = currenciesRepository.findByCode(exchangePair.get("from")).getID();
            int targetCurrencyId = currenciesRepository.findByCode(exchangePair.get("to")).getID();

            query.append(" BaseCurrencyId = ").append(baseCurrencyId);
            query.append(" AND TargetCurrencyId = ").append(targetCurrencyId);

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                exchangeRateModel = getExchangeRateModel(resultSet);
            }
        } catch (SQLException e) {
            throw e;
        } catch (NotFoundInDatabaseException e) {
            throw new NotFoundInDatabaseException("Одной из валют в паре не существует в базе данных");
        }

        if (exchangeRateModel == null) {
            throw new NotFoundInDatabaseException("Валютная пара обмена не найдена");
        }
        return exchangeRateModel;
    }

    @Override
    public ExchangeRateModel insert(ExchangeRateModel object) throws SQLException {
        String query = "INSERT INTO ExchangeRates(BaseCurrencyId, TargetCurrencyId, Rate) VALUES(" + object.getBaseCurrency().getID()
                + ", " + object.getTargetCurrency().getID()
                + ", " + object.getRate() + ")";

        Connection conn = dbManager.getConnection();
        ExchangeRateModel exchangeRate = null;

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(query);
            long key = statement.getGeneratedKeys().getInt(1);
            exchangeRate = findById(key);
        } catch (SQLException e) {
            throw e;
        }

        return exchangeRate;
    }

    @Override
    public ExchangeRateModel update(ExchangeRateModel object) throws SQLException {
        String query = "UPDATE ExchangeRates SET Rate = " + object.getRate()
        + " WHERE BaseCurrencyId = " + object.getBaseCurrency().getID()
        + " AND TargetCurrencyId = " + object.getTargetCurrency().getID();

        Connection conn = dbManager.getConnection();
        ExchangeRateModel exchangeRate = null;

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(query);
            Map<String, String> exchangePair = new HashMap<>();
            exchangePair.put("from", object.getBaseCurrency().getCode());
            exchangePair.put("to", object.getTargetCurrency().getCode());
            exchangeRate = findByExchangePair(exchangePair);
        } catch (SQLException e) {
            throw e;
        }

        return exchangeRate;
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
