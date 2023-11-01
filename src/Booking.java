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

/*

    public int getId() {
        return id;
    }
*/

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

/*    public void setCustomer(Customer customer) {
        this.customer = customer;
    }*/

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

/*    public double calcProductPrice(){
            return productPrice;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

*/

    public double calcTotal() {
        productPrice = 0;
        if (!this.getProducts().isEmpty()) {
            for (Product product : this.getProducts()) {
                productPrice += product.getPrice();
            }
            bookingTotal = haircutPrice + productPrice;
        } else {
            bookingTotal = haircutPrice;
        }
         return bookingTotal;
    }


    public double getBookingTotal() {
        return bookingTotal;
    }



    @Override
    public String toString() {

        // String builder to build a string of looped products because you can't loop in a return statement :)
        StringBuilder sb = new StringBuilder("\nItem/s bought:\n");

        for (int i = 0; i < this.getProducts().size(); i++) {
            sb.append(this.getProducts().get(i).getName()).append(": ").append(this.getProducts().get(i).getPrice()).append(",-\n");
        }
        String str = sb.toString();

        //Nested if

        //If booking ISN'T empty (customer NOT null and/or price NOT 0 )
        if (this.getCustomer() != null || this.getHaircutPrice() != 0) {
            // then check if bookings products arrayList is empty
            if (!this.getProducts().isEmpty()) {
                //IF NOT display this
                return "Booking " + this.getTimeSlot() + "\nCustomer name: " + this.getCustomer().getName() + "\nHaircut Price: " + this.getHaircutPrice() + ",-" + str + "Booking total: " + this.calcTotal() + ",-\n";
            } else {
                // if YES display this
                return "Booking " + this.getTimeSlot() + "\nCustomer name: " + this.getCustomer().getName() + "\nHaircut Price: " + this.getHaircutPrice() + ",-" + "\nBooking total: " + this.calcTotal() + ",-\n";
            }
            //If booking IS empty (customer null and/or price = 0 )
        } else {
            // then display this
            return "Booking " + this.getTimeSlot() + "\nCustomer name: " + "No customer yet" + "\nHaircut Price: " + "No price yet";
        }
    }
}
