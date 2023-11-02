import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Day {
    private LocalDate date;
    private ArrayList<Booking> bookings = new ArrayList<Booking>(8);
    private ArrayList<Product> availableProducts = new ProductBuilder().getProducts();
    private Scanner in = new Scanner(System.in);
    private boolean holiday;
    private boolean weekend;

    public Day(int day, int month, int year) {
        try {
            this.date = LocalDate.of(year, month, day);
            initializeBookings();
            registerClosedDay(); // if it's a weekend then it closes the bookings
        } catch (DateTimeException e) {
            this.date = null;
            System.out.println("This Date does not exist. Try again with a new date.");
        }
    }

    private void initializeBookings() {
        for (int i = 1; i < 9; i++) {
            bookings.add(new Booking(i, this));
        }
    }

  public int getDay(){
        return date.getDayOfMonth();
    }
    public int getMonth(){
        return date.getMonthValue();
    }
    public int getYear(){
        return date.getYear();
    }

    public LocalDate getDate() {
        return date;
    }
    public void setHoliday(boolean choice){ //find a better name
        this.holiday = choice;
    }
    public boolean getHoliday(){
        return holiday;
    }

    public boolean getWeekend(){
        return weekend;
    }

    public void closeDay(){
        this.bookings.clear();
    }


    public String[] buildClosedMessage() {                   //booking listen er 0 for lukkede dage, derfor kan den ikke bruges
        String[] closedMessage = new String[8];              //til at definerer størrelsen på beskeden. Kan give fejl i fremtiden,
        for (int i = 0; i < closedMessage.length; i++) {     //hvis størrelsen på listen ændres, fx med flere tider.
            closedMessage[i] = i == 3 ? "      CLOSED     " : "               ";
        }
        return closedMessage;
    }
    public String[] buildHolidayMessage() {
        String[] holidayMessage = new String[8];
        for (int i = 0; i < holidayMessage.length; i++) {
            holidayMessage[i] = (i == 3) ? "     CLOSED     " : ((i == 4) ? " FOR HOLIDAY  " : "               ");
        }
        return holidayMessage;
    }

    public void registerClosedDay() {
        if (date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY) {
            closeDay();
            weekend = true;
        }
    }

    public void addBookingToTimeSlot(int timeslotId) {
        System.out.println("Adding booking to timeslot");
        String customerName;
        double haircutPrice = 0;
        int arrayId = timeslotId -1;

        if (timeslotId >= 1 && timeslotId <= 8) {
            ArrayList<Booking> currentBookings = this.getBookings();

            System.out.println("Booking " + currentBookings.get(arrayId).getDay().toString() + ": " + currentBookings.get(arrayId).getTimeSlot());
            System.out.print("What is the name of the customer: ");
            customerName = in.nextLine();
            currentBookings.get(arrayId).getCustomer().setName(customerName);

            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.print("What is the price of the haircut: ");
                    haircutPrice = in.nextInt();
                    validInput = true; // Input is valid, exit the loop
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    in.next(); // Clear the invalid input
                }
            }
            currentBookings.get(arrayId).setHaircutPrice(haircutPrice);

            System.out.println();
            System.out.println("This is your booking:");
            System.out.println(this.getBookings().get(arrayId).toString());
            System.out.println("Thank you for adding a booking.");

        } else {System.out.println("This is not a valid time slot. Try again.");}
    }

    public void editCustomerNameByTimeSlot(Day day, int id) {
        if (id >= 1 && id <= 8) {
            ArrayList<Booking> currentBookings = this.getBookings();
            Booking bookingToEdit = currentBookings.get(id - 1);
            if (bookingToEdit.getCustomerName() != null) {
                System.out.print("Please write the name you want to change to: ");
                String newName = in.nextLine();
                bookingToEdit.setCustomerName(newName);
                System.out.println("The name has been updated. Here are the new booking details: ");
                System.out.println(day.getBookings().get(id-1).toString());
            } else {System.out.println("It seems that there is no booking in this time slot. Please look at\n " +
                        "the updated day and see if you meant another time slot\n");
            }
        } else {System.out.print("This is not a valid time slot. Please try again");}
    }

    public void editHaircutPriceByTimeSlot(Day day, int id) {
        if (id >= 1 && id <= 8) {
            ArrayList<Booking> currentBookings = this.getBookings();
            Booking bookingToEdit = currentBookings.get(id - 1);
            if (bookingToEdit.getHaircutPrice() != 0) {
                System.out.print("Please write the new haircut price here: ");
                double newPrice = in.nextInt();
                in.nextLine(); //Scanner bug
                bookingToEdit.setHaircutPrice(newPrice);
                System.out.print("The haircut price has been updated. Here are the new booking details: ");
                System.out.println(day.getBookings().get(id-1).toString());
            } else {
                System.out.println("It seems that there is no booking in this time slot. Please look at\n " +
                        "the updated day and see if you meant another time slot\n");
            }
        } else {System.out.print("This is not a valid time slot. Please try again");}
    }

    public boolean checkBookingInEditBooking(Day day, int id) {
        if (id >= 1 && id <= 8) {
            ArrayList<Booking> currentBookings = this.getBookings();
            Booking booking = currentBookings.get(id - 1);
            Customer customer = booking.getCustomer();

            if (customer.getName() != null) {
                System.out.println("Here are the current booking details: ");
                System.out.println(day.getBookings().get(id-1).toString());
                return true;
            } else {System.out.println("No booking has been found in this timeslot");
                return false;
            }
        } else {System.out.print("This is not a valid time slot. Try again");
            checkBookingInEditBooking(day, id);
            return true;
        }
    }

    public void deleteBookingByTimeSlot(int id) {
        if (id >= 1 && id <= 8) {
            ArrayList<Booking> currentBookings = this.getBookings();
            Booking deletedBooking = currentBookings.get(id - 1);
            Customer deletedCustomer = deletedBooking.getCustomer();

            if (deletedCustomer.getName() != null) { //Check if the given timeslot is booked
                System.out.println("Deleting booking for " + deletedBooking.getDay().toString() + ": " +
                        deletedBooking.getTimeSlot());

                deletedCustomer.setName(null);
                currentBookings.get(id - 1).setHaircutPrice(0);
                currentBookings.get(id - 1).getProducts().clear();
                System.out.println("The booking has been deleted");
            } else {System.out.println("No booking has been found in this timeslot\n");}
        } else {System.out.print("This is not a valid time slot. Try again");}
    }

    public void addProductsToBooking(Booking booking) {
        Scanner userInput = new Scanner(System.in);
        int chosenProductId;
        String userChoice;
        ArrayList<Product> products = booking.getProducts();

        listOfAvailableProducts();
        System.out.print("What product do you want to add to the booking? Please write here: ");
        chosenProductId = userInput.nextInt();

        addProductsByID(chosenProductId, products);
        remainingBookingProducts(products);
        userInput.nextLine();
        System.out.print("Would you like to add more products to the list? y/n: ");
        userChoice = userInput.nextLine();
        if (userChoice.equalsIgnoreCase("y")) {
            addProductsToBooking(booking);
        }
    }

    private void addProductsByID(int chosenProductID, ArrayList<Product> products) {
        switch (chosenProductID) {
            case 1 -> products.add(availableProducts.get(0));
            case 2 -> products.add(availableProducts.get(1));
            case 3 -> products.add(availableProducts.get(2));
            case 4 -> products.add(availableProducts.get(3));
            case 5 -> products.add(availableProducts.get(4));
            case 6 -> products.add(availableProducts.get(5));
            default -> System.out.println("Illegal choice. No products added.\nGo to edit booking menu.");
        }
    }

    private void remainingBookingProducts(ArrayList<Product> products) {
        System.out.println("The booking now has " + products.size() + " products.");
        for (Product product : products) {
            System.out.println(product.getName());
        }
    }

    private void listOfAvailableProducts() {
        System.out.println("Here is a list of all the products available with their id number:");
        for (Product product : availableProducts) {
            System.out.println(product.getId() + ": " + product.getName());
        }
    }

    public void deleteProductsFromBooking(Booking booking) {
        int chosenProductId;
        String userChoice;
        ArrayList<Product> products = booking.getProducts();

        listOfAvailableProducts();
        System.out.print("What product do you want to delete from the booking? Please write here: ");
        chosenProductId = in.nextInt();
        in.nextLine(); //Scanner bug

            for (Product product : availableProducts) {
                if (chosenProductId == product.getId() && chosenProductId >= 1 && chosenProductId <= 8) {
                    if (products.contains(product)) {
                        products.remove(product);
                        remainingBookingProducts(products);
                        if (products.isEmpty()) {
                            System.out.println("You will be redirected to main menu as there are no more products left");
                        } else {System.out.print("Would you like to delete another products from the list? y/n: ");
                        userChoice = in.nextLine();
                            if (userChoice.equalsIgnoreCase("y")) {
                                deleteProductsFromBooking(booking);
                            }
                        }
                    } else {System.out.println("This product is not available in this booking. Please try again!");
                        deleteProductsFromBooking(booking);
                    }
                } else {System.out.println("This is not a valid ID. Please try again!");
                    deleteProductsFromBooking(booking);
            }
        }
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    public String[] buildOpenDayMessage(){
        String[] dayCalender = new String[bookings.size()];

        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            String customerName=booking.getCustomer().getName();

            if ( customerName== null) { //booking has no customer yet
                dayCalender[i] = ( i+10 + ": Available  ");
            } else {dayCalender[i] = (i + 10 +": Booked     ");
            }
        }
        return dayCalender;
    }

    public String[] buildDayCalender() {
        if (this.weekend) {
            return buildClosedMessage();
        } else if (this.holiday) {
            return buildHolidayMessage();
        } else return buildOpenDayMessage();
    }

    //Had to make a new method to show the 4 days together.
    public void showDay() {
        if (this.date !=null){      // makes sure that it doesn't crash if the date is impossible
        System.out.println("     "+ date.getDayOfWeek().toString());
        System.out.println("    "+ this.toString());
        System.out.println("------------------");

        String[] dayCalendar = buildDayCalender();
        for(String bookingInfo: dayCalendar){
            System.out.println("   " + bookingInfo);
        }
        System.out.println("------------------\n");
    }}

    @Override
    public String toString() {
        return date.getDayOfMonth() + "-" + date.getMonthValue() + "-" + date.getYear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        return Objects.equals(date, day.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
