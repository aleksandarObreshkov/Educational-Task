package com.example.backend.demo.repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.function.Function;

public interface DatabaseActionTemplate {

    default <T> T initiateTransaction(Function<EntityManager, T> databaseAction){
        EntityManager manager = instantiateEntityManager();
        T result = databaseAction.apply(manager);
        closeEntityManager(manager);
        return result;
    }

    default EntityManager instantiateEntityManager(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PostgreJPA");
        EntityManager manager  = factory.createEntityManager();
        manager.getTransaction().begin();
        return manager;
    }

    default void closeEntityManager(EntityManager manager){
        manager.close();
    }

}
