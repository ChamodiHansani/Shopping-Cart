//Clothing class (Sub class of the Product  class)
public class Clothing extends Product{
    public String size;
    public String colour;

    // Override toString method
    @Override
    public String toString() {
        return "Clothing{" +
                "size=" + size +
                ", colour='" + colour + '\'' +
                ", productName='" + productName + '\'' +
                ", noOfItemAvailable=" + noOfItemAvailable +
                ", price=" + price +
                ", productId='" + productId + '\'' +
                '}';
    }

    // Default constructor
    public Clothing(){

    }

    public Clothing(String size, String colour, String productName, int noOfItemAvailable, double price, String productId) {
        super(productName,noOfItemAvailable,price,productId);
        this.size = size;
        this.colour = colour;
    }

    //Getter and Setter Methods
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
    @Override
    public String getProductId() {
        return productId != null ? productId : "";
    }

    @Override
    public void setProductId(String productId) {
        this.productId=productId;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public void setProductName(String productName) {
        this.productName=productName;
    }

    @Override
    public int getNoOfItemAvailable() {
        return noOfItemAvailable;
    }

    @Override
    public void setNoOfItemAvailable(int noOfItemAvailable) {
        this.noOfItemAvailable=noOfItemAvailable;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price=price;
    }
}
