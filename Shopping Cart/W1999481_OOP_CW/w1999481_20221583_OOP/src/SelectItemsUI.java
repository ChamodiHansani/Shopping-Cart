import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Graphical User Interface (GUI) for selecting and managing products
public class SelectItemsUI extends JFrame {
    private JComboBox<String> productType;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JPanel productDetails;
    private JButton addToCart;
    private JButton viewCart;
    private ArrayList<TableData> productsTable=new ArrayList<>();

    private int electQty=0;
    private int clothQty=0;

    private ShoppingCart shoppingCart=new ShoppingCart(new ArrayList<>(),0);

    private ShoppingCartUI shoppingCartUI =null;

    // Constructor for the GUI
    public SelectItemsUI(){
        setTitle("Product Visualization App");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        productType=new JComboBox<>(new String[]{"Electronics","Clothing"});

        productType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                updateProductTable();
            }
        });

        // DefaultTableModel for product data
        tableModel=new DefaultTableModel(new Object[]{"ProductId","Name","Category","Price","Info"},0);
        productTable = new JTable(tableModel);
        productDetails=new JPanel();

        productDetails.setBorder(BorderFactory.createTitledBorder("Selected Product - Details"));
        productDetails.setPreferredSize(new Dimension(800, 250));

        // JButton for adding the selected product to the shopping cart
        addToCart=new JButton("Add to Cart");
        addToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });

        productDetails.setLayout(new BorderLayout());
        productDetails.add(addToCart, BorderLayout.SOUTH);

        // JButton for viewing the shopping cart
        viewCart=new JButton("Shopping Cart");
        viewCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(shoppingCartUI==null){
                    shoppingCartUI=new ShoppingCartUI(shoppingCart);
                    shoppingCartUI.setVisible(true);
                }else{
                    shoppingCartUI.setVisible(true);
                    shoppingCartUI.updateProductTable(shoppingCart);
                    shoppingCartUI.calculateTotals();
                }

            }
        });

        setLayout(new BorderLayout());
        JPanel topPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(productType);
        topPanel.add(viewCart);
        add(topPanel,BorderLayout.NORTH);
        add(new JScrollPane(productTable),BorderLayout.CENTER);
        add(productDetails,BorderLayout.SOUTH);
        add(addToCart,BorderLayout.EAST);
        productDetails.add(addToCart, BorderLayout.SOUTH);
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    updateProductDetailsPanel();
                }
            }
        });
    }

    // Update the product table based on the selected product type
    private void updateProductTable(){
        productsTable=fetchProducts(productType.getSelectedItem().toString());

        tableModel.setRowCount(0);
        for (TableData p:
                productsTable) {
            Object [] row={p.getProductId(),p.getName(),p.getCategory(),p.getPrice(),p.getInfo()};
            tableModel.addRow(row);
        }

        highLightLowAvailableProducts();


    }

    // Highlight products with low availability in the product table
    private void highLightLowAvailableProducts() {
        for (int i = 0; i < productsTable.size(); i++) {
            int amnt = (int) productsTable.get(i).getQuantity();
            if (amnt < 3) {
                productTable.setRowSelectionInterval(i, i);
                productTable.getColumnModel().getColumn(0).setCellRenderer(new HighlightRenderer());
            }
        }
    }
    private class HighlightRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            c.setBackground(Color.RED);
            return c;
        }
    }

    // Update the product details panel based on the selected product
    private void updateProductDetailsPanel() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            TableData selectedProduct = productsTable.get(selectedRow);
            displayProductDetails(selectedProduct);
        }
    }

    // Display details of the selected product in the product details panel
    private void displayProductDetails(TableData selectedProduct) {
        StringBuilder details = new StringBuilder();
        details.append("<html><b>Category:</b> ").append(selectedProduct.getCategory()).append("<br>");
        details.append("<b>Name:</b> ").append(selectedProduct.getName()).append("<br>");
        details.append("<b>Price:</b> ").append(selectedProduct.getPrice()).append("<br>");
        details.append("<b>Info:</b> ").append(selectedProduct.getInfo()).append("</html>");


        productDetails.removeAll();
        JLabel detailsLabel = new JLabel(details.toString());
        detailsLabel.setVerticalAlignment(SwingConstants.TOP);
        productDetails.add(detailsLabel, BorderLayout.CENTER);
        productDetails.add(addToCart, BorderLayout.SOUTH);
        productDetails.revalidate();
        productDetails.repaint();
    }

    // Add the selected product to the shopping cart
    private void addToCart(){
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            TableData selectedProduct = productsTable.get(selectedRow);
            if(searchProduct(selectedProduct.getProductId())==null){
                if(selectedProduct.getCategory().equals("Electronics")){
                    electQty++;
                    Electronics e=new Electronics();
                    e.setPrice(Double.parseDouble(selectedProduct.getPrice()));
                    e.setNoOfItemAvailable(1);
                    e.setProductId(selectedProduct.getProductId());
                    e.setProductName(selectedProduct.getName());
                    String warr=selectedProduct.getInfo().split(", ")[0];
                    String brand=selectedProduct.getInfo().split(", ")[1];
                    e.setWarrantyPeriods(Integer.parseInt(warr));
                    e.setBrand(brand);
                    shoppingCart.products.add(e);
                }else if(selectedProduct.getCategory().equals("Clothing")){
                    clothQty++;
                    Clothing e=new Clothing();
                    e.setPrice(Double.parseDouble(selectedProduct.getPrice()));
                    e.setNoOfItemAvailable(1);
                    e.setProductId(selectedProduct.getProductId());
                    e.setProductName(selectedProduct.getName());
                    String size=selectedProduct.getInfo().split(", ")[1];
                    String col=selectedProduct.getInfo().split(", ")[0];
                    e.setSize(size);
                    e.setColour(col);
                    shoppingCart.products.add(e);
                }
            }else{
                for (int i = 0; i < shoppingCart.products.size(); i++) {
                    Product p=shoppingCart.products.get(i);
                    if(p.getProductId().equals(selectedProduct.getProductId())){
                        p.setNoOfItemAvailable(p.getNoOfItemAvailable()+1);
                    }
                }
            }
        }
    }

    // Search for a product in the shopping cart based on productId
    public Product searchProduct(String productId){

        l1:for (int i = 0; i < shoppingCart.products.size(); i++) {
            Product p=shoppingCart.products.get(i);
            if(p.getProductId().equals(productId)){
                return p;
            }
        }
        return null;
    }

    private void showShoppingCart(){

        System.out.println("Shopping Cart");
    }

    // Fetch products from the data file based on product type
    private ArrayList<TableData> fetchProducts(String type){
        ArrayList<TableData> products=new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("products_name.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {

                int braceIndex = line.indexOf('{');
                if ((line.substring(0, braceIndex).trim()).equals(type)) {
                    if(type.equals("Electronics")){
                        TableData p=electronicsInfo(line);
                        products.add(p);
                    }else if(type.equals("Clothing")){
                        TableData p=clothingInfor(line);
                        products.add(p);
                    }
                }

            }
            System.out.println("All records reloaded.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;

    }

    // Parse information for Clothing product from the data file
    public static TableData clothingInfor(String line){

        String regex = "Clothing\\{size=(.+), colour='(.+)', productName='(.+)', noOfItemAvailable=(\\d+), price=(\\d+\\.\\d+), productId='(.+)'\\}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        TableData t=new TableData();
        if(matcher.matches()){
            t.setCategory("Clothing");
            t.setInfo(matcher.group(2)+", "+matcher.group(1));
            t.setPrice(matcher.group(5));
            t.setProductId(matcher.group(6));
            t.setName(matcher.group(3));
            t.setQuantity(Integer.parseInt(matcher.group(4)));
        }else{
            return null;
        }

        return t;
    }

    // Parse information for Electronics product from the data file
    public static TableData electronicsInfo(String line){
        String regex = "Electronics\\{brand='(.+)', warrantyPeriods='(.+)', productName='(.+)', noOfItemAvailable=(\\d+), price=(\\d+\\.\\d+), productId='(.+)'\\}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        TableData t=new TableData();
        if(matcher.matches()){
            t.setCategory("Electronics");
            t.setInfo(matcher.group(2)+", "+matcher.group(1));
            t.setPrice(matcher.group(5));
            t.setProductId(matcher.group(6));
            t.setName(matcher.group(3));
            t.setQuantity(Integer.parseInt(matcher.group(4)));
        }else{
            return null;
        }
        return t;
    }


    // Main method to run the application
    public static void main(String [] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SelectItemsUI().setVisible(true);
            }
        });
    }
}
