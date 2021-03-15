package repositories;

import model.Character;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CharacterRepository extends EntityRepository{

    @Override
    public <T> List<T> findAll(Class<T> type) {
        if(type.isInterface()){
            throw new IllegalArgumentException("Method findAllOfType can not be applied to interfaces.");
        }
        String tableName = type.getSuperclass().getSimpleName();
        String characterType = type.getSimpleName();
        return execute(manager ->
                manager.createQuery(
                        "SELECT a FROM "+tableName+" a WHERE TYPE(a) = "+characterType, type)
                .getResultList());
    }

    public List<Character> allCharacters(){
        return execute(manager ->
            manager.createQuery("SELECT c from "+Character.class.getSimpleName()+" c ", Character.class).getResultList());
    }

}
