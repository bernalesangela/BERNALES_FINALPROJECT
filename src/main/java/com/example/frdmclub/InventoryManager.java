package com.example.frdmclub;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class InventoryManager {

    public List<String[]> loadDataFromFile(String fileName) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {

                data.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void editDataInFile(String id, String newData, String path) {


        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            int lineIndex = 0;
            while ((line = reader.readLine()) != null) {
                String id_temp = String.valueOf(line.charAt(0));
                if (id_temp.equals(id)) {
                    System.out.println(id_temp);

                    replaceLineInFile(path, lineIndex, newData, id_temp);
                    break;
                }
                lineIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<String> readFileContent() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/anfb/Downloads/Product_Intenvory_Management_System-main/src/main/resources/com/example/frdmclub/inventory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void replaceLineInFile(String filePath, int lineNumber, String newLine, String id) {
        try {

            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();


            if (lineNumber >= 0 && lineNumber < lines.size()) {
                lines.set(lineNumber, id + ',' + newLine);
            } else {
                System.out.println("Invalid line number");
                return;
            }


            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
            writer.close();

            System.out.println("Line replaced successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int findLineIndex(String path, String id) {
        int lineIndex = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String id_temp = String.valueOf(line.charAt(0));
                if (id_temp.equals(id)) {
                    System.out.println(id_temp);
                    break;
                }
                lineIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineIndex;
    }


    public void deleteLineInFile(String filePath, int lineNumber) {
        try {

            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();


            if (lineNumber >= 0 && lineNumber < lines.size()) {
                lines.remove(lineNumber);
            } else {
                System.out.println("Invalid line number");
                return;
            }


            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
            writer.close();

            System.out.println("Line deleted successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}