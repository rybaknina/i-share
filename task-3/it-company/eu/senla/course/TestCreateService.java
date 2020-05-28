package eu.senla.course;

import eu.senla.course.entity.Company;
import eu.senla.course.entity.Employee;
import eu.senla.course.entity.Developer;
import eu.senla.course.entity.TeamLead;
import eu.senla.course.service.AccountingService;

/**
 * @author Nina Rybak
 * Программа содержит иерархию сотрудников фирмы
 * по производству программного обеспечения и
 * рассчитывает общую месячную зарплату сотрудников
 */

public class TestCreateService {

    public static void main(String[] args) {

        Employee[] employees = new Employee[5];
        Company company = new Company();

        employees[0] = new Developer("Developer Senior", "3150489A001PB0", 1000.55, 2.5);
        employees[1] = new Developer("Developer Middle", "3230792A002PB0", 555.2, 2);
        employees[2] = new Developer("Developer Junior", "3220491A003PB2", 400, 1);
        employees[3] = new TeamLead("Team Lead", "3250185A003PB1", 1200, 3, 125);
        employees[4] = new TeamLead("Team Lead assisstant", "3250183A003PB1", 1000, 1, 110);

        for (int i= 0; i< employees.length; i++){
            company.addEmployee(employees[i]);
        }

        System.out.println(company.toString());

        System.out.println("\nSalary payments per month " + new AccountingService(0).sumCurrentMonthSalary(employees));
    }
}
