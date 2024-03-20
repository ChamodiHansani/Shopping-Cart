import java.util.ArrayList;

// ShoppingCart class represents shopping cart that holds the list of products and the total cost
public class ShoppingCart {
    // ArrayList to store the products in the shopping cart
    public ArrayList<Product> products;
    private int total;

    // Constructor to initialize the ShoppingCart with a list of products and total cost
    public ShoppingCart(ArrayList<Product> products, int total) {
        this.products = products;
        this.total = total;
    }

    // Getter and setter methods to retrieve and set the list of products
    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    // Getter and setter methods to retrieve the total cost of the products
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
