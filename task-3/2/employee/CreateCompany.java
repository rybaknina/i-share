package employee;

public class CreateCompany {

    private Employee[] employees;

    public CreateCompany(Employee[] employees) {

        this.employees = employees;
    }

    public void orderEmployee(Employee employee){
        int numbersOfStaff = employees.length;
        if (employees[numbersOfStaff - 1] != null) {
            System.out.println("\nDear, " + employee.getName() + ". Company has no vacancy now. Try again later..");
            return;
        }
        for (int i = 0; i < numbersOfStaff; i++){
            if (employees[i] == null){
                employees[i] = employee;
                break;
            }
        }
    }
    public void printEmployees() {
        System.out.println("\nFull current staff: ");
        for (Employee employee : employees) {
            System.out.println(employee.toString());
            if (employee instanceof TeamLead) {
                System.out.println("\nTeam lead has developers: ");
                for (Developer developer:((TeamLead) employee).getDevelopers()){
                    System.out.println(developer.getName());
                }
            }
        }
    }
    public double sumCurrentMonthSalary(Employee[] employees){
        double currentMonthSalary = 0;
        for (Employee employee: employees){
            if (employee != null) {
                currentMonthSalary += employee.getSalary();
            }
        }
        return currentMonthSalary;
    }
}
