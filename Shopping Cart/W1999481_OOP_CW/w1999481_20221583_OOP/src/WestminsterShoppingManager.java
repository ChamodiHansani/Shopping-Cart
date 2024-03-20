import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// WestminsterShoppingManager class implements ShoppingManager interface
public class WestminsterShoppingManager implements ShoppingManager{

    // ArrayList to store products
    static ArrayList<Product> products=new ArrayList<>();
    static int index=0;

    // Method to add a new product to the system
    public static void addProduct(){
        System.out.println("\nSelect the Product type: ");
        System.out.println("1.Electronics\n2.Clothing");
        Scanner input=new Scanner(System.in);

        while (true) {
            if(index==50){
                System.out.println("Store is Full.");
                break;
            }
            int type=0;
            try{
                type=input.nextInt();
            }catch (Exception e){
                System.out.println("Please Enter No.1 or No.2:");
                input.nextLine();
                type=input.nextInt();
            }
            input.nextLine();

            if(type==1){// Add Electronics product
                System.out.println("Please Enter the Brand:");
                String brand=input.nextLine();
                System.out.println("Please Enter the Warranty Period: ");
                int warrantyPeriods=0;
                try{
                    warrantyPeriods=input.nextInt();
                }catch (Exception e){
                    System.out.println("Please Enter a valid integer value.");
                    input.nextLine();
                    warrantyPeriods=input.nextInt();
                }
                input.nextLine();
                System.out.println("Please Enter the Product Name: ");
                String productName=input.nextLine();
                System.out.println("Please Enter Number of Items available: ");
                int noOfItemAvailable=0;
                try{
                    noOfItemAvailable=input.nextInt();
                }catch (Exception e){
                    System.out.println("Please Enter a valid integer value.");
                    input.nextLine();
                    noOfItemAvailable=input.nextInt();
                }

                System.out.println("Please Enter the price: ");
                double price;
                try{
                    price=input.nextDouble();
                }catch (Exception e){
                    System.out.println("Please Enter a valid decimal value.");
                    input.nextLine();
                    price=input.nextDouble();
                }
                input.nextLine();
                System.out.println("Please Enter the product Id:");
                String productId=input.nextLine();
                Product e=new Electronics(brand,warrantyPeriods,productName,noOfItemAvailable,price,productId);
                products.add(e);
                index++;
                break;

            }else if(type==2){// Add Clothing product
                System.out.println("Please Enter the Product Name: ");
                String productName=input.nextLine();
                System.out.println("Please Enter Number of Items available: ");
                int noOfItemAvailable=0;
                try{
                    noOfItemAvailable=input.nextInt();
                }catch (Exception e){
                    System.out.println("Please Enter a valid integer value.");
                    input.nextLine();
                    noOfItemAvailable=input.nextInt();
                }
                System.out.println("Please Enter the price: ");
                double price;
                try{
                    price=input.nextDouble();
                }catch (Exception e){
                    System.out.println("Please Enter a valid decimal value.");
                    input.nextLine();
                    price=input.nextDouble();
                }
                input.nextLine();
                System.out.println("Please Enter the product Id:");
                String productId=input.nextLine();
                System.out.println("Please Enter the Size: ");
                String size=input.nextLine();
                System.out.println("Please Enter the Color: ");
                String colour=input.nextLine();

                Product c=new Clothing(size,colour,productName,noOfItemAvailable,price,productId);
                products.add(c);
                index++;

                break;
            }else{
                System.out.print("Please Enter the No.1 or No.2:");
            }

        }
    }

    // Method to delete a product from the system
    public static void deleteProduct(String productId){

        l1:for (int i = 0; i < products.size(); i++) {
            Product p=products.get(i);
            if(p.getProductId().equals(productId)){
                products.remove(i);
                System.out.println("Product Removed Successfully.");
                break l1;
            }
        }
        index--;
    }

