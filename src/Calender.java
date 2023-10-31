import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Calender {
    String calenderName;
    private ArrayList <Day> days = new ArrayList<>();

    public Calender(String name){
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

    public void showCalender(Day day){
        LocalDate startDate = day.getDate();
        LocalDate endDate = day.getDate().plusDays(4);
        ArrayList<Day> days = new ArrayList<>();
        //Build a list of days
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            days.add(searchForDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear()));
        }
        System.out.println("------------------------------------------------------------------------------------");

        //Object[] dayOfWeekArray = days.stream().map(d->d.getDate().getDayOfWeek()).toArray();
        //Object[] toStringArray = days.stream().map(Day::toString).toArray();
        //Print the day of the week

        for (Day d : days) {
            String dayOfWeek = d.getDate().getDayOfWeek().toString();
            int spacesToAdd = 15 - dayOfWeek.length();

            // Pad with spaces to achieve consistent spacing
            String paddedDayOfWeek =" ".repeat(spacesToAdd) +  dayOfWeek ;
            System.out.print(paddedDayOfWeek);
        }
        System.out.println();

        for(Day d : days) {//Print the date as a String
            System.out.printf("%15s", d.toString());
        }
        System.out.println();
        // System.out.printf("   %s         %7s           %7s         %7s        %7s\n", days.get(0).getDate().getDayOfWeek(),days.get(1).getDate().getDayOfWeek(),days.get(2).getDate().getDayOfWeek(),days.get(3).getDate().getDayOfWeek(),days.get(4).getDate().getDayOfWeek());
        //System.out.printf("   %s        %7s         %7s        %7s         %7s\n", days.get(0).toString(),days.get(1).toString(),days.get(2).toString(),days.get(3).toString(), days.get(4).toString());
        System.out.println("------------------------------------------------------------------------------------");
        //Print the day calendar elements with consistent spacing
        for (int i = 0; i < 8; i++) {
            for(Day d: days){
                System.out.printf("%17s", d.buildDayCalender()[i]);//System.out.println(days.get(0).buildDayCalender()[i] +"  " + days.get(1).buildDayCalender()[i] +"  "+ days.get(2).buildDayCalender()[i] + "  " +days.get(3).buildDayCalender()[i] + "  " +  days.get(4).buildDayCalender()[i]);
            }
            System.out.println();
        }
        System.out.println("------------------------------------------------------------------------------------");
    }
    public void showCalenderList(){
        for (Day day:days) {
            System.out.println(day.toString());
        }
    }
}
