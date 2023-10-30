import java.util.ArrayList;
import java.util.Scanner;

public class BookingSystem {
    private ArrayList<Customer> customers = new ArrayList<>();
    private Scanner in = new Scanner(System.in);
    Calender calender = new Calender("Harry's calender");
    boolean systemRunning = true;

    public static void main(String[] args) {
        new BookingSystem().run();

    }

    public void run() {
        showIntroMessage();
        //Insert login code here
        while(systemRunning){runFirstMenu();}
    }

    //Hardcoded intro message before login
    private void showIntroMessage() {
        System.out.println("\n-----------------------------------------------");
        System.out.println("WELCOME TO HARRY'S BOOKINGSYSTEM");
        System.out.println("-----------------------------------------------");
        System.out.println("\nLogin right away, and get " +
                "\naccess to all the amazing stuff" +
                "\nin this bookingsystem :)\n");
        System.out.println("-----------------------------------------------");
    }

    //First method with menu choice to quit or search for date
    private void runFirstMenu() {
        Menu menu = new Menu("You have the following choices: ", new String[] {
                "1. Search for a date",
                "2. Quit the program"
        });

        menu.printMenu();
        System.out.print("Please write your choice here: ");

        int userChoice = menu.readChoice();

        switch (userChoice) {
           
            case 1 -> runBookingMenu(enterDate());
            case 2 -> closeProgram();
            default -> System.out.println("Illegal choice. Please try again: "); //enhanced switch, to avoid break

        }
    }

    private Day enterDate() {
        int day, month, year;

        do {
            System.out.print("\nPlease give the date in format 'DD': ");
            day = in.nextInt();
            in.nextLine(); //Scanner bug

            System.out.print("Please give the month in format 'MM': ");
            month = in.nextInt();
            in.nextLine(); //Scanner bug

            System.out.print("Please give the year in format 'YYYY': ");
            year = in.nextInt();
            in.nextLine(); //Scanner bug
            System.out.println(" "); //New line for better view when printing the day

        Day givenDate = calender.searchForDate(day,month,year);
        givenDate.showDay(); // edit, shows day instead of whole calendar


        } while (calender.searchForDate(day, month, year) == null); // runs until a valid date is entered
        return calender.searchForDate(day, month, year);
    }

    //Method after selected date to either add, delete or edit bookings

    private void runBookingMenu(Day day) {
        day.showDay();
        Menu menu = new Menu("Now you have the following choices: ", new String[] {

                "1. Add a booking",
                "2. Delete a booking",
                //"3. Edit a booking"
                "3. Go back"
        });
        menu.printMenu();
        System.out.print("Please write your choice here: ");

        int userChoice = menu.readChoice();

        switch (userChoice) {

            case 1 -> addBooking(day);
            case 2 -> closeProgram();
            default -> System.out.println("Returning to main menu ");

        }
    }

    private int chooseTimeSlot(String text) {
        int givenId = 0;
        System.out.print(text);
        while(givenId < 1 || givenId >8){ // if timeslot is before 10 or after 17 then keep trying
        int enteredTimeslot = in.nextInt();
        switch (enteredTimeslot) {
            case 10 -> givenId = 1;
            case 11 -> givenId = 2;
            case 12 -> givenId = 3;
            case 13 -> givenId = 4;
            case 14 -> givenId = 5;
            case 15 -> givenId = 6;
            case 16 -> givenId = 7;
            case 17 -> givenId = 8;
            default -> System.out.println("This is not a valid time slot. Please enter another time:");
        }}
        return givenId;
    }

    private void addBooking(Day day) {
        int givenId = chooseTimeSlot("\nIn what time slot do you want add a booking? Please write here: ");

        day.addBookingToTimeSlot(givenId);
        System.out.println("Here is the updated day: \n");
        day.showDay();
        runBookingMenu(day); //IDK. if this is the best way to return to a menu?

                firstMenu();
                break;
            case 2:
                System.out.println("Goodbye for now!");
                System.exit(0);
                break;
            default:
                System.out.println("Illegal choice. Please try again: ");
                menu.printMenu();
                System.out.print("Please write your choice here: ");
                menu.readChoice();
                break;
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
                closeProgram();
            case 2:
                closeProgram();
                System.out.println("Goodbye for now!");
                System.exit(0);
                break;
            default:
                System.out.println("Illegal choice. Please try again: ");
                editBooking();
                break;
        }
    }

    private void addCustomer(String name) {
        Customer newCustomer = new Customer(name);
        customers.add(newCustomer);
    }
    private void closeProgram(){ // code was used a lot so made a method.
        System.out.println("Goodbye for now!");
        systemRunning = false;
    }
}