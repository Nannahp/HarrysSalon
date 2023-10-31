import java.util.ArrayList;

public  class ProductBuilder {
    public static ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();


        Product p0 = new Product(1, "Hair Brush", 149);
        Product p1 = new Product(2, "Hair Spray", 79);
        Product p2 = new Product(3, "Hair Color", 99);
        Product p3 = new Product(4, "Hair Gel", 64);
        Product p4 = new Product(5, "Hair Mask", 119);
        Product p5 = new Product(6, "Hair Scissors", 129);


        products.add(p0);
        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
        products.add(p5);

        return products;

    }


}
