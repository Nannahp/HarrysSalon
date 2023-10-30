import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Day {
    private LocalDate date;
    private ArrayList<Booking> bookings = new ArrayList<Booking>(8);

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

    public void addBookingToTimeSlot(int id) {
        System.out.println("Adding booking to timeslot");
        Scanner userInput = new Scanner(System.in);
        String customerName;
        double haircutPrice;

        if (id >= 1 && id <= 8) {
            ArrayList<Booking> currentBookings = this.getBookings();

            System.out.println("Booking " + currentBookings.get(id - 1).getDay().toString() + ": " + currentBookings.get(id - 1).getTimeSlot());
            System.out.print("What is the name of the customer: ");
            customerName = userInput.nextLine();
            currentBookings.get(id - 1).getCustomer().setName(customerName);

            //just for test
            System.out.println(currentBookings.get(id -1).getCustomerName());


            System.out.print("What is the price of the haircut: ");
            haircutPrice = userInput.nextInt();
            currentBookings.get(id - 1).setHaircutPrice(haircutPrice);

            //just for test
            System.out.println(currentBookings.get(id - 1).getHaircutPrice());

            System.out.println("Thank you for adding a booking.");


        } else {
            System.out.println("This is not a valid time slot. Try again.");
        }
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
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
