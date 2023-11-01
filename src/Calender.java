import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Calender {
    String calenderName;
    private ArrayList<Day> days = new ArrayList<>();

    public Calender(String name) {
        this.calenderName = name;
    }

    public void addDay(Day day) {
        days.add(day);
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

    public void addHardcodedDay(Day day, Calender calender) {
        Day hardcodedDay = day;
        Customer hardcodedCustomer = new Customer("H. Uman");
        ArrayList<Booking> hardcodedBookings = new ArrayList<>();
        hardcodedBookings.add(new Booking(1, hardcodedDay, hardcodedCustomer, 345, randomProducts()));
        hardcodedBookings.add(new Booking(2, hardcodedDay, hardcodedCustomer, 345, randomProducts()));
        hardcodedBookings.add(new Booking(3, hardcodedDay, hardcodedCustomer, 345, randomProducts()));
        hardcodedBookings.add(new Booking(4, hardcodedDay, hardcodedCustomer, 99, randomProducts()));
        hardcodedBookings.add(new Booking(5, hardcodedDay, hardcodedCustomer, 99, randomProducts()));
        hardcodedBookings.add(new Booking(6, hardcodedDay, hardcodedCustomer, 246, randomProducts()));
        hardcodedBookings.add(new Booking(7, hardcodedDay, hardcodedCustomer, 567, randomProducts()));
        hardcodedBookings.add(new Booking(8, hardcodedDay, hardcodedCustomer, 123, randomProducts()));

        calender.addDay(hardcodedDay);
        hardcodedDay.setBookings(hardcodedBookings);

        // Forklaring på hvilken hardcoded dato der er at tjekke før i dag da man ikke kan booke i fortiden
        System.out.println("\n---DISCLAIMER---DISCLAIMER---DISCLAIMER---");
        System.out.println("Please note that until we have the ability to keep dates in file,");
        System.out.println("with the methods used and choices made at the moment, we are not really able to see");
        System.out.println("real past dates that have been added before today's date");
        System.out.println("Therefore we have provided a past date, hardcoded as a dummy date");
        System.out.println("so that the teachers can test the program from the accountant's perspective");
        System.out.println();
        System.out.println("The hardcoded date to check is: 03-03-2020");
        System.out.println("But feel free to also check other dates too! They are just a bit boring as they");
        System.out.println("are empty by default :)");
        System.out.println("---DISCLAIMER---DISCLAIMER---DISCLAIMER---\n");
    }

    //For the addHardcodedDay
    public ArrayList<Product> randomProducts() {
        ArrayList<Product> randomProducts = new ArrayList<>();
        for (int i = 0; i < (int)(Math.random() * 3) + 1; i++) {
            randomProducts.add(ProductBuilder.getProducts().get((int)(Math.random() * 5) + 1));
        }
        return randomProducts;
    }

}
