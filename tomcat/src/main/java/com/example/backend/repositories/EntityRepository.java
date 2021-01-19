package com.example.backend.repositories;





import com.example.backend.constants.HttpStatus;
import com.example.backend.RESTEntities.ResponseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class EntityRepository {

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("PostgreJPA");
    private EntityManager manager;

    public <T> ResponseEntity<List<T>> findAll(Class<T> type){
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        List<T> result = manager.createQuery("SELECT a from "+type.getSimpleName()+" a", type).getResultList();
        manager.close();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    public <T> ResponseEntity<String> save(T objectToPersist){
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(objectToPersist);
        manager.getTransaction().commit();
        manager.close();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    public <T> ResponseEntity<String> deleteById(Long id, Class<T> type){
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        int affectedRows = manager.createQuery("delete from "+type.getSimpleName()+" a where a.id="+id).executeUpdate();
        manager.getTransaction().commit();
        manager.close();
        if (affectedRows>0) return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    public <T> ResponseEntity<T> findById(Long id, Class<T> type){
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        T objectToFind = manager.find(type, id);
        if (objectToFind!=null) return new ResponseEntity<>(objectToFind, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
