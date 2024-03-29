package com.example.app.printing.representers;

import java.util.List;

public abstract class EntityRepresenter<T> {
    public abstract List<String> getHeaderRow();
    public abstract List<String> getRow(T entity);

    protected String createStringFromList(List<String> dataList){
        return String.join(", ", dataList);
    }
}
