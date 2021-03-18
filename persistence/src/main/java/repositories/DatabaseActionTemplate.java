package repositories;

import javax.persistence.*;
import java.util.function.Function;

// TODO This class can be package-private as it isn't needed outside of this package. Also, I'd prefer a name like
// "AbstractEntityRepository" or "BaseEntityRepository" as those are common naming conventions for classes that hold
// reusable protected methods.
public abstract class DatabaseActionTemplate {

    public <T> T executeInTransaction(Function<EntityManager, T> databaseAction) {
        EntityManager manager = createEntityManager();
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
            manager.close(); // TODO This should be done in the finally clause.
            // TODO "Database transaction failed" is too generic for anyone to figure out what went wrong. You should
            // append the message of the cause to add some details.
            throw new RollbackException("Database transaction failed.", e);
        }
    }

    public <T> T execute(Function<EntityManager, T> databaseAction) {
        EntityManager manager = createEntityManager();
        T result = databaseAction.apply(manager);
        manager.close(); // TODO This should be done in a finally clause in case the database action throws an
                         // exception.
        return result;
    }

    private EntityManager createEntityManager() {
        // TODO Creating an EntityManagerFactory instance is an expensive operation. You should keep in in a static
        // field and reuse it instead of creating a new one every time. EntityManagers on the other hand are cheap and
        // don't need to be reused.
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PostgreJPA");
        return factory.createEntityManager();
    }

}
