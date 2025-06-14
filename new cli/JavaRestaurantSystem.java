import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class JavaRestaurantSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Menu menu = new Menu();
    private static List<Order> allOrders = new ArrayList<>();
    private static Customer currentCustomer;

    public static void main(String[] args) {
        menu.loadFromFile("marrybrown_menu.txt");
        loadOrdersFromFile();

        boolean running = true;
        while (running) {
            System.out.println("\nWelcome to the Java Restaurant System!\n");
            System.out.println("Main Menu:");
            System.out.println("1. View Menu");
            System.out.println("2. Search Dish");
            System.out.println("3. Place Order");
            System.out.println("4. Checkout and Print Receipt");
            System.out.println("5. Admin Login (for Cashier Functions)");
            System.out.println("6. Exit\n");
            System.out.print("Please enter your choice (1-6): ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    menu.displayMenu();
                    break;
                case "2":
                    System.out.print("Enter Dish Name to Search: ");
                    String name = scanner.nextLine();
                    menu.searchItem(name);
                    break;
                case "3":
                    placeOrder();
                    break;
                case "4":
                    checkout();
                    break;
                case "5":
                    adminLogin();
                    break;
                case "6":
                    saveOrdersToFile();
                    System.out.println("\nExiting the Java Restaurant System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input. Try again.");
            }
        }
    }

    private static void placeOrder() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        currentCustomer = new Customer(name, phone, address);

        List<OrderItem> items = new ArrayList<>();
        menu.displayMenu();
        System.out.print("Enter dish numbers and quantities (e.g., 1x2,3x1): ");
        String[] input = scanner.nextLine().split(",");

        for (String entry : input) {
            try {
                String[] parts = entry.trim().split("x");
                int idx = Integer.parseInt(parts[0]) - 1;
                int qty = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;
                if (idx >= 0 && idx < menu.getSize()) {
                    MenuItem item = menu.getItem(idx);
                    items.add(new OrderItem(item, qty));
                } else {
                    System.out.println("Invalid index: " + parts[0]);
                }
            } catch (Exception e) {
                System.out.println("Invalid input format for entry: " + entry);
            }
        }
        if (!items.isEmpty()) {
            Order order = new Order(currentCustomer, items);
            allOrders.add(order);
            order.displaySummary();
        } else {
            System.out.println("No valid items selected. Order not placed.");
        }
    }

    private static void checkout() {
        if (allOrders.isEmpty()) {
            System.out.println("No order to checkout.");
            return;
        }
        Order lastOrder = allOrders.get(allOrders.size() - 1);
        System.out.print("Proceed to checkout? (Y/N): ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            System.out.print("Payment Method (Cash/Card): ");
            String method = scanner.nextLine();
            lastOrder.setPaymentMethod(method);
            lastOrder.printReceipt();
        }
    }

    private static void adminLogin() {
        System.out.print("Enter Admin Password: ");
        String pass = scanner.nextLine();
        if (!pass.equals("admin123")) {
            System.out.println("Invalid password.");
            return;
        }
        boolean session = true;
        while (session) {
            System.out.println("\n--- Admin Functions ---");
            System.out.println("1. Add Menu Item");
            System.out.println("2. Remove Menu Item");
            System.out.println("3. Edit Menu Item");
            System.out.println("4. View Sales Report");
            System.out.println("5. Logout");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter price: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("Is it Food or Drink? ");
                    String type = scanner.nextLine();
                    menu.addItem(type.equalsIgnoreCase("Drink") ? new Drink(name, price) : new Food(name, price));
                    break;
                case "2":
                    menu.displayMenu();
                    System.out.print("Enter index to remove: ");
                    menu.removeItem(Integer.parseInt(scanner.nextLine()) - 1);
                    break;
                case "3":
                    menu.displayMenu();
                    System.out.print("Enter index to edit: ");
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new price: ");
                    double newPrice = Double.parseDouble(scanner.nextLine());
                    menu.editItem(idx, newName, newPrice);
                    break;
                case "4":
                    menu.showSalesReport(allOrders);
                    break;
                case "5":
                    session = false;
                    break;
            }
        }
    }

    private static void saveOrdersToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("orders.ser"))) {
            out.writeObject(allOrders);
        } catch (IOException e) {
            System.out.println("Failed to save orders.");
        }
    }

    private static void loadOrdersFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("orders.ser"))) {
            allOrders = (List<Order>) in.readObject();
        } catch (Exception e) {
            allOrders = new ArrayList<>();
        }
    }
}
