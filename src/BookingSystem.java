import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class BookingSystem {
    private ArrayList<Customer> customers = new ArrayList<>();
    private Scanner in = new Scanner(System.in);
    Calender calender = new Calender("Harry's calender");
    boolean systemRunning = true;

    private boolean isBeforeToday;

    public static void main(String[] args) {
        new BookingSystem().run();
    }

    public void run() {
        // adding hardcoded day for accountant example - ny bookings hver gang
        //____________________________________________
        showIntroMessage();
        runLogin();
        //Insert login code here
        while(systemRunning){runFirstMenu();}
    }


    //_________________________________________________________________________________________

    //!\\ For the hardcoded day again
    public ArrayList<Product> randomProducts() {
        ArrayList<Product> randomProducts = new ArrayList<>();
        for (int i = 0; i < (int)(Math.random() * 3) + 1; i++) {
            randomProducts.add(ProductBuilder.getProducts().get((int)(Math.random() * 5) + 1));
        }
        return randomProducts;
    }

    // *******************************************************

    //!\\ ADDING a hardcoded day to check out when check past dates details!
    public void addHardcodedDay() {
        Day hardcodedDay = new Day(3, 3, 2020);
        Customer hardcodedCustomer = new Customer("H. Uman");
        ArrayList<Booking> hardcodedBookings = new ArrayList<>();
        hardcodedBookings.add(new Booking(1, hardcodedDay, hardcodedCustomer, 345, randomProducts()));
        hardcodedBookings.add(new Booking(2, hardcodedDay, hardcodedCustomer, 345, randomProducts()));
        hardcodedBookings.add(new Booking(3, hardcodedDay, hardcodedCustomer, 345, randomProducts()));
        hardcodedBookings.add(new Booking(4, hardcodedDay, hardcodedCustomer, 99, randomProducts()));
        hardcodedBookings.add(new Booking(5, hardcodedDay, hardcodedCustomer, 99, randomProducts()));
        hardcodedBookings.add(new Booking(6, hardcodedDay, hardcodedCustomer, 246, randomProducts()));
        hardcodedBookings.add(new Booking(7, hardcodedDay, hardcodedCustomer, 567, randomProducts()));
        hardcodedBookings.add(new Booking(8, hardcodedDay, hardcodedCustomer, 123, randomProducts()));

        calender.addDay(hardcodedDay);
        hardcodedDay.setBookings(hardcodedBookings);

        System.out.println("---DISCLAIMER---DISCLAIMER---DISCLAIMER---");
        // Forklaring på hvilket hardcoded dato der er at tjekke hvis man vil kunne se noget spændende
        // ellers er der nemlig kun tomme days med tomme bookings fordi vi ikke kan add en booking for
        // en dag før dagen dag.
        System.out.println("Please note that until we have the ability to keep dates in file,");
        System.out.println("with the methods used and choices made AND/OR the fact that it would require");
        System.out.println("a few days to do this authentically we are not really able to see");
        System.out.println("real past dates that have been added before today's date");
        System.out.println("Therefore we have provided a past date hardcoded as a dummy date");
        System.out.println("so that you teachers can test the program without being cheated from ");
        System.out.println("the accountant's perspective");
        System.out.println();
        System.out.println("The hardcoded date to check is the 03-03-2020");
        System.out.println("But feel free to also check other dates out too, they are just boring as they");
        System.out.println("are empty by default.");
        System.out.println("---DISCLAIMER---DISCLAIMER---DISCLAIMER---");
    }

    //_________________________________________________________________________________________



    //Hardcoded intro message before login
    private void showIntroMessage() {
        System.out.println("\n-----------------------------------------------");
        System.out.println("WELCOME TO HARRY'S BOOKINGSYSTEM");
        System.out.println("HAIRY? SEE COTTER!");
        System.out.println("-----------------------------------------------");
        System.out.println("\nLogin right away, and get " +
                "\naccess to all the amazing stuff" +
                "\nin this bookingsystem :)\n");
        System.out.println("-----------------------------------------------");
    }

    //Login code where the 'right login' is hardcoded
    private void runLogin() {
        Login login = new Login();
        if (!login.runLogin("hairyharry")) {
            closeProgram();
        } else {
            systemRunning = true;
        }
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
            case 1 -> sendToCorrectMenu(enterDate());
            case 2 -> closeProgram();
            default -> System.out.println("Illegal choice. Please try again: "); //enhanced switch, to avoid break
        }
    }

    private boolean isDateBeforeToday(Day day) {
        isBeforeToday = day.getDate().isBefore(LocalDate.now());
        return isBeforeToday;
    }

    private void sendToCorrectMenu(Day day) {
        isBeforeToday = isDateBeforeToday(day);
        if (isBeforeToday) {
            // Accountant menu
            System.out.println("The date you have entered is before today!");
            addHardcodedDay();
            runAccountantMenu(day);
        } else {
            // Employee - booking menu
            runBookingMenu(day);
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
                "3. Go back to main menu"
        });
        menu.printMenu();
        System.out.print("Please write your choice here: ");

        int userChoice = menu.readChoice();

        switch (userChoice) {
            case 1 -> addBooking(day);
            case 2 -> deleteBooking(day);
            default -> System.out.println("Returning to main menu ");
        }
    }

    //Method for accountants
    private void runAccountantMenu(Day day) {
        day.showDay();
        Menu menu = new Menu("Now you have the following choices: ", new String[] {
                "1. See booking details",
                "2. Go back to main menu"
        });
        menu.printMenu();
        System.out.print("Please write your choice here: ");

        int userChoice = menu.readChoice();

        switch (userChoice) {
            case 1 -> seeBookingDetail(day);
            default -> System.out.println("Returning to main menu ");
        }
    }

    private int chooseTimeSlot(int chosenTimeslot) {
        int timeslotId = 0;
        while(timeslotId < 1 || timeslotId>8){ // if timeslot is before 10 or after 17 then keep trying
        switch (chosenTimeslot) {
            case 10 -> timeslotId = 1;
            case 11 -> timeslotId = 2;
            case 12 -> timeslotId = 3;
            case 13 -> timeslotId = 4;
            case 14 -> timeslotId = 5;
            case 15 -> timeslotId = 6;
            case 16 -> timeslotId = 7;
            case 17 -> timeslotId = 8;
            default -> System.out.println("This is not a valid time slot. Please enter another time:");
        }}
        return timeslotId;
    }

    private void addBooking(Day day) {
        int chosenTimeslot;
        int timeslotId;
        String userChoice;

        System.out.print("\nIn what time slot do you want to add a booking? Please write here: ");
        chosenTimeslot = in.nextInt();
        in.nextLine(); //Scanner bug

        timeslotId = chooseTimeSlot(chosenTimeslot);
        day.addBookingToTimeSlot(timeslotId);

        System.out.println("Here is the updated day: \n");
        day.showDay();

        //Det er den som displayer Day to gange, så lad os bare slet den tror jeg
        //runBookingMenu(day); //IDK. if this is the best way to return to a menu?
    }

    private void deleteBooking(Day day) {
        int chosenTimeslot;
        int timeslotId;
        String userChoice;

        System.out.print("\nIn what time slot do you want to delete a booking? Please write here: ");
        chosenTimeslot = in.nextInt();
        in.nextLine(); //Scanner bug

        timeslotId = chooseTimeSlot(chosenTimeslot);
        day.deleteBookingByTimeSlot(timeslotId);

        System.out.println("Here is the updated day: \n");
        day.showDay();

        //runBookingMenu(day); //IDK. if this is the best way to return to a menu?
    }


    private void seeBookingDetail(Day day) {
        int chosenTimeSlot;
        int timeSlotId;
        String userChoice;

        System.out.print("\nIn what time slot do you want to see the booking details? Please write here: ");

        chosenTimeSlot = in.nextInt();
        timeSlotId = chooseTimeSlot(chosenTimeSlot);

        System.out.println(day.getBookings().get(timeSlotId-1).toString());
    }


    //Menu for editing bookings
    private void editBooking(Day day) {
        Menu menu = new Menu("Would you like to: ", new String[] {
                "1. Edit product list",
                "2. Edit customer name",
                "3. Edit haircut price",
                //"4. Change payment method"
        });

        menu.printMenu();
        System.out.print("Please write your choice here: ");

        int userChoice = menu.readChoice();

        switch (userChoice) {
            case 1 -> closeProgram();
            case 2 -> editCustomerName(day);
            case 3 -> editHaircutPrice(day);
            //case 4 -> closeProgram();
            default -> System.out.println("Illegal choice. Please try again: ");
        }
    }

    private void editCustomerName(Day day) {
        int chosenTimeSlot;
        int timeSlotId;

        System.out.print("\nIn what time slot do you want to edit the name? \nPlease write here: ");

        chosenTimeSlot = in.nextInt();
        in.nextLine(); //Scanner bug
        timeSlotId = chooseTimeSlot(chosenTimeSlot);

        day.editCustomerNameByTimeSlot(timeSlotId);
        System.out.println("Here is the updated day: \n");
        runBookingMenu(day);
    }

    private void editHaircutPrice(Day day) {
        int chosenTimeSlot;
        int timeSlotId;

        System.out.print("\nIn what time slot do you want to see the haircut price? Please write here: ");

        chosenTimeSlot = in.nextInt();
        in.nextLine(); //Scanner bug
        timeSlotId = chooseTimeSlot(chosenTimeSlot);

        day.editHaircutPriceByTimeSlot(timeSlotId);
        day.displayBookingList();
        System.out.println("Here is the updated day: \n");
        runBookingMenu(day);
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