package com.example.app.printing;

import java.util.HashMap;
import java.util.Map;

public class TablePrinter {

    // TODO Handle the case where the array is empty.
    public static void printDataTable(String[][] data) {
        // TODO The keys of this map are never used.
        Map<Integer, Integer> columnLengths = setupColumnLengths(data);
        String formatString = prepareFormatString(columnLengths);
        String line = prepareLine(columnLengths);
        printTable(formatString, line, data);
    }

    private static Map<Integer, Integer> setupColumnLengths(String[][] array) {
        Map<Integer, Integer> columnLengths = new HashMap<>();
        for (String[] row : array) {
            for (int i = 0; i < row.length; i++) {
                columnLengths.putIfAbsent(i, 0);
                if (columnLengths.get(i) < row[i].length()) {
                    columnLengths.put(i, row[i].length());
                }
            }
        }
        return columnLengths;
    }

    private static String prepareFormatString(Map<Integer, Integer> columnLengths) {
        final StringBuilder formatString = new StringBuilder("");
        columnLengths.forEach((key, value) -> formatString.append("| %").append("-").append(value).append("s "));
        formatString.append("|\n");
        return formatString.toString();
    }

    // TODO The name of this method doesn't really indicate that this is a separator line. I first thought that this was
    // going to be a line with values.
    private static String prepareLine(Map<Integer, Integer> columnLengths) {
        StringBuilder result = new StringBuilder();
        for (Integer columnSize : columnLengths.values()) {
            result.append("+-");
            result.append("-".repeat(columnSize + 1));
        }
        result.append("+\n");
        return result.toString();
    }

    private static void printTable(String formatString, String line, String[][] data) {
        System.out.print(line);
        System.out.printf(formatString, data[0]);
        System.out.print(line);
        for (int i = 1; i < data.length; i++) {
            System.out.printf(formatString, data[i]);
        }
        System.out.println(line);
        System.out.println();
    }

}
