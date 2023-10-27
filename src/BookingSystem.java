import java.util.ArrayList;
import java.util.Scanner;

public class BookingSystem {
    private ArrayList<Customer> customers = new ArrayList<>();
    private Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        new BookingSystem().run();
    }

    public void run() {
        introMessage();
        //Insert login code here
        firstMenu();
    }

    //Hardcoded intro message before login
    private void introMessage() {
        System.out.println("\n-----------------------------------------------");
        System.out.println("WELCOME TO HARRY'S BOOKINGSYSTEM");
        System.out.println("-----------------------------------------------");
        System.out.println("\nLogin right away, and get " +
                "\naccess to all the amazing stuff" +
                "\nin this bookingsystem :)\n");
        System.out.println("-----------------------------------------------");
    }

    //First method with menu choice to quit or search for date
    private void firstMenu() {
        Menu menu = new Menu("You have the following choices: ", new String[] {
                "1. Search for a date",
                "2. Quit the program"
        });

        menu.printMenu();
        System.out.print("Please write your choice here: ");

        int userChoice = menu.readChoice();

        switch (userChoice) {
            case 1:
                enterDate();
            case 2:
                System.out.println("Goodbye for now!");
            default:
                System.out.println("Illegal choice. Please try again: ");
        }
    }

    private void enterDate() {
        System.out.print("\nPlease give the date in format 'DD': ");
        int day = in.nextInt();
        in.nextLine(); //Scanner bug

        System.out.print("Please give the month in format 'MM': ");
        int month = in.nextInt();
        in.nextLine(); //Scanner bug

        System.out.print("Please give the year in format 'YYYY': ");
        int year = in.nextInt();
        in.nextLine(); //Scanner bug
        System.out.println(" "); //New line for better view when printing the day

        Calender calender = new Calender("Harry's calender");
        Day givenDate = calender.searchForDate(day,month,year);
        givenDate.showDay(); // edit, shows day instead of whole calendar

        bookingMenu(day, month, year);
    }

    //Method after selected date to either add, delete or edit bookings
    //Parameters needed to know the date
    private void bookingMenu(int day, int month, int year) {
        Menu menu = new Menu("Now you have the following choices: ", new String[] {
                "1. Add a booking",
                "2. Delete a booking"
                //"3. Edit a booking"
        });

        menu.printMenu();
        System.out.print("Please write your choice here: ");

        int userChoice = menu.readChoice();

        switch (userChoice) {
            case 1:
                addBooking(day, month, year);
            case 2:
                System.out.println("Goodbye for now!");
                System.exit(0);
            case 3:
                System.out.println("Goodbye for now!");
                System.exit(0);
            default:
                System.out.println("Illegal choice. Please try again: ");
        }
    }

    private int chooseTimeSlot(String text) {
        int givenId = 0;
        System.out.print(text);
        int enteredTimeslot = in.nextInt();
        in.nextLine(); //Scanner bug

        switch (enteredTimeslot) {
            case 10 -> givenId = 1;
            case 11 -> givenId = 2;
            case 12 -> givenId = 3;
            case 13 -> givenId = 4;
            case 14 -> givenId = 5;
            case 15 -> givenId = 6;
            case 16 -> givenId = 7;
            case 17 -> givenId = 8;
            default -> System.out.println("This time slot does not seem to exist. Please write another timeslot: ");
        }
        return givenId;
    }

    private void addBooking(int day, int month, int year) {
        int givenId = chooseTimeSlot("\nIn what time slot do you want add a booking? Please write here: ");

        Calender calender = new Calender("Harry's calender");
        Day givenDate = calender.searchForDate(day,month,year);
        givenDate.addBookingToTimeSlot(givenId);

        System.out.println("Here is the updated day: \n");
        givenDate.showDay();

        Menu menu = new Menu("Now would you like to: ", new String[] {
                "1. Go back to Menu",
                "2. Quit the system"
        });

        menu.printMenu();
        System.out.print("Please write your choice here: ");

        int userChoice = menu.readChoice();

        switch (userChoice) {
            case 1:
                firstMenu();
            case 2:
                System.out.println("Goodbye for now!");
                System.exit(0);
            default:
                System.out.println("Illegal choice. Please try again: ");
        }
    }

    //Menu for editing bookings
    private void editBooking() {
        boolean running = true;
        Menu menu = new Menu("Would you like to: ", new String[] {
                "1. Add a product to the given booking",
                "2. Change the payment method"
        });

        menu.printMenu();
        System.out.print("Please write your choice here: ");


        int userChoice = menu.readChoice();

        switch (userChoice) {
            case 1:
                System.out.println("Goodbye for now!");
                System.exit(0);
            case 2:
                System.out.println("Goodbye for now!");
                System.exit(0);
            default:
                System.out.println("Illegal choice. Please try again: ");
        }
    }

    private void addCustomer(String name) {
        Customer newCustomer = new Customer(name);
        customers.add(newCustomer);
    }
}