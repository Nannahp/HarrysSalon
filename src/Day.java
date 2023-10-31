import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Day {
    private LocalDate date;
    private ArrayList<Booking> bookings = new ArrayList<Booking>(8);
    private ArrayList<Product> availableProducts = new ProductBuilder().getProducts();

    public Day(int day, int month, int year) {
        try {
            this.date = LocalDate.of(year, month, day);
            initializeBookings();
        } catch (DateTimeException e) {
            this.date = null;
            System.out.println("Date does not exist. Are you sure you have entered the right date?");

        }
    }

    private void initializeBookings() {
        for (int i = 1; i < 9; i++) {
            bookings.add(new Booking(i, this));
        }
    }

    public void displayBookingList() {
        System.out.println("____" + this.getDate().toString() + " bookings____");
        for (Booking booking : this.getBookings()) {
            System.out.println(booking.toString() + "\n");
        }
        System.out.println("___________________");
    }

    public LocalDate getDate() {
        return date;
    }

    public void addBookingToTimeSlot(int timeslotId) {
        System.out.println("Adding booking to timeslot");
        Scanner userInput = new Scanner(System.in);
        String customerName;
        double haircutPrice;
        String userChoice;
        int arrayId = timeslotId -1;

        if (timeslotId >= 1 && timeslotId <= 8) {
            ArrayList<Booking> currentBookings = this.getBookings();

            System.out.println("Booking " + currentBookings.get(arrayId).getDay().toString() + ": " + currentBookings.get(arrayId).getTimeSlot());
            System.out.print("What is the name of the customer: ");
            customerName = userInput.nextLine();
            currentBookings.get(arrayId).getCustomer().setName(customerName);

            System.out.print("What is the price of the haircut: ");
            haircutPrice = userInput.nextInt();
            currentBookings.get(arrayId).setHaircutPrice(haircutPrice);


            System.out.println("Do you want to add products to the booking? y/n");
            userInput.nextLine(); // scanner bug
            userChoice = userInput.nextLine();
            if (userChoice.equalsIgnoreCase("y")) {
                addProductsToBooking(currentBookings.get(arrayId));
            } else {
                System.out.println("No products added to the booking.");
            }

            System.out.println();
            System.out.println("This is your booking:");
            System.out.println(this.getBookings().get(arrayId).toString());

            System.out.println("Thank you for adding a booking.");


        } else {
            System.out.println("This is not a valid time slot. Try again.");
        }
    }

    public void addProductsToBooking(Booking booking) {
        Scanner userInput = new Scanner(System.in);
        int chosenProductId;
        String userChoice;
        ArrayList<Product> products = booking.getProducts();

        System.out.println("Here is a list of all the products available:");
        for (Product product : availableProducts) {
            System.out.println(product.getId() + ": " + product.getName());
        }
        System.out.println("What product do you want to add to the booking? (Type product id(number)");
        chosenProductId = userInput.nextInt();

        switch (chosenProductId) {
            case 1 -> products.add(availableProducts.get(0));
            case 2 -> products.add(availableProducts.get(1));
            case 3 -> products.add(availableProducts.get(2));
            case 4 -> products.add(availableProducts.get(3));
            case 5 -> products.add(availableProducts.get(4));
            case 6 -> products.add(availableProducts.get(5));
            default -> System.out.println("Illegal choice. No products added.\nGo to edit booking menu.");
        }

        System.out.println("The booking has now " + products.size()+ " products.");
        for (Product product : products) {
            System.out.println(product.getName());
        }
        userInput.nextLine();
        System.out.println("Add more? y/n");
        userChoice = userInput.nextLine();
        if (userChoice.equalsIgnoreCase("y")) {
            addProductsToBooking(booking);
        }

    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }


    public void showDay() {
        if (this.date !=null){      // makes sure that it doesn't crash if the date is impossible
        System.out.println(date.getDayOfWeek().toString());
        System.out.println(this.toString());
        for (int i = 0; i < bookings.size(); i++) {
            System.out.print((i + 10));
            String customerName=bookings.get(i).getCustomer().getName();
            if ( customerName== null) { //booking has no customer yet
                System.out.println(": Available");
            } else {
                System.out.println(": Booked");
            }
        }}
        System.out.println(" ");
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
