import java.util.ArrayList;
import java.util.Optional;

public class Calender {
    String calenderName;
    private ArrayList <Day> days = new ArrayList<>();

    public Calender(String name){
        this.calenderName = name;
    }
    public void addDay(Day day){
        int index = 0;
        while (index < days.size() && day.getDate().isAfter(days.get(index).getDate()))
            index++;
            days.add(index, day);
        }



    public Day searchForDate(int day, int month, int year) {
        Day dateSearchedFor = new Day(day, month, year);
        Optional<Day> dateInList = days.stream().filter(x-> x.equals(dateSearchedFor)).findFirst();
           if (dateInList.isPresent()) {
               //System.out.println("found");
                return dateInList.get() ;
            } else {
               //System.out.println("not found");
                Day newDay = new Day(day, month, year);
                addDay(newDay);
                return newDay;
            }
        }
    public void showCalender(){
        for (Day day:days) {
            System.out.println(day.toString());
        }
    }
}
