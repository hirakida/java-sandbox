package com.example;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

public final class Main {

    public static void main(String[] args) {
        read();
        write();
    }

    private static void read() {
        try (InputStream is = Main.class.getResourceAsStream("/input.csv");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            CSVParser parser = CSVFormat.EXCEL.withIgnoreEmptyLines(true)
                                              .withHeader("id", "name", "age")
                                              .withFirstRecordAsHeader()
                                              .withIgnoreSurroundingSpaces(true)
                                              .parse(br);
            parser.getRecords()
                  .forEach(record -> {
                      System.out.println(record);
                      System.out.println(record.get("id") + ',' + record.get("name") + ',' + record.get("age"));
                  });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void write() {
        try (FileWriter fileWriter = new FileWriter("output.csv")) {
            CSVPrinter printer = CSVFormat.DEFAULT.withHeader("id", "name", "age")
                                                  .print(fileWriter);
            printer.printRecord("1", "output1", "31");
            printer.printRecord("2", "output2", "32");
            printer.printRecords(new String[] { "3", "output3", "33" },
                                 new String[] { "4", "output4", "34" });
            printer.flush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
