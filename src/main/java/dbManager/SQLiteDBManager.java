package dbManager;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteDBManager extends DBManager {
    private Connection conn = null;
    private final String connectionString = "jdbc:sqlite:CurrencyExchange.db";
    private static SQLiteDataSource dataSource;

    public SQLiteDBManager() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(connectionString);
    }

    @Override
    public Connection getConnection() {
        try {
            Connection conn = dataSource.getConnection();
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
