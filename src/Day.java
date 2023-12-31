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
    private Scanner userInput = new Scanner(System.in);
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

    public void initializeBookings() {
        for (int i = 1; i < 9; i++) {
            bookings.add(new Booking(i, this));
        }
    }

    public void addBooking(Booking booking, int timeslotID) {
        if (timeslotID >= 1 && timeslotID <= 8) {
            int index = timeslotID - 1; // Adjust for 0-based index
            if (bookings.size() > index) {
                bookings.set(index, booking); // Replace the existing booking at the time slot
            } else {
                bookings.add(booking); // Add a new booking if the index doesn't exist yet
            }
        }
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getYear() {
        return date.getYear();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setHoliday(boolean choice) { //find a better name
        this.holiday = choice;
    }

    public boolean getHoliday() {
        return holiday;
    }

    public boolean getWeekend() {
        return weekend;
    }

    public void closeDay() {
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
        int arrayId = timeslotId - 1;

        if (timeslotId >= 1 && timeslotId <= 8) {
            ArrayList<Booking> currentBookings = this.getBookings();

            System.out.println("Booking " + currentBookings.get(arrayId).getDay().toString() + ": " + currentBookings.get(arrayId).getTimeSlot());
            System.out.print("What is the name of the customer: ");
            customerName = userInput.nextLine();
            currentBookings.get(arrayId).getCustomer().setName(customerName);

            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.print("What is the price of the haircut: ");
                    haircutPrice = userInput.nextInt();
                    validInput = true; // Input is valid, exit the loop
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    userInput.next(); // Clear the invalid input
                }
            }
            currentBookings.get(arrayId).setHaircutPrice(haircutPrice);

            System.out.println();
            System.out.println("This is your booking:");
            System.out.println(this.getBookings().get(arrayId).toString());
            System.out.println("Thank you for adding a booking.");

        } else {
            System.out.println("This is not a valid time slot. Try again.");
        }
    }

    public void editCustomerNameByTimeSlot(Day day, int id) {
        if (id >= 1 && id <= 8) {
            ArrayList<Booking> currentBookings = this.getBookings();
            Booking bookingToEdit = currentBookings.get(id - 1);
            if (bookingToEdit.getCustomerName() != null) {
                System.out.print("Please write the name you want to change to: ");
                String newName = userInput.nextLine();
                bookingToEdit.setCustomerName(newName);
                System.out.println("The name has been updated. Here are the new booking details: ");
                System.out.println(day.getBookings().get(id - 1).toString());
            } else {
                System.out.println("It seems that there is no booking in this time slot. Please look at\n " +
                        "the updated day and see if you meant another time slot\n");
            }
        } else {
            System.out.print("This is not a valid time slot. Please try again");
        }
    }

    public void editHaircutPriceByTimeSlot(Day day, int id) {
        if (id >= 1 && id <= 8) {
            ArrayList<Booking> currentBookings = this.getBookings();
            Booking bookingToEdit = currentBookings.get(id - 1);
            if (bookingToEdit.getHaircutPrice() != 0) {
                System.out.print("Please write the new haircut price here: ");
                double newPrice = userInput.nextInt();
                userInput.nextLine(); //Scanner bug
                bookingToEdit.setHaircutPrice(newPrice);
                System.out.print("The haircut price has been updated. Here are the new booking details: ");
                System.out.println(day.getBookings().get(id - 1).toString());
            } else {
                System.out.println("It seems that there is no booking in this time slot. Please look at\n " +
                        "the updated day and see if you meant another time slot\n");
            }
        } else {
            System.out.print("This is not a valid time slot. Please try again");
        }
    }

    public boolean checkBookingInEditBooking(Day day, int id) {
        if (id >= 1 && id <= 8) {
            ArrayList<Booking> currentBookings = this.getBookings();
            Booking booking = currentBookings.get(id - 1);
            Customer customer = booking.getCustomer();

            if (customer.getName() != null) {
                System.out.println("Here are the current booking details: ");
                System.out.println(day.getBookings().get(id - 1).toString());
                return true;
            } else {
                System.out.println("No booking has been found in this timeslot");
                return false;
            }
        } else {
            System.out.print("This is not a valid time slot. Try again");
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
            } else {
                System.out.println("No booking has been found in this timeslot\n");
            }
        } else {
            System.out.print("This is not a valid time slot. Try again");
        }
    }

    public void chooseProductToAddToBooking(Booking booking) {

        int chosenProductId;
        String userChoice;
        ArrayList<Product> products = booking.getProducts();

        displayAvailableProducts();
        System.out.print("What product do you want to add to the booking? Please write here: ");
        chosenProductId = userInput.nextInt();

        addProductsByID(chosenProductId, products);
        displayBookingProducts(products);
        userInput.nextLine();
        System.out.print("Would you like to add more products to the list? y/n: ");
        userChoice = userInput.nextLine();
        if (userChoice.equalsIgnoreCase("y")) {
            chooseProductToAddToBooking(booking);
        }
    }

    public void addProductsByID(int chosenProductID, ArrayList<Product> products) {
        switch (chosenProductID) {
            case 1 -> products.add(new Product(availableProducts.get(0).getId(), availableProducts.get(0).getName(), availableProducts.get(0).getPrice()));
            case 2 -> products.add(new Product(availableProducts.get(1).getId(), availableProducts.get(1).getName(), availableProducts.get(1).getPrice()));
            case 3 -> products.add(new Product(availableProducts.get(2).getId(), availableProducts.get(2).getName(), availableProducts.get(2).getPrice()));
            case 4 -> products.add(new Product(availableProducts.get(3).getId(), availableProducts.get(3).getName(), availableProducts.get(3).getPrice()));
            case 5 -> products.add(new Product(availableProducts.get(4).getId(), availableProducts.get(4).getName(), availableProducts.get(4).getPrice()));
            case 6 -> products.add(new Product(availableProducts.get(5).getId(), availableProducts.get(5).getName(), availableProducts.get(5).getPrice()));
            default -> System.out.println("Illegal choice. No products added.\nGo to edit booking menu.");
        }
    }



    private void displayBookingProducts(ArrayList<Product> products) {
        products = updateProductsId(products);

        System.out.println("The booking now has " + products.size() + " products.");
        for (Product product : products) {
            System.out.println(product.getId() + ": " + product.getName());
        }
    }

    private void displayAvailableProducts() {
        System.out.println("Here is a list of all the products available with their id number:");
        for (Product product : availableProducts) {
            System.out.println(product.getId() + ": " + product.getName());
        }
    }

    public void chooseProductToRemoveFromBooking(Booking booking) {
        int chosenProductId;
        String userChoice;
        ArrayList<Product> products = updateProductsId(booking.getProducts());


        displayBookingProducts(products);
        System.out.print("What product do you want to delete from the booking? Please write here: ");
        chosenProductId = userInput.nextInt();
        removeProductById(chosenProductId, products);
        displayBookingProducts(products);
        userInput.nextLine(); //Scanner bug
        System.out.print("Would you like to remove more products to the list? y/n: ");
        userChoice = userInput.nextLine();
        if (userChoice.equalsIgnoreCase("y")) {
            chooseProductToRemoveFromBooking(booking);
        }
    }

    private ArrayList<Product> updateProductsId(ArrayList<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            products.get(i).setId(i+1);
        }
        return products;
    }


    private void removeProductById(int chosenProductID, ArrayList<Product> products) {
        products = updateProductsId(products);
        if (!products.isEmpty()) {
            if (products.removeIf(product -> chosenProductID == product.getId())) {
                products.removeIf(product -> chosenProductID == product.getId());
            } else {
                System.out.println("No product by that id to remove.");
            }
        } else {
            System.out.println("You can't remove a product if there is not products in the list.");
        }
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    public String[] buildOpenDayMessage() {
        String[] dayCalender = new String[bookings.size()];

            /*Booking booking = bookings.get(i);
            String customerName = booking.getCustomer().getName();
            Customer customer = booking.getCustomer();

            if (customerName == null || customer == null) { //booking has no customer yet
                dayCalender[i] = (i + 10 + ": Available  ");
            } else {
                dayCalender[i] = (i + 10 + ": Booked     ");
            }*/

        for (int i = 0; i < dayCalender.length; i++) {
            Booking booking = bookings.get(i);

            if (booking == null) {
                dayCalender[i] = (i + 10 + ": Available  ");
            } else {
                Customer customer = booking.getCustomer();
                if (customer == null) {
                    dayCalender[i] = (i + 10 + ": Available  ");
                } else {
                    if (customer.getName() == null || "null".equals(customer.getName())) {
                        dayCalender[i] = (i + 10 + ": Available  ");
                    } else {dayCalender[i] = (i + 10 + ": Booked     ");}
                }
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
        if (this.date != null) {      // makes sure that it doesn't crash if the date is impossible
            System.out.println("     " + date.getDayOfWeek().toString());
            System.out.println("    " + this.toString());
            System.out.println("------------------");

            String[] dayCalendar = buildDayCalender();
            for (String bookingInfo : dayCalendar) {
                System.out.println("   " + bookingInfo);
            }
            System.out.println("------------------\n");
        }
    }

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
