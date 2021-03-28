package com.example.app.printing.printers;

import com.example.app.printing.representers.EntityRepresenter;
import com.example.app.printing.Table;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityPrinter<T, R extends EntityRepresenter<T>> {

    private final R representer;

    public EntityPrinter(R representer) {
        this.representer = representer;
    }

    public void printTable(List<T> data){
        Table table = createTable(data);
        TablePrinter.printDataTable(table);
    }

    public Table createTable(List<T> data){
        Table table = new Table(representer.getHeaderRow());
        List<List<String>> rows = new ArrayList<>();
        for (T entity: data){
            rows.add(representer.getRow(entity));
        }
        table.setRows(rows);
        return table;
    }
}
