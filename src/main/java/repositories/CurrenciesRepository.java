package repositories;

import dbManager.DBManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.CurrencyModel;

public class CurrenciesRepository extends Repository<CurrencyModel> {

    public CurrenciesRepository(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public List<CurrencyModel> findAll() {
        String query = "SELECT * FROM Currencies";

        List<CurrencyModel> currencies = new ArrayList<>();

        Connection conn = dbManager.getConnection();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                CurrencyModel currencyModel = getCurrencyModel(resultSet);
                currencies.add(currencyModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return currencies;
    }

    @Override
    public CurrencyModel findById(Long id) {
        String query = "SELECT * FROM Currencies WHERE id = " + id;
        
        Connection conn = dbManager.getConnection();
        CurrencyModel currency = null;
        
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                currency = getCurrencyModel(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return currency;
    }

    private static CurrencyModel getCurrencyModel(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("ID");
        String code = resultSet.getString("Code");
        String fullName = resultSet.getString("FullName");
        String sign = resultSet.getString("Sign");
        CurrencyModel currencyModel = new CurrencyModel(id, code, fullName, sign);
        return currencyModel;
    }
}
