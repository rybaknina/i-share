package eu.senla.course.service;

import eu.senla.course.entity.Employee;

public class AccountingService {
    private double currentMonthSalary;

    public AccountingService(double currentMonthSalary) {
        this.currentMonthSalary = currentMonthSalary;
    }

    public double sumCurrentMonthSalary(Employee[] employees){
        for (Employee employee: employees){
            if (employee != null) {
                currentMonthSalary += employee.getSalary();
            }
        }
        return currentMonthSalary;
    }

}
