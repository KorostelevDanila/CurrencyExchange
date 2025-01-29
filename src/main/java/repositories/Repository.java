package repositories;

import java.util.List;

public abstract class Repository<T> {
    public abstract List<T> findAll();
    public abstract T findById(Long id);
}
