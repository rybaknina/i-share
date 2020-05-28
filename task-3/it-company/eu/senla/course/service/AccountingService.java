package eu.senla.course.service;

import eu.senla.course.entity.Employee;

public class AccountingService {
    private double currentMonthSalary;

    public AccountingService() {
        this.currentMonthSalary = 0;
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
