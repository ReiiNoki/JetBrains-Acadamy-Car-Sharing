package carsharing.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private int id;
    private String name;
    private List<Car> carList;

    public Customer(int id, String name) {
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

    public List<Car> getCarList() {
        return this.carList;
    }

    public void addCar(Car car) {
        this.carList.add(car);
    }

    @Override
    public String toString() {
        return id + ". " + name;
    }
}
