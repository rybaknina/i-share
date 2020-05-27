package eu.senla.course.employee;

public class TeamLead extends AEmployee {
    private double rate;
    private double bonus;
    private Developer[] developers;

    public TeamLead(String name, String securityNumbet, double salary, double rate, double bonus) {
        super(name, securityNumbet, salary);
        this.rate = rate;
        this.bonus = bonus;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public Developer[] getDevelopers() {
        return developers;
    }

    public void setDevelopers(Developer[] developers) {
        this.developers = developers;
    }

    @Override
    public double getSalary() {
        return super.getSalary() * rate + bonus;
    }

    @Override
    public String toString() {
        return "TeamLead " +
                "Name = \"" + getName() + "\"" +
                ", Security Number = " + getSecurityNumber() +
                ", Base salary = " + super.getSalary() +
                ", Salary per month = " + getSalary() +
                " with rate = " + rate +
                " and bonus = " + bonus;
    }
}
