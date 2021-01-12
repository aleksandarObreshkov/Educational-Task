package com.example.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class EntityRepository {

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("PostgreJPA");
    private EntityManager manager;

    public String getTest(){return "test string from repo class";}

    public <T> ResponseEntity<List<T>> findAll(Class<T> type){
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        List<T> result = manager.createQuery("SELECT a from "+type.getSimpleName()+" a", type).getResultList();
        manager.close();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public <T> ResponseEntity<String> save(T objectToPersist){
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(objectToPersist);
        manager.getTransaction().commit();
        manager.close();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public <T> ResponseEntity<String> deleteById(Long id, Class<T> type){
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        int affectedRows = manager.createQuery("delete from "+type.getSimpleName()+" a where a.id="+id).executeUpdate();
        manager.getTransaction().commit();
        manager.close();
        if (affectedRows>0) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    public <T> ResponseEntity<T> findById(Long id, Class<T> type){
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        T objectToFind = manager.find(type, id);
        if (objectToFind!=null) return ResponseEntity.status(HttpStatus.OK).body(objectToFind);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
