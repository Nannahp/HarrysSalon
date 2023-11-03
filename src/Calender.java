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

    public void showCalenderList() {
        for (Day day : days) {
            System.out.println(day.toString());
        }
    }


}
