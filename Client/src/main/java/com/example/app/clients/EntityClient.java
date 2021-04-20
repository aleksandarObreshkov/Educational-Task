package com.example.app.clients;

import java.util.List;

public abstract class EntityClient<T> extends StarWarsClient{
    private final Class<T> entityClass;
    public EntityClient(String url, Class<T> entityClass) {
        super(url);
        this.entityClass = entityClass;
    }

    public void delete(Long id){
        template.delete(url+id);
    }

    public void create(T entity){
        template.postForObject(url, entity, entityClass);
    }

    //Since I can't get the T[].class, I think a bit of duplication
    //is better alternative to using reflection
    public abstract List<T> list();
}
