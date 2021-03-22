package com.example.app.printing;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private final List<String> headerRow;
    private List<List<String>> rows = new ArrayList<>();

    public Table(List<String> headerRow) {
        this.headerRow = headerRow;
    }

    public List<String> getHeaderRow() {
        return headerRow;
    }

    public List<List<String>> getRows() {
        return rows;
    }

    public void setRows(List<List<String>> rows) {
        this.rows = rows;
    }
}
