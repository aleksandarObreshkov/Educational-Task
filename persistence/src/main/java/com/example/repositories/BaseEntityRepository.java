package com.example.repositories;
import javax.persistence.*;
import java.util.function.Function;

abstract class BaseEntityRepository {

    protected final EntityManagerFactory factory = Persistence.createEntityManagerFactory("PostgreJPA");

    public <T> T executeInTransaction(Function<EntityManager, T> databaseAction) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            T result = databaseAction.apply(manager);
            manager.getTransaction().commit();
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
            manager.getTransaction().begin();
            return databaseAction.apply(manager);
        }catch (Exception e){
            throw new PersistenceException("Database error: "+e.getMessage(), e);
        }finally {
            manager.close();
        }
    }

    public EntityManager getEntityManager(){
        return factory.createEntityManager();
    }

}
