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

    public Day searchForDate(int day, int month, int year) {
        Day dateSearchedFor = new Day(day, month, year);
        if (dateSearchedFor.getDate() != null) { //only returns a Day if the day has a possible date.
            Optional<Day> dateInList = days.stream().filter(x -> x.equals(dateSearchedFor)).findFirst();
            if (dateInList.isPresent()) {
                return dateInList.get();
            } else {
                Day newDay = new Day(day, month, year);
                addDay(newDay);
                return newDay;
            }
        } else return null; //
            }


    public void showCalender(){
        for (Day day:days) {
            System.out.println(day.toString());
        }
    }
}
