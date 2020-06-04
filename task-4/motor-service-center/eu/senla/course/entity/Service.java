package eu.senla.course.entity;

public class Service {
    private int id;
    private String name;
    private int hours;
    private double hourlyPrice;

    public Service(int id, String name) {
        this.id = id;
        this.name = name;
        this.hours = 1;
        this.hourlyPrice = 5;
    }

    public Service(int id, String name, int hours, double hourlyPrice) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.hourlyPrice = hourlyPrice;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public double getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(double hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id = " + id +
                ", name = '" + name + '\'' +
                '}';
    }
}
