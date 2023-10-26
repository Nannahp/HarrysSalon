public class TestingGround {
    public static void main(String[] args) {
        new TestingGround().run();
    }

    private void run() {
        Calender calender = new Calender("Harry's Salon Calender");
        calender.addDay(new Day(30,3,1998));



        Day date1 = calender.searchForDate(30,3,1998);
        date1.showDay();
        date1.addBooking("booking", 13);
        date1.showDay();

        calender.searchForDate(29,3,1998).addBooking("booking", 10);
        Day date2 = calender.searchForDate(29,3,1998);
        date2.showDay();
        date2.addBooking("booking", 13);
        date2.showDay();


    }
}
