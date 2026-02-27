package com.example;

import java.util.Map;
import java.util.Set;

public class Exercise {

    public static void main(String[] args) {

        // Input..
        String[] csvReport = {
                "A,IT,1000",
                "B,HR,2000",
                "C,IT,3000",
                "D,SALE,4000",
                "E,IT,5000",
                "F,MARKETING,6000",
        };
        // Output..
        // IT: 9000
        // HR: 2000
        // SALE: 4000
        // MARKETING: 6000

        Map<String, Double> departmentSalaries = new java.util.HashMap<>();
        for (String line : csvReport) {
            String[] parts = line.split(",");
            String department = parts[1];
            double salary = Double.parseDouble(parts[2]);
            double existingDepSalary = departmentSalaries.getOrDefault(department, 0.0);
            departmentSalaries.put(department, existingDepSalary + salary);
        }

        System.out.println("Department Salaries:");
        Set<String> keys = departmentSalaries.keySet();
        for (String key : keys) {
            System.out.println(key + ": " + departmentSalaries.get(key));
        }


    }
}
