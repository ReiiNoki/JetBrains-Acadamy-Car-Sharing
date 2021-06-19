package carsharing.view;


import carsharing.model.Customer;

public class ReturnCarView implements View {
    private Customer customer = null;

    public ReturnCarView(Customer customer) {
        this.customer = customer;
    }

    public ReturnCarView() {

    }
    @Override
    public String getInput() {
        return null;
    }

    @Override
    public void display() {
        if (this.customer != null) {
            System.out.println("You've returned a rented car!");
        } else {
            System.out.println("You didn't rent a car!");
        }
    }
}
