package eu.senla.course.entity;

import eu.senla.course.api.entity.IEntity;

import javax.persistence.*;

@Entity
@Table(name = "spot")
public class Spot implements IEntity {
    private static final long serialVersionUID = 5407284135064833379L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "garage_id")
    private Garage garage;

    public Spot() {

    }

    public Spot(Garage garage) {
        this.garage = garage;
    }

    public Spot(int id, Garage garage) {
        this.id = id;
        this.garage = garage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
