package com.example.backend.demo.repositories;

import model.Character;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Repository
public class EntityRepository {

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("PostgreJPA");
    private EntityManager manager;


    public <T> List<T> findAll(Class<T> type){
        //I'm not entirely familiar with the `EntityManager` class but
        // why are you creating and closing it on every method?
        // is it equivalent to opening and closing a db connection?
        // if so, won't that take up a lot more resources for creating it every time than using 1 connection
        // and opening and operating with transactions?
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        List<T> result = manager.createQuery("SELECT a from "+type.getSimpleName()+" a", type).getResultList();
        manager.close();
        return  result;
    }

    public <T> void save(T objectToPersist){
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(objectToPersist);
        manager.getTransaction().commit();
        manager.close();
    }

    public <T> void deleteById(Long id, Class<T> type){
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        //wouldn't it be more efficient if we just executed 1 DELETE sql statement
        // rather than first executing a select and then a delete?
        T objectToRemove = manager.find(type, id);
        manager.remove(objectToRemove);
        manager.getTransaction().commit();
        manager.close();
    }

    public <T> T findById(Long id, Class<T> type){
        manager = factory.createEntityManager();
        //FIXME uncommitted transaction
        manager.getTransaction().begin();
        return manager.find(type, id);
    }

}
