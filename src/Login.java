import java.util.Scanner;
import java.util.InputMismatchException;

public class Login {
    private Scanner in = new Scanner(System.in);

    public boolean runLogin(String realPassword) {
        String password;
        int tries = 0;

        // Makes sure this only runs while the system is running
        while (tries != 3) {
            System.out.print("\nPlease enter your password here: ");
            try {
                password = in.nextLine();

                if (realPassword.equals(password)) {
                    System.out.println("Your login is successful!");
                    return true;
                } else {
                    System.out.println("Your login failed! " + (2 - tries) + " attempts left.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid password.");
                in.nextLine(); // Clear the input buffer
            }
            tries += 1;
        }
        // If statement that makes sure the message is only displayed after 3 tries
        System.out.println("Sorry, maximum attempts reached");
        return false;
    }
}
