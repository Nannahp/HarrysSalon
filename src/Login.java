import java.util.Scanner;

public class Login {
    private Scanner in = new Scanner(System.in);

    public boolean runLogin(String realPassword) {
        String password;
        int tries = 0;

        //Makes sure this only runs while system is running
        while (tries != 3) {
            System.out.print("\nPlease enter your password here: ");
            password = in.nextLine();

            if (realPassword.equals(password)) {
                System.out.println("Your login is successful!");
                return true;
            } else {
                System.out.println("Your login failed!");
            }
            tries += 1;
        }
        //If statement that makes sure that the message is only displayed after 3 tries
        if (tries == 3) {
            System.out.println("Sorry, maximum attempts reached");
        }
        return false;
    }

}
