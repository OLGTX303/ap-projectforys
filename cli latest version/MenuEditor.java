import java.util.List;
import java.util.Scanner;

public class MenuEditor {
    public static void editMenu(Menu menu, Scanner sc) {
        while (true) {
            System.out.println("\n--- MENU EDITOR ---");
            List<MenuItem> items = menu.getItems();
            for (int i = 0; i < items.size(); i++) {
                MenuItem item = items.get(i);
                System.out.printf("%d. %s - RM %.2f (%s)\n", i + 1, item.getName(), item.getPrice() / 100.0, item.getDescription());
            }
            System.out.println("a. Add item");
            System.out.println("d. Delete item");
            System.out.println("q. Quit editor");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("q")) break;
            if (choice.equalsIgnoreCase("a")) {
                addItem(menu, sc);
            } else if (choice.equalsIgnoreCase("d")) {
                deleteItem(menu, sc);
            }
        }
    }

    private static void addItem(Menu menu, Scanner sc) {
        System.out.print("Item name: ");
        String name = sc.nextLine();
        System.out.print("Price in cents: ");
        int price;
        try {
            price = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid price.");
            return;
        }
        System.out.print("Description: ");
        String desc = sc.nextLine();
        System.out.print("Image URL: ");
        String url = sc.nextLine();
        MenuItem item = new MenuItem();
        item.setName(name);
        item.setPrice(price);
        item.setDescription(desc);
        item.setImagePath(url);
        menu.addItem(item);
        System.out.println("Item added.");
    }

    private static void deleteItem(Menu menu, Scanner sc) {
        System.out.print("Enter item number to delete: ");
        int index;
        try {
            index = Integer.parseInt(sc.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }
        menu.removeItem(index);
        System.out.println("Item removed if number was valid.");
    }
}
