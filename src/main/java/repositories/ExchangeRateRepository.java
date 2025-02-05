package repositories;

import dbManager.DBManager;
import models.CurrencyModel;
import models.ExchangeRateModel;

import java.sql.SQLException;
import java.util.List;

public class ExchangeRateRepository extends Repository<ExchangeRateModel> {
    CurrenciesRepository currenciesRepository;

    public ExchangeRateRepository(DBManager dbManager, CurrenciesRepository currenciesRepository) {
        this.dbManager = dbManager;
        this.currenciesRepository = currenciesRepository;
    }

    @Override
    public List<ExchangeRateModel> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public ExchangeRateModel findById(Long id) throws SQLException {
        return null;
    }

    @Override
    public ExchangeRateModel insert(ExchangeRateModel object) throws SQLException {
        return null;
    }
}
