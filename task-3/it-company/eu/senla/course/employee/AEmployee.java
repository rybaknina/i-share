package eu.senla.course.employee;

public abstract class AEmployee {
    private String name;
    private String securityNumber;
    private double salary;

    public AEmployee(String name, String securityNumbet, double salary) {
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
        return "AEmployee " +
                "name = \"" + name + "\"" +
                ", Ssecurity Number = " + securityNumber +
                ", salary = " + salary;
    }
}
