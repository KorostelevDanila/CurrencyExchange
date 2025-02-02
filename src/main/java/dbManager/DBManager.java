package dbManager;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DBManager {
    public abstract Connection getConnection() throws SQLException;
}
