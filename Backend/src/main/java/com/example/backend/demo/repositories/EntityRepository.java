package com.example.backend.demo.repositories;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class EntityRepository extends DatabaseActionTemplate {

    public <T> List<T> findAll(Class<T> type){
        return execute(manager -> manager.createQuery("SELECT a from "+type.getSimpleName()+" a", type).getResultList());
    }

    public <T> void save(T objectToPersist){
        executeInTransaction(manager -> {
            manager.persist(objectToPersist);
            return null;
        });
    }

    public <T> boolean deleteById(Long id, Class<T> type){
        return executeInTransaction(manager -> {
            int affectedRows = manager.createQuery("delete from "+type.getSimpleName()+" a where a.id="+id).executeUpdate();
            return affectedRows>0;
        });
    }

    public <T> T findById(Long id, Class<T> type){
        return execute( (manager) -> manager.find(type,id));
    }

}
