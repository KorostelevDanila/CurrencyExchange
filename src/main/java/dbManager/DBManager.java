package dbManager;

import java.sql.Connection;

public abstract class DBManager {
    public abstract Connection getConnection();
}
