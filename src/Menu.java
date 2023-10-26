import java.util.Scanner;

public class Menu {
    private String menuText;
    private String[] menuItems;

    public Menu(String menuText, String[] menuItems) {
        this.menuText = menuText;
        this.menuItems = menuItems;
    }

    //Prints the heading text and the menu
    public void printMenu() {
        String printString = menuText + "\n";

        for (int i = 0; i < menuItems.length; i++) {
            printString += menuItems[i] + "\n";
        }
        System.out.println("\n" + printString);
    }

    //Read the choice from the menu
    public int readChoice() {
        Scanner in = new Scanner(System.in);
        boolean validChoice = false;
        int choice = -1;

        //Loop to read choice, that runs as long as the choice is valid
        while (! validChoice) {
            if (in.hasNextInt()) {
                choice = in.nextInt();
                validChoice = true;
            } else {
                in.nextLine();
            }
        }

        return choice;
    }
}
