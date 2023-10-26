import java.util.ArrayList;

public class Customer {

    private int id;
    private String name;

    public Customer(String name) {
        this.name = name;
       /* this.id = getCustomers().size() + 1;*/
    }

    public Customer() {
        this.name = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
/*    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();

        Customer c1 = new Customer("Annie");
        customers.add(c1);

        return customers;

    }*/


}
