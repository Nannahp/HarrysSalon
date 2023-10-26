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
        //firstMenu();
        //Enter search a date code here
        //bookingMenu();

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
        boolean running = true;
        Menu menu = new Menu("You have the following choices: ", new String[] {
                "1. Search for a date",
                "2. Quit the program"
        });

        menu.printMenu();
        System.out.print("Please write your choice here: ");

        while(running) {
            int userChoice = menu.readChoice();

            switch (userChoice) {
                case 1 -> enterDate();
                case 2 -> running = false;
                default -> System.out.println("Illegal choice. Please try again: ");
            }
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
        in.nextLine();

        Calender calender = new Calender("Harry's calender");
        calender.searchForDate(day,month,year);
        calender.showCalender();
    }

    //Method after selected date to either add, delete or edit bookings
    private void bookingMenu() {
        boolean running = true;
        Menu menu = new Menu("Now you have the following choices: ", new String[] {
                "1. Add a booking",
                "2. Delete a booking",
                "3. Edit a booking"
        });

        menu.printMenu();
        System.out.print("Please write your choice here: ");

        while(running) {
            int userChoice = menu.readChoice();

            switch (userChoice) {
                case 1 -> running = false;
                case 2 -> running = false;
                case 3 -> editBooking();
                default -> System.out.println("Illegal choice. Please try again: ");
            }
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

        while(running) {
            int userChoice = menu.readChoice();

            switch (userChoice) {
                case 1 -> running = false;
                case 2 -> running = false;
                default -> System.out.println("Illegal choice. Please try again: ");
            }
        }
    }

    private void addCustomer(String name) {
        Customer newCustomer = new Customer(name);
        customers.add(newCustomer);
    }
}