package carsharing.controller;

import carsharing.model.*;
import carsharing.view.*;

import java.util.List;

public class Controller {
    private View view;
    private final CompanyDAO companyDAO;
    private final CarDAO carDAO;
    private final CustomerDAO customerDAO;

    public Controller(CompanyDAO companyDAO, CarDAO carDAO, CustomerDAO customerDAO) {
        this.companyDAO = companyDAO;
        this.carDAO = carDAO;
        this.customerDAO = customerDAO;
    }

    public void run() {
        companyDAO.createTable();
        carDAO.createTable();
        customerDAO.createTable();
        displayMainMenu();
    }

    private void displayMainMenu() {
        while (true) {
            view = new MainMenuView();
            view.display();
            String input = view.getInput();
            if ("1".equals(input)) {
                displayManagerActions();
            } else if ("2".equals(input)) {
                displayCustomers();
            } else if ("3".equals(input)) {
                addCustomer();
            } else if ("0".equals(input)) {
//                customerDAO.dropTable();
//                carDAO.dropTable();
//                companyDAO.dropTable();
                break;
            }
        }
    }

    private void addCustomer() {
        view = new NewCustomerView();
        view.display();
        String name = view.getInput();
        customerDAO.addCustomer(name);
    }

    private void displayCustomers() {
        List<Customer> customers = customerDAO.findAllCustomer();
        view = new CustomerListView(customers);
        view.display();

        if (customers.isEmpty()) {
            return;
        }

        int index = Integer.parseInt(view.getInput()) - 1;
        if (index == -1) {
            return;
        }

        Customer customer = customers.get(index);
        displayCustomerActions(customer);
    }

    private void displayCustomerActions(Customer customer) {
        int customerId = customer.getId();
        while (true) {
            view = new CustomerActionsView();
            view.display();
            String input = view.getInput();
            if ("1".equals(input)) {
                Customer resultCustomer = customerDAO.checkRentingCustomerById(customerId);
                if (resultCustomer.getCarList().size() == 1) {
                    System.out.println("You've already rented a car!");
                } else {
                    displayCompaniesForCustomer(customerId);
                }
            } else if ("2".equals(input)) {
                Customer resultCustomer = customerDAO.checkRentingCustomerById(customerId);
                if (resultCustomer.getCarList().size() == 1) {
                    displayReturn(customerId);
                } else {
                    System.out.println("You didn't rent a car!");
                }
            } else if ("3".equals(input)) {
                displayRentedCar(customer);
            } else if ("0".equals(input)) {
                break;
            }
        }
    }

    private void displayRentedCar(Customer customer) {
        view = new RentedCarListView();
        Car car = customerDAO.findRentedCar(customer.getId());
        if (car != null) {
            view.display();
            System.out.println(car.getName());
            System.out.println("Company:");
            String companyName = companyDAO.findCompanyById(car.getCompanyId());
            System.out.println(companyName);
        } else {
            System.out.println("You didn't rent a car!");
        }
    }

    private void displayReturn(int customerId) {
        Customer customer = customerDAO.findCustomerById(customerId);
        if (customer != null) {
            view = new ReturnCarView(customer);
            customerDAO.rentCar(customerId);
        } else {
            view = new ReturnCarView();
        }
        view.display();
    }

    private void displayManagerActions() {
        while (true) {
            view = new ManagerActionsView();
            view.display();
            String input = view.getInput();
            if ("1".equals(input)) {
                displayCompanies();
            } else if ("2".equals(input)) {
                addCompany();
            } else if ("0".equals(input)) {
                break;
            }
        }
    }

    private void addCompany() {
        view = new NewCompanyView();
        view.display();
        String name = view.getInput();
        companyDAO.addCompany(name);
    }

    private void displayCompanies() {
        List<Company> companies = companyDAO.findAllCompanies();
        view = new CompanyListView(companies);
        view.display();

        if (companies.isEmpty()) {
            return;
        }

        int index = Integer.parseInt(view.getInput()) - 1;
        if (index == -1) {
            return;
        }

        Company company = companies.get(index);
        showCompany(company);
    }

    private void displayCompaniesForCustomer(int customerId) {
        List<Company> companies = companyDAO.findAllCompanies();
        view = new CompanyListView(companies);
        view.display();

        if (companies.isEmpty()) {
            return;
        }

        int index = Integer.parseInt(view.getInput()) - 1;
        if (index == -1) {
            return;
        }

        Company company = companies.get(index);
        Car car = displayCarsForCustomer(company);
        if (car != null) {
            String carName = car.getName();
            int carId = car.getId();
            customerDAO.rentCar(customerId, carId);
            System.out.println("You rented '" + carName + "'");
        }
    }

    private void showCompany(Company company) {
        while (true) {
            view = new CompanyView(company);
            view.display();
            String input = view.getInput();
            if ("1".equals(input)) {
                displayCars(company);
            } else if ("2".equals(input)) {
                addCar(company);
            } else if ("0".equals(input)) {
                break;
            }
        }
    }

    private void addCar(Company company) {
        view = new NewCarView();
        view.display();
        String name = view.getInput();
        carDAO.addCar(name, company.getId());
    }

    private void displayCars(Company company) {
        List<Car> cars = carDAO.getCarsByCompanyId(company.getId());
        view = new CarListView(cars);
        view.display();
    }

    private Car displayCarsForCustomer(Company company) {
        List<Car> cars = carDAO.getCarsByCompanyIdForCustomer(company.getId());
        view = new RentCarView(cars);
        view.display();
        if (cars.isEmpty()) {
            return null;
        }
        int carsIndex = Integer.parseInt(view.getInput()) - 1;
        if (carsIndex == -1) {
            return null;
        }
        return cars.get(carsIndex);
    }
}