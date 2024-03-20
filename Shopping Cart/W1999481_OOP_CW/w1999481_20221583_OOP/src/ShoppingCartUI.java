
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

//Swing-based user interface for displaying a shopping cart
public class ShoppingCartUI extends JFrame {
    private DefaultTableModel table;
    private JTextField totalField;
    private JTextField categoryDiscount;
    private JTextField firstPurchaseDiscount;
    private JTextField finalTotalField;
    private ShoppingCart shoppingCart;
    private int noOfElecs=0;
    private int noOfClothings=0;

    private double total=0;

    // Constructor that takes a ShoppingCart instance as a parameter
    public ShoppingCartUI(ShoppingCart shoppingCart){
        this.shoppingCart=shoppingCart;
        setTitle("Shopping Cart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setLayout(new BorderLayout());

        // Create a table with specified column names
        String[] columnNames = {"Product","Quantity","Price"};
        table=new DefaultTableModel(columnNames,0);
        JTable jTable=new JTable(table);

        // Add the table to a scroll pane and set its position
        JScrollPane scrollPane=new JScrollPane(jTable);
        add(scrollPane,BorderLayout.CENTER);

        // Create a panel for displaying total information
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new GridLayout(8, 2));

        //Text fields for Adding Discount
        categoryDiscount = new JTextField();
        totalPanel.add(new JLabel("Total: "));
        totalField=new JTextField();
        totalPanel.add(totalField);

        firstPurchaseDiscount = new JTextField();
        totalPanel.add(new JLabel("10% of Discount Added for the First Purchase: "));
        totalPanel.add(firstPurchaseDiscount);

        totalPanel.add(new JLabel("20% of Discount Added - Three Items in the Same Category Discount: "));
        totalPanel.add(categoryDiscount); // Placeholder for the label under Three Items in Same Category Discount

        finalTotalField = new JTextField();
        totalPanel.add(new JLabel("Final Total: "));
        totalPanel.add(finalTotalField);


        // Add the total panel to the south position
        add(totalPanel, BorderLayout.SOUTH);
        // Update the product table and calculate totals
        updateProductTable(shoppingCart);
        calculateTotals();
    }

    // Update the product table based on the products in the shopping cart
    public void updateProductTable(ShoppingCart shoppingCart){
        ArrayList<Product> productsList=shoppingCart.getProducts();
        if (productsList != null) {
            table.setRowCount(0);
            for (Product p : productsList) {
                if ((p.toString().substring(0, p.toString().indexOf('{')).trim()).equals("Clothing")) {

                    Object[] row = clothingInfor((Clothing) p);
                    table.addRow(row);
                } else if ((p.toString().substring(0, p.toString().indexOf('{')).trim()).equals("Electronics")) {

                    Object[] row = electronicsInfo((Electronics) p);
                    table.addRow(row);
                }
            }
        }
    }

    // Create an array of objects representing clothing information
    public Object[] clothingInfor(Clothing p){
        Object [] row={p.getProductId()+"\n"+p.getProductName()+"\n"+p.getSize()+", "+p.getColour(),p.getNoOfItemAvailable(),p.getPrice()};
        noOfClothings++;

        return row;
    }

    // Create an array of objects representing electronics information
    public Object[] electronicsInfo(Electronics p){
        Object [] row={p.getProductId()+"\n"+p.getProductName()+"\n"+p.getBrand()+", "+p.getWarrantyPeriods(),p.getNoOfItemAvailable(),p.getPrice()};
        noOfElecs++;
        return row;
    }

    // Calculate and update the total, discounts, and final total
    public void calculateTotals(){
        double price=0;
        int qty=0;
        double fPurchase=0;
        double catDisc=0;
        for (int i=0; i<table.getRowCount(); i++){
            price=Double.parseDouble(table.getValueAt(i,2).toString());
            qty=Integer.parseInt(table.getValueAt(i,1).toString());
            total+=price*qty;
        }
        double discounts=0;

        if(qty==1) {
            fPurchase=(total*0.1);

        }
        if(noOfClothings>3 ){
            catDisc=(total*0.2);
        }else if(noOfElecs>3){
            catDisc=(total*0.2);
        }
        firstPurchaseDiscount.setText("-"+fPurchase);
        double finalTotal=total- catDisc-fPurchase;
        totalField.setText(String.valueOf(total));
        categoryDiscount.setText("-"+catDisc);
        finalTotalField.setText(String.valueOf(finalTotal));
    }


}
