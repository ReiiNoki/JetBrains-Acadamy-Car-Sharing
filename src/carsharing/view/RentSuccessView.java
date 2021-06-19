package carsharing.view;

public class RentSuccessView implements View {

    @Override
    public String getInput() {
        return null;
    }

    @Override
    public void display() {

    }

    public void display(String name) {
        System.out.println("You rented '" + name + "'");
    }
}
