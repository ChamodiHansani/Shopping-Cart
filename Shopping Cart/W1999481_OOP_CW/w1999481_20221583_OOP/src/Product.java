// Product class (Super class of electronics and clothing classes)
abstract class Product {
    public String productName;
    public int noOfItemAvailable;
    public double price;
    public String productId;

    // Default constructor
    Product(){

    }

    //Override toString method
    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", noOfItemAvailable=" + noOfItemAvailable +
                ", price=" + price +
                ", productId='" + productId + '\'' +
                '}';
    }

    // Parameterized constructor
    public Product(String productName, int noOfItemAvailable, double price, String productId) {
        this.productName = productName;
        this.noOfItemAvailable = noOfItemAvailable;
        this.price = price;
        this.productId = productId;
    }

    //Getters and Setters
    public abstract String getProductId() ;

    public abstract void setProductId(String productId) ;


    public abstract String getProductName();

    public abstract void setProductName(String productName);

    public abstract int getNoOfItemAvailable() ;

    public abstract void setNoOfItemAvailable(int noOfItemAvailable);

    public abstract double getPrice();

    public abstract void setPrice(double price);
}
