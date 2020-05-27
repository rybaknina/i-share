package eu.senla.course.employee;

public abstract class Employee{
    private String name;
    private String securityNumber;
    private double salary;

    public Employee(String name, String securityNumbet, double salary) {
        this.name = name;
        this.securityNumber = securityNumbet;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecurityNumber() {
        return securityNumber;
    }

    public void setSecurityNumber(String securityNumber) {
        this.securityNumber = securityNumber;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee " +
                "name = \"" + name + "\"" +
                ", Ssecurity Number = " + securityNumber +
                ", salary = " + salary;
    }
}
