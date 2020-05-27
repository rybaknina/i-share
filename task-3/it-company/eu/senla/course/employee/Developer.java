package eu.senla.course.employee;

public class Developer extends AEmployee {

    private double rate;

    public Developer(String name, String securityNumbet, double salary, double rate) {
        super(name, securityNumbet, salary);
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public double getSalary() {
        return super.getSalary()*rate;
    }

    @Override
    public String toString() {
        return "Developer " +
                "Name = \"" + getName() + "\"" +
                ", Security Number = " + getSecurityNumber() +
                ", Base salary = " + super.getSalary() +
                ", Salary per month = " + getSalary() +
                " with rate = " + rate;
    }
}
