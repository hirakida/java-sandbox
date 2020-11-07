package com.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public final class Main {

    public static void main(String[] args) {
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet1");

        IntStream.rangeClosed(0, 4)
                 .forEach(i -> {
                     Row row = sheet.createRow(i);

                     Cell cell0 = row.createCell(0);
                     cell0.setCellValue(i);

                     Cell cell1 = row.createCell(1);
                     cell1.setCellValue("name" + i);
                 });

        try (OutputStream os = new FileOutputStream(new File("output.xlsx"))) {
            workbook.write(os);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
