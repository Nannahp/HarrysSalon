import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Day {
    private LocalDate date;
    private final ArrayList<String> bookings =  new ArrayList<>(Arrays.asList(new String[8]));

    public Day(int day, int month, int year) {
        try {
            this.date = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            System.out.println("Date does not exist. Are you sure you have entered the right date?");
        }
    }
    public LocalDate getDate() {
        return date;
    }
    public void addBooking(String booking, int time){
        int index = time - 10;
        bookings.set(index, booking);
    }

    public void showDay(){
        System.out.println(date.getDayOfWeek().toString());
        System.out.println(toString());
        for (int i =0; i < bookings.size(); i++) {
            System.out.print((i+10));
            if (bookings.get(i) ==null){
                System.out.println(": Available");}
            else {
                System.out.println(": Booked");
            }
        }
        System.out.println(" ");
    }


    @Override
    public String toString() {
        return date.getDayOfMonth() + "-" + date.getMonthValue() + "-" +  date.getYear();
    }

    @Override       // Makes it possible to compare instances of Day's only by their date.
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        return Objects.equals(date, day.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
