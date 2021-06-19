package carsharing.view;

import carsharing.Main;

public class CustomerActionsView implements View{
    
    private static final Menu menu = new Menu()
            .addItem("Rent a car", "1")
            .addItem("Return a rented car", "2")
            .addItem("My rented car", "3")
            .addItem("Back", "0");

    @Override
    public String getInput() {
        while (true) {
            String actionKey = Main.scanner.nextLine().strip();
            if (menu.isValidKey(actionKey)) {
                return actionKey;
            } else {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    @Override
    public void display() {
        System.out.println();
        menu.show();
    }
}

