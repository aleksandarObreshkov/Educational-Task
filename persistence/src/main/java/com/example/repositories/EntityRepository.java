package com.example.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntityRepository<T> extends BaseEntityRepository {

    public List<T> findAll(Class<T> type) {
        return execute(
                // TODO
                manager -> manager.createQuery("SELECT a FROM " + type.getSimpleName() + " a", type).getResultList());
    }

    public void save(T objectToPersist) {
        executeInTransaction(manager -> {
            manager.persist(objectToPersist);
            return null;
        });
    }

    public boolean deleteById(Long id, Class<T> type) {
        return executeInTransaction(manager -> {
            // TODO
            int affectedRows = manager.createQuery("DELETE FROM " + type.getSimpleName() + " a WHERE a.id=" + id)
                    .executeUpdate();
            return affectedRows > 0;
        });
    }

    public T findById(Long id, Class<T> type) {
        return execute(manager -> manager.find(type, id));
    }

}
