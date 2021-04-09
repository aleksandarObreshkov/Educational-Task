package com.example.repositories;
import java.util.List;

public abstract class EntityRepository<T> extends BaseEntityRepository {

    protected Class<T> type;

    public EntityRepository(Class<T> type) {
        this.type = type;
    }

    public List<T> findAll() {
        return execute(
                manager -> manager.createQuery("SELECT a FROM " + type.getSimpleName() + " a", type)
                        .getResultList());
    }

    public void save(T objectToPersist) {
        executeInTransaction(manager -> {
            manager.merge(objectToPersist);
            return null;
        });
    }

    public boolean deleteById(Long id) {
        return executeInTransaction(manager -> {
            int affectedRows = manager.createQuery("DELETE FROM " + type.getSimpleName() + " a WHERE a.id="+id)
                    .executeUpdate();
            return affectedRows > 0;
        });
    }

    public T findById(Long id) {
        return execute(manager -> manager.find(type, id));
    }

}
