package eu.senla.course.entity;

public class Spot {
    private int id;
    private Garage garage;

    public Spot(int id, Garage garage) {
        this.id = id;
        this.garage = garage;
    }

    public int getId() {
        return id;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    @Override
    public String toString() {
        return "Spot{" +
                "id = " + id +
                ", garage = " + garage +
                '}';
    }
}
