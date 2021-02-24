package repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.function.Function;

public abstract class DatabaseActionTemplate {

    public <T> T executeInTransaction(Function<EntityManager, T> databaseAction){
        EntityManager manager = createEntityManager();
        manager.getTransaction().begin();
        T result = databaseAction.apply(manager);
        manager.getTransaction().commit();
        manager.close();
        return result;
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
