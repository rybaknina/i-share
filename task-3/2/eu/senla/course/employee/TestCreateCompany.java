package eu.senla.course.employee;

/**
 * @author Nina Rybak
 * Программа содержит иерархию сотрудников фирмы
 * по производству программного обеспечения и
 * рассчитывает общую месячную зарплату сотрудников
 */

public class TestCreateCompany {
    private static final int MAX_NUMBER_OF_STAFF = 4;
    public static void main(String[] args) {

        Developer[] developers = new Developer[3];
        Employee developer1 = developers[0] = new Developer("Developer Senior", "3150489A001PB0", 1000.55, 2.5);
        Employee developer2 = developers[1] = new Developer("Developer Middle", "3230792A002PB0", 555.2, 2);
        Employee developer3 = developers[2] = new Developer("Developer Junior", "3220491A003PB2", 400, 1);
        Employee teamLead = new TeamLead("Team Lead", "3250185A003PB1", 1200, 3, 125);
        ((TeamLead) teamLead).setDevelopers(developers);

        Employee[] staff = new Employee[MAX_NUMBER_OF_STAFF];

        CreateCompany company = new CreateCompany(staff);
        company.orderEmployee(developer1);
        company.orderEmployee(developer2);
        company.orderEmployee(developer3);

        company.orderEmployee(teamLead);

        company.orderEmployee(new TeamLead("Team Lead assisstant", "3250183A003PB1", 1000, 1, 110));

        company.printEmployees();

        System.out.println("\nSalary payments per month " + company.sumCurrentMonthSalary(staff));
    }
}
