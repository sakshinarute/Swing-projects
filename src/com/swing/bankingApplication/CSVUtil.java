package com.swing.bankingApplication;

import java.io.*;
import java.util.*;

public class CSVUtil {

    public static void writeToCSV(String fileName, List<String[]> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String[] row : data) {
                bw.write(String.join(",", escapeSpecialChars(row)));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> readFromCSV(String fileName) {
        List<String[]> data = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) return data;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static String[] escapeSpecialChars(String[] row) {
        String[] escaped = new String[row.length];
        for (int i = 0; i < row.length; i++) {
            if (row[i].contains(",") || row[i].contains("\"")) {
                escaped[i] = "\"" + row[i].replace("\"", "\"\"") + "\"";
            } else {
                escaped[i] = row[i];
            }
        }
        return escaped;
    }
}
