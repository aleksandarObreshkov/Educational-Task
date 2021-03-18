package repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// TODO "T" is declared in all methods. You could just add it to the class definition, so that you don't need to
// declare it over and over again.
public class EntityRepository extends DatabaseActionTemplate {

    public <T> List<T> findAll(Class<T> type) {
        return execute(
                // TODO
                manager -> manager.createQuery("SELECT a FROM " + type.getSimpleName() + " a", type).getResultList());
    }

    public <T> void save(T objectToPersist) {
        executeInTransaction(manager -> {
            manager.persist(objectToPersist);
            return null;
        });
    }

    public <T> boolean deleteById(Long id, Class<T> type) {
        return executeInTransaction(manager -> {
            // TODO
            int affectedRows = manager.createQuery("DELETE FROM " + type.getSimpleName() + " a WHERE a.id=" + id)
                    .executeUpdate();
            return affectedRows > 0;
        });
    }

    public <T> T findById(Long id, Class<T> type) {
        return execute(manager -> manager.find(type, id));
    }

}
