package carsharing.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private static final String DEFAULT_DB_NAME = "default_car_sharing_db";
    private static final String PATH = "jdbc:h2:./src/carsharing/db/";
    private static final String DRIVER = "org.h2.Driver";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS customer (" +
            "id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) UNIQUE NOT NULL, " +
            "rented_car_id INTEGER, " +
            "FOREIGN KEY (rented_car_id) REFERENCES car(id)" +
            ")";

    private final String dbFilename;

    public CustomerDAO(String dbFileName) throws ClassNotFoundException {
        this.dbFilename = PATH + dbFileName;
        Class.forName(DRIVER);
    }

    public CustomerDAO() throws ClassNotFoundException {
        this(DEFAULT_DB_NAME);
    }

    public void createTable() {
        try (Connection conn = DriverManager.getConnection(dbFilename);
             Statement stmt = conn.createStatement()) {

            conn.setAutoCommit(true);
            stmt.executeUpdate(SQL_CREATE_TABLE);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void addCustomer(String name) {
        String sql = "INSERT INTO customer(id, name) VALUES(NULL, '" + name + "')";

        if (findCustomerByName(name) != null) {
            return;
        }

        try (Connection conn = DriverManager.getConnection(dbFilename);
             Statement stmt = conn.createStatement()) {

            conn.setAutoCommit(true);
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Customer findCustomerByName(String name) {
        String sql = "SELECT * FROM customer WHERE name = " + name;
        try (Connection conn = DriverManager.getConnection(dbFilename);
             Statement stmt = conn.createStatement()) {

            conn.setAutoCommit(true);
            ResultSet resultSet = stmt.executeQuery(sql);

            int companyId = resultSet.getInt("id");
            String companyName = resultSet.getString("name");
            return new Customer(companyId, companyName);

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Customer> findAllCustomer() {
        String sql = "SELECT * FROM customer ORDER BY id";
        List<Customer> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbFilename);
             Statement stmt = conn.createStatement()) {

            conn.setAutoCommit(true);
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                list.add(new Customer(id, name));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public void dropTable() {
        String sql = "DROP TABLE customer";
        try (Connection conn = DriverManager.getConnection(dbFilename);
             Statement stmt = conn.createStatement()){

            conn.setAutoCommit(true);
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void rentCar(int customerId, int carId) {
        String sql = "UPDATE customer SET rented_car_id = " + carId + " " +
                "WHERE id = " + customerId + " ;";
        try (Connection conn = DriverManager.getConnection(dbFilename);
             Statement stmt = conn.createStatement()){

            conn.setAutoCommit(true);
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void rentCar(int customerId) {
        String sql = "UPDATE customer SET rented_car_id = null " +
                "WHERE id = " + customerId + " ;";
        try (Connection conn = DriverManager.getConnection(dbFilename);
             Statement stmt = conn.createStatement()){

            conn.setAutoCommit(true);
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Car findRentedCar(int id) {
        String sql = "SELECT * FROM car " +
                "INNER JOIN customer " +
                "WHERE customer.id = " + id + " AND customer.rented_car_id = car.id;";
        Car car = null;
        try (Connection conn = DriverManager.getConnection(dbFilename);
             Statement stmt = conn.createStatement()){

            conn.setAutoCommit(true);
            ResultSet resultSet = stmt.executeQuery(sql);

            if (resultSet.next()) {
                int carId = resultSet.getInt("id");
                String carName = resultSet.getString("name");
                int companyId = resultSet.getInt("company_id");
                car = new Car(carId, carName, companyId);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return car;
    }

    public Customer findCustomerById(int customerId) {
        String sql = "SELECT * FROM customer " +
                "WHERE id = " + customerId + " AND rented_car_id IS NOT null;";
        Customer customer = null;
        try (Connection conn = DriverManager.getConnection(dbFilename);
             Statement stmt = conn.createStatement()){

            conn.setAutoCommit(true);
            ResultSet resultSet = stmt.executeQuery(sql);

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int rentedCarId = resultSet.getInt("rented_car_id");
                customer = new Customer(id, name);
                customer.addCar(new Car(rentedCarId));
            } else {
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return customer;
    }

    public Customer checkRentingCustomerById(int customerId) {
        String sql = "SELECT * FROM customer " +
                "WHERE id = " + customerId + ";";
        Customer customer = null;
        try (Connection conn = DriverManager.getConnection(dbFilename);
             Statement stmt = conn.createStatement()){

            conn.setAutoCommit(true);
            ResultSet resultSet = stmt.executeQuery(sql);

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int rentedCarId = resultSet.getInt("rented_car_id");
                customer = new Customer(id, name);
                if (rentedCarId != 0) {
                    customer.addCar(new Car(rentedCarId));
                }
            } else {
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return customer;
    }
}
