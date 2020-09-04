package eu.senla.course.entity;

import eu.senla.course.api.entity.IEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "garage")
public class Garage implements IEntity {
    private static final long serialVersionUID = -2617930426404733166L;

    private static final AtomicInteger count = new AtomicInteger(0);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL)
    private List<Spot> spots = new ArrayList<>();
    @OneToMany(mappedBy = "garage", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Cascade(org.hibernate.annotations.CascadeType.REPLICATE)
    private List<Mechanic> mechanics = new ArrayList<>();

    public Garage() {

    }

    public Garage(String name) {
        this.id = count.incrementAndGet();
        this.name = name;
    }

    public Garage(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static AtomicInteger getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    public List<Mechanic> getMechanics() {
        return mechanics;
    }

    public void setMechanics(List<Mechanic> mechanics) {
        this.mechanics = mechanics;
    }

    @Override
    public String toString() {
        return "Garage{" +
                "id = " + id +
                ", name = '" + name + '\'' +
                '}';
    }
}
