import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class BookingSystem {
    private Scanner in = new Scanner(System.in);
    Calender calender = new Calender("Harry's calender");
    boolean systemRunning = true;
    private boolean isBeforeToday;
    public static void main(String[] args) {
        new BookingSystem().run();
    }

    public void run() {
        //addHardcodedDay();
        showIntroMessage();
        //runLogin();
        while(systemRunning){runFirstMenu();}
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
    private void runFirstMenu() {
        Menu menu = new Menu("You have the following choices: ", new String[] {
                "1. Search for a date",
                "2. Register holidays",
                "3. Quit the program"
        });

        menu.printMenu();
        System.out.print("Please write your choice here: ");

        int userChoice = menu.readChoice();

        switch (userChoice) {
            case 1 -> sendToCorrectMenu(enterDate());
            case 2 -> registerHolidays();
            case 3 -> closeProgram();
            default -> System.out.println("Illegal choice. Please try again:\n"); //enhanced switch, to avoid break
        }
    }

    //Method that asks for a date and creates a day out of it in calender
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

    private boolean isDateBeforeToday(Day day) {
        isBeforeToday = day.getDate().isBefore(LocalDate.now());
        return isBeforeToday;
    }

    private void sendToCorrectMenu(Day day) {
            isBeforeToday = isDateBeforeToday(day);
            if (isBeforeToday) {
                // Accountant menu
                sendToPastMenu(day);
            } else {
                sendToFutureMenu(day);
            }

    }
    private void sendToPastMenu(Day day){
        System.out.println("The date you have entered is before today!");
        calender.addHardcodedDay(day, calender);
        runAccountantMenu(day);
    }
    private void sendToFutureMenu(Day day){
        calender.showCalender(day);
        if (day.getWeekend()|| day.getHoliday()){
            runClosedMenu(day);}
        else {runBookingMenu(day);

        }

    }

    private void runClosedMenu(Day day){
        System.out.println("\nYou are currently on "+ day.toString());
        System.out.println("Current date is closed");
        Menu menu = new Menu("Now you have the following choices: ", new String[]{
                "1. Go to one of the following dates",
                "2. Go back to main menu"
        });
        menu.printMenu();
        System.out.print("Please write your choice here: ");
        int userChoice = menu.readChoice();
        switch (userChoice) {
            case 1 -> goToAnotherDate(day);
            case 2 -> {
                System.out.println("Returning to main menu");
                runFirstMenu();
            }
            default -> {
                System.out.print("This is not a valid choice. Please try again!\n");
                runClosedMenu(day);
            }

    }
    }
    private void goToAnotherDate(Day day){
        Day day1 = calender.searchForDate(day.getDay()+1, day.getMonth(), day.getYear());
        Day day2 = calender.searchForDate(day.getDay()+2, day.getMonth(), day.getYear());
        Day day3 = calender.searchForDate(day.getDay()+3, day.getMonth(), day.getYear());
        Day day4 = calender.searchForDate(day.getDay()+4, day.getMonth(), day.getYear());
        Menu menu = new Menu("Which date would you like to go to: ", new String[]{
                "1. " + day1.toString(),
                "2. " + day2.toString(),
                "3. " + day3.toString(),
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
    //Method after selected date to either add, delete or edit bookings
    private void runBookingMenu(Day day) {
        System.out.println("\nYou are currently on "+ day.toString());
            Menu menu = new Menu("Now you have the following choices: ", new String[]{
                    "1. Add a booking",
                    "2. Delete a booking",
                    "3. Edit a booking",
                    "4. Go to one of the following dates",
                    "5. Go back to main menu"
            });
        menu.printMenu();
        System.out.print("Please write your choice here: ");
        int userChoice = menu.readChoice();

        switch (userChoice) {
            case 1 -> addBooking(day);
            case 2 -> deleteBooking(day);
            case 3 -> editBooking(day);
            case 4 -> goToAnotherDate(day);
            case 5 -> {
                System.out.println("Returning to main menu");
                runFirstMenu();
            }
            default -> {
                System.out.print("This is not a valid choice. Please try again!\n");
                runBookingMenu(day);
            }
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
            case 2 -> {
                System.out.println("Returning to main menu");
                runFirstMenu();
            }
            default -> {
                System.out.print("This is not a valid choice. Please try again!\n");
                runAccountantMenu(day);
            }
        }
    }

    private int chooseTimeSlot() {
        int timeslotId = 0;
        while(timeslotId < 1 || timeslotId >8){ // if timeslot is before 10 or after 17 then keep trying
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
            default -> System.out.println("This is not a valid time slot. Please enter another time:");
        }}
        return timeslotId;
    }

    private void addBooking(Day day) {
        int timeslotId;

        System.out.print("\nIn what time slot do you want add a booking? Please write here: ");
        timeslotId = chooseTimeSlot();
        day.addBookingToTimeSlot(timeslotId);

        System.out.println("Here is the updated day: \n");
        calender.showCalender(day);
        runBookingMenu(day);
    }

    private void deleteBooking(Day day) {
        int timeslotId;

        System.out.print("\nIn what time slot do you want to delete a booking? Please write here: ");
        timeslotId = chooseTimeSlot();
        day.deleteBookingByTimeSlot(timeslotId);

        System.out.println("Here is the updated day: \n");
        calender.showCalender(day);
        runBookingMenu(day);
    }

    private void seeBookingDetail(Day day) {
        int timeSlotId;

        System.out.print("\nIn what timeslot do you want to see the booking details? Please write here: ");
        timeSlotId = chooseTimeSlot();

        System.out.println(day.getBookings().get(timeSlotId-1).toString());
    }

    //Menu for editing bookings
    private void editBooking(Day day) {
        Menu menu = new Menu("Would you like to: ", new String[] {
                "1. Edit product list",
                "2. Edit customer name",
                "3. Edit haircut price",
                "4. Go back to main menu"
                //"4. Change payment method"
        });

        menu.printMenu();
        System.out.print("Please write your choice here: ");

        int userChoice = menu.readChoice();

        switch (userChoice) {
            case 1 -> editProductList(day);
            case 2 -> editCustomerName(day);
            case 3 -> editHaircutPrice(day);
            case 4 -> {
                System.out.println("Returning to main menu");
                runFirstMenu();
            }
            default -> {
                System.out.print("This is not a valid choice. Please try again! ");
                editBooking(day);
            }
        }
    }

    private void editProductList(Day day) {
        int timeSlotId;

        System.out.print("\nIn what time slot do you want to edit the product list? Please write here: ");
        timeSlotId = chooseTimeSlot();
        day.checkBookingInEditBooking(day, timeSlotId);

        Menu menu = new Menu("\nWould you like to: ", new String[] {
                "1. Add a product to the list",
                "2. Remove a product from the list",
                "3. Go back to main menu"
        });

        menu.printMenu();
        System.out.print("Please write your choice here: ");

        int userChoice = menu.readChoice();

        switch (userChoice) {
            case 1 -> {
                day.addProductsToBooking(day.getBookings().get(timeSlotId-1));
                System.out.print("The product list has been updated. Here are the new booking details: ");
                System.out.println(day.getBookings().get(timeSlotId-1).toString());
            }
            case 2 -> {
                day.deleteProductsFromBooking(day.getBookings().get(timeSlotId-1));
                System.out.print("The product list has been updated. Here are the new booking details: ");
                System.out.println(day.getBookings().get(timeSlotId-1).toString());
            }
            case 3 -> {
                System.out.println("Returning to main menu");
                runFirstMenu();
            }
            default -> {
                System.out.print("This is not a valid choice. Please try again! ");
                editProductList(day);
            }
        }
    }

    private void registerHolidays(){
        System.out.println("Please enter the start date for the closed period:");
        Day startDate = enterDate();
        System.out.println("Please enter the end date for the closed period");
        Day endDate = enterDate();
        calender.registerHolidays(startDate,endDate);
        System.out.println("Closed period registered: " + startDate.toString() + " - " +endDate.toString());
    }

    private void editCustomerName(Day day) {
        int timeSlotId;

        System.out.print("\nIn what time slot do you want to edit the name? Please write here: ");
        timeSlotId = chooseTimeSlot();

        day.editCustomerNameByTimeSlot(day,timeSlotId);
        //System.out.println("Here is the updated day: \n");
        //day.showDay();
    }

    private void editHaircutPrice(Day day) {
        int timeSlotId;

        System.out.print("\nIn what time slot do you want to see the haircut price? Please write here: ");

        timeSlotId = chooseTimeSlot();

        day.editHaircutPriceByTimeSlot(day, timeSlotId);
        //day.displayBookingList();
        //System.out.println("Here is the updated day: \n");
        //runBookingMenu(day);
    }

    private void closeProgram(){// code was used a lot so made a method.
        System.out.println("Goodbye for now!");
        systemRunning = false;
    }
}