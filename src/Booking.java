import java.util.ArrayList;
import java.util.Scanner;

public class Booking {
    private int id;
    private String timeSlot;
    private Customer customer;
    private double haircutPrice;
    private double productPrice;
    private double bookingTotal;
    private Day day;
    private ArrayList<Product> products = new ArrayList<Product>();

    public Booking (int id, Day day) {
        this.id = id;
        this.day = day;
        this.customer = new Customer(null);
        this.haircutPrice = 0;
        this.bookingTotal = calcTotal();

        switch (id) {
            case 1 -> this.setTimeSlot("10:00 - 11:00");
            case 2 -> this.setTimeSlot("11:00 - 12:00");
            case 3 -> this.setTimeSlot("12:00 - 13:00");
            case 4 -> this.setTimeSlot("13:00 - 14:00");
            case 5 -> this.setTimeSlot("14:00 - 15:00");
            case 6 -> this.setTimeSlot("15:00 - 16:00");
            case 7 -> this.setTimeSlot("16:00 - 17:00");
            case 8 -> this.setTimeSlot("17:00 - 18:00");
        }
    }

    //!\\ Only for hardcoded day
    public Booking (int id, Day day, Customer customer, double haircutPrice, ArrayList<Product> products) {
        this.id = id;
        this.day = day;
        this.customer = customer;
        this.haircutPrice = haircutPrice;
        this.products = products;
        this.bookingTotal = calcTotal();

        switch (id) {
            case 1 -> this.setTimeSlot("10:00 - 11:00");
            case 2 -> this.setTimeSlot("11:00 - 12:00");
            case 3 -> this.setTimeSlot("12:00 - 13:00");
            case 4 -> this.setTimeSlot("13:00 - 14:00");
            case 5 -> this.setTimeSlot("14:00 - 15:00");
            case 6 -> this.setTimeSlot("15:00 - 16:00");
            case 7 -> this.setTimeSlot("16:00 - 17:00");
            case 8 -> this.setTimeSlot("17:00 - 18:00");
        }
    }


    public String getTimeSlot() {
        return timeSlot;
    }

    private void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCustomerName() {
        return customer.getName();
    }

    public void setCustomerName(String name) {
        customer.setName(name);
    }


    public double getHaircutPrice() {
        return haircutPrice;
    }

    public void setHaircutPrice(double haircutPrice) {
        this.haircutPrice = haircutPrice;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }


    public double calcTotal() {
        try {
            productPrice = 0;
            if (!this.getProducts().isEmpty()) {
                for (Product product : this.getProducts()) {
                    productPrice += product.getPrice();
                }
                bookingTotal = haircutPrice + productPrice;
            } else {
                bookingTotal = haircutPrice;
            }
        } catch (Exception e) {
            System.out.println("There was an error in calculating the total. Try again.");
            bookingTotal = 0; // Set a default value
        }
        return bookingTotal;
    }



    public double getBookingTotal() {
        return bookingTotal;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // String builder to build a string of looped products because you can't loop in a return statement :)
        try {
            for (int i = 0; i < this.getProducts().size(); i++) {
                sb.append(this.getProducts().get(i).getName()).append(": ").append(this.getProducts().get(i).getPrice()).append(",-\n");
            }
        } catch (Exception e) {
            sb.append("Error generating product details");
        }

        String productString = sb.toString();

        // Booking details
        sb = new StringBuilder("Booking ").append(this.getTimeSlot()).append("\n");
        if (this.getCustomer().getName() != null || this.getHaircutPrice() != 0) {
            sb.append("Customer name: ").append(this.getCustomer().getName()).append("\n");
            sb.append("Haircut Price: ").append(this.getHaircutPrice()).append(",-\n");
            if (!this.getProducts().isEmpty()) {
                sb.append("Item/s bought:\n").append(productString);
            }
            try {
                sb.append("Booking total: ").append(this.calcTotal()).append(",-\n");
            } catch (Exception e) {
                sb.append("Error calculating booking total");
            }
        } else {
            sb.append("Customer name: --\n");
            sb.append("Haircut Price: --\n");
        }
        return sb.toString();
    }

}
