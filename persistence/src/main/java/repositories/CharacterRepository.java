package repositories;

import model.Character;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CharacterRepository extends EntityRepository{

    @Override
    public <T> List<T> findAll(Class<T> type) {
        // TODO Use a bounded type parameter instead of just T. This can ensure that this method is only called with a class that
        // extends Character and not Integer, for example. It might also make this "if" unnecessary.
        if(type.isInterface()){
            throw new IllegalArgumentException("Method findAllOfType can not be applied to interfaces.");
        }
        String tableName = type.getSuperclass().getSimpleName();
        String characterType = type.getSimpleName();
        return execute(manager ->
                manager.createQuery(
                        // TODO Just use Character.class.getSimpleName() like you've done in the allCharacters method.
                        // You don't need to compute it dynamically.
                        // Also use JPQL query parameters instead of string concatenation. You shouldn't get used to
                        // concatenation, because it might lead to security vulnerabilities.
                        "SELECT a FROM "+tableName+" a WHERE TYPE(a) = "+characterType, type)
                .getResultList());
    }

    // TODO The name of this method differs a lot from the method above, but they do mostly the same thing. Use similar names to indicate this.
    // Also, can't this method just call "findAll(Character.class)". Won't this work?
    public List<Character> allCharacters(){
        return execute(manager ->
            manager.createQuery("SELECT c from "+Character.class.getSimpleName()+" c ", Character.class).getResultList());
    }

}
