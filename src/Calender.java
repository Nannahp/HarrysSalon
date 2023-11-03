import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;
import java.io.*;

public class Calender {
    String calenderName;
    private ArrayList<Day> days = new ArrayList<>();

    public Calender(String name) {
        this.calenderName = name;
    }

    public void addDay(Day day) {
        days.add(day);
    }
    public void saveBookingDataToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Day day : days) {
                for (Booking booking : day.getBookings()) {
                    StringBuilder bookingDetails = new StringBuilder();
                    bookingDetails.append(day.getDate())
                            .append(";")
                            .append(booking.getId())
                            .append(";")
                            .append(String.valueOf(booking.getCustomer().getName()))
                            .append(";")
                            .append(booking.getHaircutPrice())
                            .append(";");
                    for (Product product : booking.getProducts()) { // Save product details (if any)
                        bookingDetails.append(product.getId()).append(";");
                    }
                    writer.println(bookingDetails.toString());
                }
            }
            System.out.println("Booking data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBookingDataFromFile(String fileName, Calender calendar) throws FileNotFoundException {
        ProductBuilder productBuilder = new ProductBuilder();
        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            Day currentDay = null;
            boolean isNewDay = true;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");

                if (parts.length < 4) {
                    System.err.println("Invalid format in the input file. Skipping line: " + line);
                    continue;
                }
                LocalDate date = LocalDate.parse(parts[0]);
                int timeSlot = Integer.parseInt(parts[1]);
                String name = parts[2];
                double haircutPrice = Double.parseDouble(parts[3]);

                if (isNewDay) {
                    currentDay = new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
                    days.add(currentDay);
                    isNewDay = false; // New day is created, so reset the boolean
                }

                updateDay(currentDay, timeSlot, name, haircutPrice, productBuilder, parts);

                if (timeSlot == 8) {isNewDay = true;}
            }
        } catch (IOException e) {e.printStackTrace();}
    }
    private void updateDay(Day currentDay, int timeSlot, String name, double haircutPrice, ProductBuilder productBuilder, String[] parts) {
        if (currentDay != null && timeSlot >= 1 && timeSlot <= 8) {
            Booking currentBooking = new Booking(timeSlot, currentDay);
            checkName(name, currentBooking);
            currentBooking.setHaircutPrice(haircutPrice);
            currentDay.addBooking(currentBooking, timeSlot);
            addProductsToBooking(parts, productBuilder, currentBooking);
        }
    }
    private void addProductsToBooking(String[] parts, ProductBuilder productBuilder, Booking currentBooking) {
        for (int i = 4; i < parts.length; i++) {
            int productId = Integer.parseInt(parts[i]);
            ArrayList<Product> products = productBuilder.getProducts();
            if (productId >= 1 && productId <= products.size()) {
                Product productToAdd = products.get(productId - 1);
                currentBooking.addProduct(productToAdd); // Add product to the booking
            }
        }
    }
    private void checkName(String name, Booking currentBooking) {
        if (!"null".equals(name)) {
            currentBooking.setCustomerName(name);
        } else {
            currentBooking.setCustomerName(null);
        }
    }

    public void registerHolidays(Day day1, Day day2) {
        LocalDate startDate = day1.getDate();
        LocalDate endDate = day2.getDate();

        Day day = null;

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            day = searchForDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
            day.closeDay();
            day.setHoliday(true);
        }
    }

    public Day searchForDate(int day, int month, int year) {
        Day dateSearchedFor = new Day(day, month, year);

        if (dateSearchedFor.getDate() != null) { //only returns a Day if the day has a possible date.
            Optional<Day> dateInList = days.stream().filter((dateSearchedFor::equals)).findFirst();
            if (dateInList.isPresent()) {
                return dateInList.get();
            } else {
                Day newDay = new Day(day, month, year);
                addDay(newDay);
                return newDay;
            }
        } else return null; //
    }

    public ArrayList<Day> buildCalender(Day day) {
        LocalDate startDate = day.getDate();
        LocalDate endDate = day.getDate().plusDays(4);
        ArrayList<Day> daysInSearch = new ArrayList<>();
        //Build a list of days
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            daysInSearch.add(searchForDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear()));
        }
        return daysInSearch;
    }

    public void showCalender(Day day) {
        ArrayList<Day> daysInSearch = buildCalender(day);
        int dashCount = 87;

        System.out.println("-".repeat(dashCount));
        showWeekdays(daysInSearch);
        System.out.println();

        showDate(daysInSearch);
        System.out.println();
        System.out.println("-".repeat(dashCount));
        showContents(daysInSearch);
        System.out.println("-".repeat(dashCount));
    }

    public void showWeekdays(ArrayList<Day> daysInList) {
        for (Day d : daysInList) {
            String dayOfWeek = d.getDate().getDayOfWeek().toString();
            int spacesToAdd = 11 - dayOfWeek.length();
            System.out.print("      " + dayOfWeek + " ".repeat(spacesToAdd));
        }
    }

    public void showDate(ArrayList<Day> daysInList) {
        for (Day d : daysInList) {//Print the date as a String
            String date = d.getDate().toString();
            if (d.getDay()<10) {
                int spacesToAdd = 13 - date.length();
                System.out.print("      " + d.toString() + " ".repeat(spacesToAdd));
            }
            else if (d.getDay() >10 || d.getMonth()> 9){
                int spacesToAdd = 12 - date.length();
                System.out.print("      " + d.toString() + " ".repeat(spacesToAdd));
            }
            else {
                int spacesToAdd = 11 - date.length();
                System.out.print("     " + d.toString() + " ".repeat(spacesToAdd));

            }
        }
    }

    public void showContents(ArrayList<Day> daysInList) {
        for (int i = 0; i < 8; i++) {
            for (Day d : daysInList) {
                System.out.printf("%17s", d.buildDayCalender()[i]);
            }
            System.out.println();
        }
    }



}
