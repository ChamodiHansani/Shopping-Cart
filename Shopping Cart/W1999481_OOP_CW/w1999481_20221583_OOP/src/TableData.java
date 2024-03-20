//TableData class
public class TableData {
    String productId;
    String name;
    String category;
    String price;
    String info;
    int qty;
    public int getQuantity() {
        return qty;
    }

    public void setQuantity(int qty) {
        this.qty = qty;
    }

    // Default constructor
    public TableData() {
    }

    // Parameterized constructor
    public TableData(String productId, String name, String category, String price, String info,int qty) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.info = info;
        this.qty=qty;
    }

    //Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
