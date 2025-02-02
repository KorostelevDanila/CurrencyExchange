package repositories;

import dbManager.DBManager;

import java.sql.SQLException;
import java.util.List;

public abstract class Repository<T> {
    DBManager dbManager;
    public abstract List<T> findAll() throws SQLException;
    public abstract T findById(Long id) throws SQLException;
}
