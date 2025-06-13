import java.util.List;
import java.util.Scanner;

public class OrderEditor {
    public static void editOrder(Order order, Scanner scanner) {
        while (true) {
            List<OrderItem> items = order.getOrderItems();
            if (items.isEmpty()) {
                System.out.println("No items in order.");
                return;
            }

            System.out.println("\n--- Edit Order ---");
            for (int i = 0; i < items.size(); i++) {
                OrderItem item = items.get(i);
                System.out.printf("%d. %s x%d\n", i + 1, item.getMenuItem().getName(), item.getQuantity());
            }
            System.out.println("0. Finish Editing");
            System.out.print("Select item number to edit/remove: ");
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

            OrderItem current = items.get(choice - 1);
            System.out.printf("Selected %s x%d\n", current.getMenuItem().getName(), current.getQuantity());
            System.out.print("Enter new quantity (0 to remove): ");
            int qty;
            try {
                qty = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity.");
                continue;
            }

            if (qty == 0) {
                order.removeItem(choice - 1);
                System.out.println("Item removed.");
            } else {
                order.updateItemQuantity(choice - 1, qty);
                System.out.println("Quantity updated.");
            }
        }
        System.out.println("Subtotal: RM " + (order.getTotal() / 100.0));
    }
}
