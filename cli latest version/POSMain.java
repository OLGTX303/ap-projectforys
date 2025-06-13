

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class POSMain {
    private static Scanner scanner = new Scanner(System.in);
    private static int orderIDCounter = 1001;
    private static Admin admin = new Admin("admin123");

    public static void main(String[] args) {
        Menu menu = new Menu();
        MenuStorage.loadMenuFromFile(menu, "menu.txt");

        System.out.println("=== Welcome to the CLI POS System ===");

        while (true) {
            Order currentOrder = createNewOrder();
            addItemsToOrder(menu, currentOrder);

            System.out.print("Edit order items? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                OrderEditor.editOrder(currentOrder, scanner);
            }

            System.out.print("Enter payment method (e.g., Cash, Card): ");
            currentOrder.setPaymentMethod(scanner.nextLine());

            System.out.print("Enter admin password to finish order: ");
            String password = scanner.nextLine();

            if (admin.checkAuthentication(password)) {
                currentOrder.saveReceiptToFile();
                System.out.println("✅ Order saved. Receipt written to file: receipt_order_" + currentOrder.getOrderID() + ".txt");
            } else {
                System.out.println("❌ Incorrect password. Order not saved.");
            }

            System.out.print("Create new order? (y/n): ");
            if (!scanner.nextLine().equalsIgnoreCase("y")) break;
        }

        // Persist menu items on exit
        MenuStorage.saveMenuToFile(menu, "menu.txt");

        System.out.println("Thank you for using the CLI POS System. Goodbye!");
    }

    private static Order createNewOrder() {
        Customer customer = new Customer();

        System.out.print("Enter customer name: ");
        customer.setCustomerName(scanner.nextLine());

        System.out.print("Enter phone number: ");
        try {
            customer.setPhoneNo(Integer.parseInt(scanner.nextLine()));
        } catch (NumberFormatException e) {
            customer.setPhoneNo(0);
        }

        System.out.print("Enter address: ");
        customer.setAddress(scanner.nextLine());

        Order order = new Order();
        order.setOrderID(orderIDCounter++);
        order.setCustomerInfo(customer);
        return order;
    }

    private static void addItemsToOrder(Menu menu, Order order) {
        while (true) {
            System.out.println("\n--- MENU ---");
            List<MenuItem> items = menu.getItems();
            for (int i = 0; i < items.size(); i++) {
                MenuItem item = items.get(i);
                System.out.printf("%d. %s - RM %.2f (%s)\n", i + 1, item.getName(), item.getPrice() / 100.0, item.getDescription());
            }

            System.out.print("Select item number (0 to finish): ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            if (choice == 0) break;
            if (choice < 1 || choice > items.size()) {
                System.out.println("Invalid item number.");
                continue;
            }

            MenuItem selectedItem = items.get(choice - 1);

            System.out.print("Enter quantity: ");
            int qty;
            try {
                qty = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity.");
                continue;
            }

            order.addNewOrder(new OrderItem(selectedItem, qty, new ArrayList<>()));
            System.out.println("Added: " + selectedItem.getName() + " x" + qty);
        }

        System.out.println("Subtotal: RM " + (order.getTotal() / 100.0));
    }
}
