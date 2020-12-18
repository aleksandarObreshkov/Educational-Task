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
        T objectToRemove = manager.find(type, id);
        manager.remove(objectToRemove);
        manager.getTransaction().commit();
        manager.close();
    }

    public <T> T findById(Long id, Class<T> type){
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        return manager.find(type, id);
    }

}
