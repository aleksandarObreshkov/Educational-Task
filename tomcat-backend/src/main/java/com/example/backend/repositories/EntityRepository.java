package com.example.backend.repositories;

import java.util.List;

public class EntityRepository implements DatabaseActionTemplate {

    public <T> List<T> findAll(Class<T> type){
        return initiateTransaction(manager -> manager.createQuery("SELECT a from "+type.getSimpleName()+" a", type).getResultList());
    }

    public <T> void save(T objectToPersist){
        initiateTransaction(manager -> {
            manager.persist(objectToPersist);
            manager.getTransaction().commit();
            return null;
        });
    }

    public <T> boolean deleteById(Long id, Class<T> type){
        return initiateTransaction(manager -> {
            int affectedRows = manager.createQuery("delete from "+type.getSimpleName()+" a where a.id="+id).executeUpdate();
            manager.getTransaction().commit();
            return affectedRows>0;
        });
    }

    public <T> T findById(Long id, Class<T> type){
        return initiateTransaction( (manager) -> manager.find(type,id));
    }

}
