package carsharing.model;

public class Car {
    private int id;
    private String name;
    private int companyId;

    public Car(int id, String name, int carCompanyId) {
        this.id = id;
        this.name = name;
        this.companyId = carCompanyId;
    }

    public Car(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Car(int id) {
        this.id = id;
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

    public int getCompanyId() {
        return companyId;
    }
}
