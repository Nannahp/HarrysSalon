public class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        try {
            if (id >= 0 && price >= 0) {
                this.id = id;
                this.name = name;
                this.price = price;
            } else {

                // Here, I'm throwing an IllegalArgumentException if id or price is negative.
                throw new IllegalArgumentException("ID and price must be non-negative.");
            }
        } catch (IllegalArgumentException e) {

            System.err.println("Error creating a product: " + e.getMessage());

            this.id = -1;
            this.name = "Invalid Product";
            this.price = 0.0;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
