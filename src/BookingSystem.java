import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BookingSystem {
    private Scanner in = new Scanner(System.in);
    Calender calender = new Calender("Harry's calender");
    boolean systemRunning = true;
    private boolean isBeforeToday;

    String workingDirectory = System.getProperty("user.dir");
    String filenameOnly = "/src/BookingSaved.txt";
    String filename = Paths.get(workingDirectory, filenameOnly).toString();


    public static void main(String[] args) throws FileNotFoundException {
        new BookingSystem().run();
    }

    public Menu openDayMenu = new Menu("Now you have the following choices: ", new String[]{
            "1. Choose a timeslot",  "2. Go to one of the following dates",
            "3. Go back to main menu"
    });

    public Menu bookingOptionMenu = new Menu("Now you have the following choices: ", new String[]{
            "1. Add a booking to timeslot",
            "2. Go back to main menu"
    });
    public Menu editOptionMenu = new Menu("Now you have the following choices: ", new String[]{
            "1. Edit booking","2. Delete booking",
            "3. Go back to main menu"
    });
    public Menu closedMenu = new Menu("Now you have the following choices: ", new String[]{
            "1. Go to one of the following dates", "2. Go back to main menu"
    });
    public Menu mainMenu = new Menu("You have the following choices: ", new String[]{
            "1. Search for a date", "2. Register holidays", "3. Quit the program"
    });
    public Menu accountantMenu = new Menu("Now you have the following choices: ", new String[]{
            "1. See booking details", "2. Go back to main menu"
    });
    public Menu editBookingMenu = new Menu("Would you like to: ", new String[]{
            "1. Edit product list", "2. Edit customer name", "3. Edit haircut price",
            "4. Go back to main menu"
            //"4. Change payment method"
    });
    public Menu editProductsMenu = new Menu("\nWould you like to: ", new String[]{
            "1. Add a product to the list", "2. Remove a product from the list",
            "3. Go back to main menu"
    });

    public void run() throws FileNotFoundException {
        createFile(filename);
        loadBookingData();
        //addHardcodedDay();
        showIntroMessage();
        runLogin();
        while (systemRunning) {
            runMainMenu();
        }
        calender.saveBookingDataToFile(filename);
    }

    private void createFile(String filename) {
        File file = new File(filename);
        try {
            if (file.exists()) {
                System.out.println("File already exists.");
            } else {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                } else {
                    System.out.println("File could not be created.");
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    private void loadBookingData() throws FileNotFoundException {
        calender.loadBookingDataFromFile(filename, calender);
    }

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

    //First method with menu choice to quit, register holidays or search for date
    private void runMainMenu() {
        mainMenu.printMenu();
        System.out.print("Please write your choice here: ");

        int userChoice = mainMenu.readChoice();

        switch (userChoice) {
            case 1 -> sendToCorrectMenu(enterDate());
            case 2 -> registerHolidays();
            case 3 -> closeProgram();
            default -> System.out.println("Illegal choice. Please try again:\n"); //enhanced switch, to avoid break
        }
    }

    private Day enterDate() {
        int day = 0;
        int month = 0;
        int year = 0;

        do {
            try {
                System.out.print("\nPlease give the day in format 'DD': ");
                int inputDay = in.nextInt();
                in.nextLine(); // Consume the newline character left in the buffer

                if (inputDay < 1 || inputDay > 31) {
                    System.out.println("Invalid day. Please ensure the day is between 1 and 31.");
                    continue; // Invalid day, loop again
                }
                day = inputDay;

                System.out.print("Please give the month in format 'MM': ");
                int inputMonth = in.nextInt();
                in.nextLine(); // Consume the newline character left in the buffer

                if (inputMonth < 1 || inputMonth > 12) {
                    System.out.println("Invalid month. Please ensure the month is between 1 and 12.");
                    continue; // Invalid month, loop again
                }
                month = inputMonth;

                System.out.print("Please give the year in format 'YYYY': ");
                int inputYear = in.nextInt();
                in.nextLine(); // Consume the newline character left in the buffer

                if (inputYear < 2000 || inputYear > 2030) {
                    System.out.println("Invalid year. Please ensure the year is between 2000 and 2030.");
                    continue; // Invalid year, loop again
                }
                year = inputYear;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numeric values for the date.");
                in.nextLine(); // Clear the input buffer
            }
        } while (year == 0); // runs until a valid date is entered
        return calender.searchForDate(day, month, year);
    }

    private boolean isDateBeforeToday(Day day) {
        isBeforeToday = day.getDate().isBefore(LocalDate.now());
        return isBeforeToday;
    }

    private void sendToCorrectMenu(Day day) {
        isBeforeToday = isDateBeforeToday(day);
        if (isBeforeToday) {
            sendToPastMenu(day);
        } else {
            sendToFutureMenu(day);
        }
    }

    private void sendToPastMenu(Day day) {
        System.out.println("The date you have entered is before today!");

        System.out.println();
        // Forklaring på hvilken hardcoded dato der er at tjekke før i dag da man ikke kan booke i fortiden
        System.out.println("\n---DISCLAIMER---DISCLAIMER---DISCLAIMER---");
        System.out.println("Please note that until we have the ability to keep dates in file,");
        System.out.println("with the methods used and choices made at the moment, we are not really able to see");
        System.out.println("real past dates that have been added before today's date");
        System.out.println("Therefore we have provided a past date, hardcoded as a dummy date");
        System.out.println("so that the teachers can test the program from the accountant's perspective");
        System.out.println();
        System.out.println("The hardcoded date to check is: 03-03-2020");
        System.out.println("But feel free to also check other dates too! They are just a bit boring as they");
        System.out.println("are empty by default :)");
        System.out.println("---DISCLAIMER---DISCLAIMER---DISCLAIMER---\n");
        System.out.println();

        runAccountantMenu(day);
    }

    private void sendToFutureMenu(Day day) {
        calender.showCalender(day);
        if (day.getWeekend() || day.getHoliday()) {
            runClosedDayMenu(day);
        } else {
            runOpenDayMenu(day);
        }
    }

    private void runClosedDayMenu(Day day) {
        System.out.println("\nYou are currently on " + day.toString());
        System.out.println("Current date is closed");

        closedMenu.printMenu();
        System.out.print("Please write your choice here: ");
        int userChoice = closedMenu.readChoice();
        switch (userChoice) {
            case 1 -> goToAnotherDate(day);
            case 2 -> {
                System.out.println("Returning to the main menu");
                runMainMenu();
            }
            default -> {
                System.out.print("This is not a valid choice. Please try again!\n");
                runClosedDayMenu(day);
            }
        }
    }

    private void goToAnotherDate(Day day) {
        Day day1 = calender.searchForDate(day.getDay() + 1, day.getMonth(), day.getYear());
        Day day2 = calender.searchForDate(day.getDay() + 2, day.getMonth(), day.getYear());
        Day day3 = calender.searchForDate(day.getDay() + 3, day.getMonth(), day.getYear());
        Day day4 = calender.searchForDate(day.getDay() + 4, day.getMonth(), day.getYear());
        Menu menu = new Menu("Which date would you like to go to: ", new String[]{
                "1. " + day1.toString(), "2. " + day2.toString(), "3. " + day3.toString(),
                "4. " + day4.toString(),
        });
        menu.printMenu();
        System.out.print("Please write your choice here: ");
        int userChoice = menu.readChoice();

        switch (userChoice) {
            case 1 -> sendToFutureMenu(day1);
            case 2 -> sendToFutureMenu(day2);
            case 3 -> sendToFutureMenu(day3);
            case 4 -> sendToFutureMenu(day4);
            default -> {
                System.out.print("This is not a valid choice. Please try again!\n");
                goToAnotherDate(day);
            }
        }
    }
    private void runOpenDayMenu(Day day){
        System.out.println("\nYou are currently on " + day.toString());
        openDayMenu.printMenu();
        System.out.print("Please write your choice here: ");
        int userChoice = openDayMenu.readChoice();

        switch (userChoice){
            case 1 -> runBookingMenu(day);
            case 2 -> goToAnotherDate(day);
            case 3-> runMainMenu();
        }

    }



    //Method after selected date to either add, delete or edit bookings
    private void runBookingMenu(Day day) {
        int timeslotId;

        System.out.print("\nWhat time slot do you want to edit? Please write here: ");
        try {
            timeslotId = chooseTimeSlot();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid time slot.");
            in.nextLine(); // Clear the input buffer
            return;
        }
        //String customerName = day.getBookings().get(timeslotId-1).getCustomer().getName();
        boolean bookingExist = day.checkBookingInEditBooking(day, timeslotId);
        if (!bookingExist) {
            bookingOptionMenu.printMenu();
            System.out.print("Please write your choice here: ");
            int userChoice = bookingOptionMenu.readChoice();

            switch (userChoice) {
                case 1 -> addBooking(day,timeslotId);
                case 2 -> {
                    System.out.println("Returning to the main menu");
                    runMainMenu();
                }
                default -> {System.out.print("This is not a valid choice. Please try again!\n");
                    runBookingMenu(day);
                }
            }

        }else {
            editOptionMenu.printMenu();
            System.out.print("Please write your choice here: ");
            int userChoice = editOptionMenu.readChoice();
            switch (userChoice) {
                case 1 -> editBooking(day,timeslotId);
                case 2 -> deleteBooking(day,timeslotId);
                case 3 -> {
                    System.out.println("Returning to the main menu");
                    runMainMenu();
                }
                default -> {
                    System.out.print("This is not a valid choice. Please try again!\n");
                    runBookingMenu(day);
                }
            }
        }

    }

    //Method for accountants
    private void runAccountantMenu(Day day) {
        day.showDay();
        accountantMenu.printMenu();

        System.out.print("Please write your choice here: ");
        int userChoice = accountantMenu.readChoice();

        switch (userChoice) {
            case 1 -> seeBookingDetail(day);
            case 2 -> {System.out.println("Returning to the main menu");
                runMainMenu();
            }
            default -> {System.out.print("This is not a valid choice. Please try again!\n");
                runAccountantMenu(day);
            }
        }
    }

    private int chooseTimeSlot() {
        int timeslotId = 0;
        while (timeslotId < 1 || timeslotId > 8) { // if timeslot is before 10 or after 17 then keep trying
            int chosenTimeslot = in.nextInt();
            in.nextLine(); //Scanner bug

            switch (chosenTimeslot) {
                case 10 -> timeslotId = 1;
                case 11 -> timeslotId = 2;
                case 12 -> timeslotId = 3;
                case 13 -> timeslotId = 4;
                case 14 -> timeslotId = 5;
                case 15 -> timeslotId = 6;
                case 16 -> timeslotId = 7;
                case 17 -> timeslotId = 8;
                default -> System.out.print("This is not a valid time slot. Please enter another time:");
            }
        }
        return timeslotId;
    }

    private void addBooking(Day day, int timeSlotId) {
        day.addBookingToTimeSlot(timeSlotId);

        System.out.println("Here is the updated day: \n");
        calender.showCalender(day);
        runOpenDayMenu(day);
    }

    private void deleteBooking(Day day, int timeSlotId) {
        day.deleteBookingByTimeSlot(timeSlotId);

        System.out.println("Here is the updated day: \n");
        calender.showCalender(day);
        runOpenDayMenu(day);
    }

    private void seeBookingDetail(Day day) {
        int timeSlotId;

        System.out.print("\nIn what timeslot do you want to see the booking details? Please write here: ");
        timeSlotId = chooseTimeSlot();

        System.out.println(day.getBookings().get(timeSlotId - 1).toString());
    }

    //Method for editing bookings
    private void editBooking(Day day, int timeSlotId) {
        boolean checkBooking;
        checkBooking = day.checkBookingInEditBooking(day, timeSlotId);

        if (checkBooking) {
            editBookingMenu.printMenu();
            System.out.print("Please write your choice here: ");
            int userChoice;

            try {
                userChoice = editBookingMenu.readChoice();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice.");
                in.nextLine(); // Clear the input buffer
                editBooking(day, timeSlotId);
                return;
            }

            switch (userChoice) {
                case 1 -> editProducts(day, timeSlotId);
                case 2 -> day.editCustomerNameByTimeSlot(day, timeSlotId);
                case 3 -> day.editHaircutPriceByTimeSlot(day, timeSlotId);
                case 4 -> {System.out.println("Returning to the main menu");
                    runMainMenu();
                }
                default -> {
                    System.out.print("This is not a valid choice. Please try again!");
                    editBooking(day, timeSlotId);
                }
            }
        } else {
            System.out.print("You will be redirected to the previous menu\n");
            sendToFutureMenu(day);
        } sendToFutureMenu(day);
    }

    private void editProducts(Day day, int timeSlotId) {
        editProductsMenu.printMenu();
        System.out.print("Please write your choice here: ");
        int userChoice = editProductsMenu.readChoice();

        switch (userChoice) {
            case 1 -> {day.chooseProductToAddToBooking(day.getBookings().get(timeSlotId - 1));
                System.out.print("The product list has been updated. Here are the new booking details: ");
                System.out.println(day.getBookings().get(timeSlotId - 1).toString());
            }
            case 2 -> {day.chooseProductToRemoveFromBooking(day.getBookings().get(timeSlotId - 1));
                System.out.print("The product list has been updated. Here are the new booking details: ");
                System.out.println(day.getBookings().get(timeSlotId - 1).toString());
            }
            case 3 -> {System.out.println("Returning to the main menu");
                runMainMenu();
            }
            default -> {System.out.print("This is not a valid choice. Please try again!");
                editProducts(day, timeSlotId);
            }
        }
    }

    private void registerHolidays() {
        System.out.println("Please enter the start date for the closed period:");
        Day startDate = enterDate();
        System.out.println("Please enter the end date for the closed period");
        Day endDate = enterDate();
        calender.registerHolidays(startDate, endDate);
        System.out.println("Closed period registered: " + startDate.toString() + " - " + endDate.toString());
    }

    public void addHardcodedDay() {
        Day hardcodedDay = new Day(03, 03, 2020);
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
    }

    //For the addHardcodedDay
    public ArrayList<Product> randomProducts() {
        ArrayList<Product> randomProducts = new ArrayList<>();
        for (int i = 0; i < (int) (Math.random() * 3) + 1; i++) {
            randomProducts.add(ProductBuilder.getProducts().get((int) (Math.random() * 5) + 1));
        }
        return randomProducts;
    }

    private void closeProgram() {// code was used a lot so made a method.
        System.out.println("Goodbye for now!");
        systemRunning = false;
    }

}