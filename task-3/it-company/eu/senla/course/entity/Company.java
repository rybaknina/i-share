package eu.senla.course.entity;

public class Company {
    private static final int MAX_NUMBER_OF_STAFF = 4;
    private Employee[] employees;

    public Company() {
        this.employees = new Employee[MAX_NUMBER_OF_STAFF];
    }

    public void addEmployee(Employee employee){

        if (employees[MAX_NUMBER_OF_STAFF - 1] != null) {
            System.out.println("\nDear, " + employee.getName() + ". Company has no vacancy now. Try again later..\n");
            return;
        }
        for (int i = 0; i < MAX_NUMBER_OF_STAFF; i++){
            if (employees[i] == null){
                employees[i] = employee;
                break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Full current staff: \n");
        for (Employee employee : employees) {
            stringBuilder.append(employee.toString());
        }
        return stringBuilder.toString();
    }
}

