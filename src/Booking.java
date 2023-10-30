import java.util.ArrayList;
import java.util.Scanner;

public class Booking {
    private int id;
    private String timeSlot;
    private Customer customer;
    private double haircutPrice;
    private Day day;
    private ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<Product> availableProducts = new ProductBuilder().getProducts();

    public Booking (int id, Day day) {
        this.id = id;
        this.day = day;
        this.customer = new Customer();

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

    public int getId() {
        return id;
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

    public void setCustomer(Customer customer) {
        this.customer = customer;
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


    public void addProductsToBooking() {
        Scanner userInput = new Scanner(System.in);
        int chosenProductId;
        String userChoice;

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
            addProductsToBooking();
        } else {
            System.out.println("Here is your updated booking:");
            //this.displayBooking();
            System.out.println("\n");
        }

    }


    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", timeSlot='" + timeSlot + '\'' +
                ", customer=" + customer.getName() +
                ", haircutPrice=" + haircutPrice +
                ", day=" + day +
                ", products=" + products +
                '}';
    }

    /*public void displayBooking(){
        if (this.getCustomer() == null || this.getHaircutPrice() == 0) {
            System.out.println("Booking " + this.getTimeSlot() + "\nCustomer name: " + "No customer yet" + "\nHaircut Price: " + "No price yet");
        }
        else {
            if (!this.getProducts().isEmpty()) {
                System.out.println("Booking " + this.getTimeSlot() + "\nCustomer name: " +
                        this.getCustomer().getName() + "\nHaircut Price: " + this.getHaircutPrice() +
                        ",-" + "\nHaircut Price: " + this.getHaircutPrice() + ",-" +
                        "\nProduct/s bought:");
                for (Product product: this.products){
                    return product.getName() + ": " + product.getPrice() + ",-";
                });

            } else {
                return "Booking " + this.getTimeSlot() + "\nCustomer name: " + this.getCustomer().getName() + "\nHaircut Price: " + this.getHaircutPrice() + ",-"+ "\nHaircut Price: " + this.getHaircutPrice() + ",-";
            }

        }


    }*/

}
