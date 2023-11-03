import java.time.LocalDate;
import java.util.*;
import java.io.*;
import java.util.ArrayList;


public class Calender {
    String calenderName;
    private ArrayList<Day> days = new ArrayList<>();


    public Calender(String name) {
        this.calenderName = name;
    }

    public void addDay(Day day) {days.add(day);
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
                    // Save product details (if any)
                    for (Product product : booking.getProducts()) {
                        bookingDetails.append(product.getId()).append(";");
                    }
                    bookingDetails.append("END");
                    writer.println(bookingDetails.toString());
                }
            }
            System.out.println("Booking data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Day getDayByDate(LocalDate targetDate) {
        for (Day day : days) {
            if (day.getDate().equals(targetDate)) {
                return day;
            }
        }
        return null; // Return null if no matching Day is found
    }


    public void loadBookingDataFromFile(String fileName, Calender calendar) throws FileNotFoundException {
        ArrayList<Product> availableProducts = new ProductBuilder().getProducts();
        ArrayList<Booking> bookings = new ArrayList<>(8);
        boolean endOfBooking = false;
        Day currentDay = null;

        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            while (scanner.hasNextLine()) {
                String dateBookingIdLine = scanner.nextLine();

                if (dateBookingIdLine.equals("END")) {
                    endOfBooking = true; // End of current booking
                } else {
                    String[] parts = dateBookingIdLine.split(";");
                    LocalDate date = LocalDate.parse(parts[0]);
                    int bookingId = Integer.parseInt(parts[1]);
                    String name = parts[2];
                    double haircutPrice = Double.parseDouble(parts[3]);

                    System.out.println("Debug: Date: " + date + ", Booking ID: " + bookingId + ", Name: " + name + ", Haircut Price: " + haircutPrice);

                    if (!endOfBooking) {
                        Booking currentBooking = new Booking(bookingId, currentDay);
                        if (!"null".equals(name)) {
                            currentBooking.getCustomer().setName(name);
                        } else {
                            currentBooking.getCustomer().setName(null);
                        }
                        currentBooking.setHaircutPrice(haircutPrice);
                        bookings.add(currentBooking);
                    }

                    if (currentDay == null || !currentDay.getDate().isEqual(date)) {
                        currentDay = new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
                        currentDay.initializeBookings();
                        currentDay.setBookings(bookings);
                        bookings = new ArrayList<>(8);
                        calendar.addDay(currentDay);
                    }
                    System.out.println(days.size());
                }
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while loading the file");
            e.printStackTrace();
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
            int spacesToAdd = 13 - date.length();
            System.out.print("      " + d.toString() + " ".repeat(spacesToAdd));
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

    public void showCalenderList() {
        for (Day day : days) {
            System.out.println(day.toString());
        }
    }


}
