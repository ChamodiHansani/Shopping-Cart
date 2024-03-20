//Electronics class (Sub class of the Product  class)
class Electronics extends Product{
    public String brand;
    public int warrantyPeriods;

    //Default constructor
    Electronics(){
        // Call the constructor of the superclass (Product)
        super();
    }

    // Parameterized constructor
    public Electronics(String brand, int warrantyPeriods,String productName, int noOfItemAvailable, double price, String productId) {
        super(productName,noOfItemAvailable,price,productId);
        this.brand = brand;
        this.warrantyPeriods = warrantyPeriods;
    }

    //Override toString method
    @Override
    public String toString() {
        return "Electronics{" +
                "brand='" + brand + '\'' +
                ", warrantyPeriods='" + warrantyPeriods + '\'' +
                ", productName='" + productName + '\'' +
                ", noOfItemAvailable=" + noOfItemAvailable +
                ", price=" + price +
                ", productId='" + productId + '\'' +
                '}';
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriods() {
        return warrantyPeriods;
    }

    public void setWarrantyPeriods(int warrantyPeriods) {
        this.warrantyPeriods = warrantyPeriods;
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

