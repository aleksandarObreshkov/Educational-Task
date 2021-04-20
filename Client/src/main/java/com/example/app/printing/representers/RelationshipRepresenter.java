package com.example.app.printing.representers;

import java.util.List;

public abstract class RelationshipRepresenter<T, R> extends EntityRepresenter<T>{

    protected abstract List<String> getNames(List<R> entities);
}
