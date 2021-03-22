package com.example.repositories;

import javax.persistence.*;
import java.util.function.Function;

abstract class BaseEntityRepository {

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("PostgreJPA");

    public <T> T executeInTransaction(Function<EntityManager, T> databaseAction) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            T result = databaseAction.apply(manager);
            manager.getTransaction().commit();
            manager.close();
            return result;
        } catch (RollbackException e){
            if (manager.getTransaction().isActive()){
                manager.getTransaction().rollback();
            }
            throw new RollbackException("Database transaction failed: "+e.getMessage(), e);
        }finally {
            manager.close();
        }
    }

    public <T> T execute(Function<EntityManager, T> databaseAction) {
        EntityManager manager = factory.createEntityManager();
        try{
            return databaseAction.apply(manager);
        }catch (Exception e){
            throw new RollbackException("Database error: "+e.getMessage(), e);
            //TODO: Add an adequate exception and message
        }finally {
            manager.close();
        }
    }

}