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

    public void loadBookingDataFromFile(String fileName, Calender calendar) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            Day currentDay = null;
            boolean isNewDay = true;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] parts = line.split(";");
                if (parts.length < 5) {
                    System.err.println("Invalid format in the input file. Skipping line: " + line);
                    continue;
                }

                LocalDate date = LocalDate.parse(parts[0]);
                int timeSlot = Integer.parseInt(parts[1]);
                String name = parts[2];
                double haircutPrice = Double.parseDouble(parts[3]);

                System.out.println("Loaded data: Date: " + date + ", Time Slot: " + timeSlot +
                        ", Name: " + name + ", Haircut Price: " + haircutPrice);

                if (isNewDay) {
                    currentDay = new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
                    days.add(currentDay);
                    System.out.println("Creating a new Day for " + date);
                    isNewDay = false; // New day is created, so reset the flag
                }

                if (currentDay != null && timeSlot >= 1 && timeSlot <= 8) {
                    Booking currentBooking = new Booking(timeSlot, currentDay);
                    if (name != "null") {
                        currentBooking.setCustomerName(name);
                    } else {
                        currentBooking.setCustomerName(null);
                    }
                    currentBooking.setHaircutPrice(haircutPrice);
                    currentDay.addBooking(currentBooking, timeSlot);
                    System.out.println("Added booking for Time Slot " + timeSlot + ": " + name + " - " + haircutPrice);
                }

                if (timeSlot == 8) {
                    isNewDay = true; // End of bookings for the current day, set flag to create a new day
                    System.out.println("End of bookings for the current day.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /*public void loadBookingDataFromFile(String fileName, Calender calendar) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            Day currentDay = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] parts = line.split(";");
                LocalDate date = LocalDate.parse(parts[0]);
                int timeSlot = Integer.parseInt(parts[1]);
                String name = parts[2];
                double haircutPrice = Double.parseDouble(parts[3]);

                if (timeSlot == 1) {
                    currentDay = new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
                    days.add(currentDay);
                }

                if (currentDay != null && timeSlot >= 1 && timeSlot <= 8) {
                    Booking booking = new Booking(timeSlot, currentDay);
                    currentDay.addBooking(booking, timeSlot);
                }

                if (line.endsWith("END")) {
                    currentDay = null; // End of bookings for the current day
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/



    public void createBooking(LocalDate date, int bookingId, String name, double haircutPrice) {
        Day day = searchForDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        if (day == null) {
            day = new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
            addDay(day);
        }
    }

    /*public void loadBookingDataFromFile(String filename) {
        ArrayList<Product> availableProducts = new ProductBuilder().getProducts();
        boolean endOfBooking = false;

        try (Scanner scanner = new Scanner(new FileReader(filename))) {
            while (scanner.hasNextLine()) {
                String dateBookingIdLine = scanner.nextLine();

                if (dateBookingIdLine.equals("END")) {
                    continue;
                } String[] parts = dateBookingIdLine.split(";");
                LocalDate date = LocalDate.parse(parts[0]);
                int bookingId = Integer.parseInt(parts[1]);
                String name = parts[2];
                double haircutPrice = Double.parseDouble(parts[3]);

                createBooking(date, bookingId, name, haircutPrice);
            }

        } catch (IOException e) {
            System.out.println("Something went wrong while loading the file");
            e.printStackTrace();
        }
    }*/

    public Day getDayByDate(LocalDate targetDate) {
        for (Day day : days) {
            if (day.getDate().equals(targetDate)) {
                return day;
            }
        }
        return null; // Return null if no matching Day is found
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
