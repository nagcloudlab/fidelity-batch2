package com.example;

import com.example.model.Employee;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Example7 {

    public static void main(String[] args) {

        String filePath = "report.xlsx";
        List<Employee> employeeList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Read first sheet

            Iterator<Row> rowIterator = sheet.iterator();

            // Skip header row
//            if (rowIterator.hasNext()) {
//                rowIterator.next();
//            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Employee employee = new Employee();
                // Column 0 -> Name
                Cell nameCell = row.getCell(0);
                if (nameCell != null) {
                    employee.setName(nameCell.getStringCellValue());
                }
                // Column 1 -> Department
                Cell deptCell = row.getCell(1);
                if (deptCell != null) {
                    employee.setDepartment(deptCell.getStringCellValue());
                }

                // Column 2 -> Salary
                Cell salaryCell = row.getCell(2);
                if (salaryCell != null && salaryCell.getCellType() == CellType.NUMERIC) {
                    employee.setSalary(salaryCell.getNumericCellValue());
                }
                employeeList.add(employee);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print all employees
        employeeList.forEach(System.out::println);
    }
}