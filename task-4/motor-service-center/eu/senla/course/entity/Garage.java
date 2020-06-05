package eu.senla.course.entity;

public class Garage {
    private int id;
    private Spot[] spots;
    private Mechanic[] mechanics;

    public Garage(int id) {
        this.id = id;
        this.spots = new Spot[1];
    }

    public Garage(int id, int capacity) {
        this.id = id;
        this.spots = new Spot[capacity];
    }
    public int getId() {
        return id;
    }

    public void setSpots(Spot[] spots) {
        this.spots = spots;
    }

    public Spot[] getSpots() {
        return spots;
    }

    public Mechanic[] getMechanics() {
        return mechanics;
    }

    public void setMechanics(Mechanic[] mechanics) {
        this.mechanics = mechanics;
    }

    // Добавить место в гараже
    public void addSpot(Spot spot){
        int len = spots.length;
        if (spots[len - 1] != null) {
            System.out.println("Garage is full");
        }
        else {
            for (int i = 0; i < len; i++) {
                if (spots[i] == null) {
                    spots[i] = spot;
                    break;
                }
            }
        }
    }

    // удалить место в гараже
    public void deleteSpot(int id){
        for (int i=0; i < spots.length; i++){
            if (spots[i].getId() == id){
                spots[i] = null;
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Garage{" +
                "id = " + id +
                '}';
    }
}
