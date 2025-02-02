package dbManager;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteDBManager extends DBManager {
    private Connection conn = null;
    //TODO: Find another solution for connection string (both the path and where to store it)
    private final String connectionString = "jdbc:sqlite:C:\\Users\\Данила\\Desktop\\Projects\\Java\\CurrencyExchange\\src\\main\\resources\\CurrencyExchange.db";
    private static SQLiteDataSource dataSource;

    public SQLiteDBManager() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(connectionString);
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Connection conn = dataSource.getConnection();
            return conn;
        } catch (SQLException e) {
            throw e;
        }
    }
}