    // Method to print the list of products
    public static int printProducts(){
        // Check if there are no items in the list
        if(index==0){
            System.out.println("No Items in the list.");
            return 0;
        }

        // Sort products alphabetically based on product ID
        Collections.sort(products,Comparator.comparing(Product::getProductId,Comparator.nullsLast(String::compareTo)));
        for (Product p:
                products) {
            System.out.println(p);
        }
        return 0;
    }

    // Method to save the list of products to a file
    public static void saveInFile(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("products_name.txt"))) {
            for (Product product : products) {
                writer.write(product.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read back product information from a file
    public static void readBack(){
        try (BufferedReader reader = new BufferedReader(new FileReader("products_name.txt"))) {
            String line;
            index=-1;
            while ((line = reader.readLine()) != null) {

                int braceIndex = line.indexOf('{');
                if ((line.substring(0, braceIndex).trim()).equals("Electronics")) {
                    Product p=electronicsInfo(line);
                    products.add(p);
                    System.out.println(line);
                }else if((line.substring(0, braceIndex).trim()).equals("Clothing")) {
                    Product p=clothingInfor(line);
                    products.add(p);
                    System.out.println(line);
                }

            }
            System.out.println("Reloaded all the records.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to extract Clothing information from a line
    public static Clothing clothingInfor(String line){

        String regex = "Clothing\\{size=(.+), colour='(.+)', productName='(.+)', noOfItemAvailable=(\\d+), price=(\\d+\\.\\d+), productId='(.+)'\\}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        Clothing c=new Clothing();
        if(matcher.matches()){
            c.setPrice(Double.parseDouble(matcher.group(5)));
            c.setColour(matcher.group(2));
            c.setSize(matcher.group(1));
            c.setProductId(matcher.group(6));
            c.setProductName(matcher.group(3));
            c.setNoOfItemAvailable(Integer.parseInt(matcher.group(4)));
        }else{
            return null;
        }

        return c;
    }

    // Method to extract Electronics information from a line
    public static Electronics electronicsInfo(String line){
        String regex = "Electronics\\{brand='(.+)', warrantyPeriods='(.+)', productName='(.+)', noOfItemAvailable=(\\d+), price=(\\d+\\.\\d+), productId='(.+)'\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        Electronics e=new Electronics();

        if(matcher.matches()){
            e.setBrand(matcher.group(1));
            e.setWarrantyPeriods(Integer.parseInt(matcher.group(2)));
            e.setPrice(Double.parseDouble(matcher.group(5)));
            e.setProductId(matcher.group(6));
            e.setProductName(matcher.group(3));
            e.setNoOfItemAvailable(Integer.parseInt(matcher.group(4)));

        }else{
            return null;
        }
        return e;
    }

    public static void main(String[] args) {
        while(true){
            System.out.println("\nWestminster Shopping Center\n");
            System.out.println("Menu");
            System.out.println("1.Add a new product.");
            System.out.println("2.Delete a product.");
            System.out.println("3.Print the list of the products.");
            System.out.println("4.Save in a file.");
            System.out.println("5.Read back all the information.");
            System.out.print("Select an option: ");
            Scanner input=new Scanner(System.in);
            int choice=0;
            try{
                choice=input.nextInt();
            }catch (Exception e){
                System.out.println("Please Enter a valid number.");
                input.nextLine();
                choice=input.nextInt();
            }

            // Switch statement
            switch (choice){
                case 1:
                    addProduct();
                    break;
                case 2:
                    input.nextLine();
                    System.out.println("Please Enter the product ID: ");
                    String id=input.nextLine();
                    deleteProduct(id);
                    break;
                case 3:
                    printProducts();
                    break;
                case 4:
                    saveInFile();
                    System.out.println("Saved Successfully!");
                    break;
                case 5:
                    readBack();
                    break;
                default:
                    System.out.println("Invalid Input Please Enter a Valid Input.");
                    break;
            }
        }

    }
}
