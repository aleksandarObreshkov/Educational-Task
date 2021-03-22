package com.example.app.printing;

import java.util.ArrayList;
import java.util.List;

public class TablePrinter {

    public static void printDataTable(Table data) {
        if (data.getRows().isEmpty()){
            System.out.println("No data to show.");
            return;
        }
        List<Integer> columnLengths = setUpColumnLengths(data);
        String formatString = prepareFormatString(columnLengths);
        String line = prepareSeparatorLine(columnLengths);
        printTable(formatString, line, data);
    }

    private static List<Integer> setUpColumnLengths(Table array) {
        List<Integer> columnLengths = getHeaderRowColumnSize(array.getHeaderRow());
        for (List<String> row : array.getRows()) {
            for (int i = 0; i < row.size(); i++) {
                if (columnLengths.get(i) < row.get(i).length()) {
                    columnLengths.set(i, row.get(i).length());
                }
            }
        }
        return columnLengths;
    }

    private static List<Integer> getHeaderRowColumnSize(List<String> headerRow){
        List<Integer> headerRowColumnSizes = new ArrayList<>();
        for (String column : headerRow){
            headerRowColumnSizes.add(column.length());
        }
        return headerRowColumnSizes;
    }

    private static String prepareFormatString(List<Integer> columnLengths) {
        final StringBuilder formatString = new StringBuilder("");
        columnLengths.forEach(value -> formatString.append("| %").append("-").append(value).append("s "));
        formatString.append("|\n");
        return formatString.toString();
    }

    private static String prepareSeparatorLine(List<Integer> columnLengths) {
        StringBuilder result = new StringBuilder();
        for (Integer columnSize : columnLengths) {
            result.append("+-");
            result.append("-".repeat(columnSize + 1));
        }
        result.append("+\n");
        return result.toString();
    }

    private static void printTable(String formatString, String line, Table data) {
        System.out.print(line);
        System.out.printf(formatString, data.getHeaderRow());
        System.out.print(line);
        for (int i = 1; i < data.getRows().size(); i++) {
            System.out.printf(formatString, data.getRows().get(i));
        }
        System.out.println(line);
        System.out.println();
    }
}
