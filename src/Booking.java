public class Booking {
    private int id;
    private String timeSlot;
    private Customer customer;
    private double haircutPrice;
    private Day day;

    public Booking (int id, Day day) {
        this.id = id;
        this.day = day;
        this.customer = new Customer(null);
        this.haircutPrice = 0;

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

    // TODO:
    // TEST Booking


    // tror ikke at der er brug for den, fordi vi laver instancer af booking når vi laver
    // en arraylist<Booking> i Day, derfor skal dage kun oprettes med et id, som vil lave en
    // setTimeslot i forhold til id'et, og senere når Harry skal oprette en Booking er han jo
    // på en day som allerede har den array med bookings allerede initialiseret med en Day og en id,
    // derfor skal man kun setCustomer, setHaircutPrice

 /*   public Booking (int id, Customer customer, double haircutPrice, Day day) {
        this.id = id;

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

        this.customer = customer;
        this.haircutPrice = haircutPrice;
        this.day = day;
    }*/

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

    @Override
    public String toString() {
        if (this.getCustomer() == null || this.getHaircutPrice() == 0) {
            return "Booking " + this.getTimeSlot() + "\nCustomer name: " + "No customer yet" + "\nHaircut Price: " + "No price yet";
        } else {
            return "Booking " + this.getTimeSlot() + "\nCustomer name: " + this.getCustomer().getName() + "\nHaircut Price: " + this.getHaircutPrice() + ",-";
        }

    }
}
