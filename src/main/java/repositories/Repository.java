package repositories;

import dbManager.DBManager;

import java.util.List;

public abstract class Repository<T> {
    DBManager dbManager;
    public abstract List<T> findAll();
    public abstract T findById(Long id);
}
