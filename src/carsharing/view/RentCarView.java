package carsharing.view;

import carsharing.Main;
import carsharing.model.Car;

import java.util.List;

public class RentCarView implements View{
    private final Menu menu = new Menu().addItem("Back", "0");

    private final List<Car> list;

    public RentCarView(List<Car> cars) {
        this.list = cars;
    }

    @Override
    public String getInput() {
        while (true) {
            String input = Main.scanner.nextLine().strip();
            if (input.matches("[0-9]+")) {
                int index = Integer.parseInt(input);
                if (index >= 0 && index <= list.size()) {
                    return input;
                }
            } else {
                displayInvalidIndexError();
            }
        }
    }

    @Override
    public void display() {
        if (list.isEmpty()) {
            displayEmptyListError();
        } else {
            System.out.println("\nChoose a car:");
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + 1 + ". " + list.get(i).getName());
            }
            menu.show();
        }
    }

    private void displayEmptyListError() {
        System.out.println("\nThe car list is empty!");
    }

    private void displayInvalidIndexError() {
        System.out.println("Enter a valid index.");
    }

}