package repositories;

import javax.persistence.*;
import java.util.function.Function;

public abstract class DatabaseActionTemplate {

    public <T> T executeInTransaction(Function<EntityManager, T> databaseAction) {
        EntityManager manager = createEntityManager();
        try{
            manager.getTransaction().begin();
            T result = databaseAction.apply(manager);
            manager.getTransaction().commit();
            manager.close();
            return result;
        } catch (RollbackException e){
            if (manager.getTransaction().isActive()){
                manager.getTransaction().rollback();
            }
            manager.close();
            throw new RollbackException("Database transaction failed.", e);
        }
    }

    public <T> T execute(Function<EntityManager, T> databaseAction){
        EntityManager manager = createEntityManager();
        T result = databaseAction.apply(manager);
        manager.close();
        return result;
    }

    private EntityManager createEntityManager(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PostgreJPA");
        return factory.createEntityManager();
    }

}
