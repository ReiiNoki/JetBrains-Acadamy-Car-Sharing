package carsharing.model;

import java.util.ArrayList;
import java.util.List;

public class Company {

    private int id;
    private String name;
    private List<Car> carList;

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
        this.carList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + ". " + name;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void addCar(Car car) {
        carList.add(car);
    }
}
