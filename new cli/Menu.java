import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Menu {
    private List<MenuItem> items = new ArrayList<>();
    public void addItem(MenuItem item) { items.add(item); }
    public void removeItem(int index) { if (index >= 0 && index < items.size()) items.remove(index); }
    public void editItem(int index, String name, double price) {
        if (index >= 0 && index < items.size()) {
            items.get(index).setName(name);
            items.get(index).setPrice(price);
        }
    }
    public void displayMenu() {
        System.out.println("\n--- Today's Menu ---");
        int i = 1;
        for (MenuItem item : items) {
            System.out.printf("%d. %s (%s) - RM%.2f\n", i++, item.getName(), item.getType(), item.getPrice());
        }
    }
    public MenuItem getItem(int index) { return items.get(index); }
    public void searchItem(String name) {
    name = name.trim().toLowerCase();
    boolean found = false;

    System.out.println("\nSearch Results:");
    int index = 1;
    for (MenuItem item : items) {
        if (item.getName().toLowerCase().contains(name)) {
            System.out.printf("%d. %s (%s) - RM%.2f\n", index++, item.getName(), item.getType(), item.getPrice());
            found = true;
        }
    }

    if (!found) {
        System.out.println("No matching dish found.");
    }
}

    public void showSalesReport(List<Order> orders) {
        Map<String, Integer> sales = new HashMap<>();
        for (Order order : orders) {
            for (OrderItem oi : order.getItems()) {
                String name = oi.getItem().getName();
                sales.put(name, sales.getOrDefault(name, 0) + oi.getQuantity());
            }
        }
        System.out.println("\n--- Sales Report ---");
        for (Map.Entry<String, Integer> e : sales.entrySet()) {
            System.out.printf("%s - Sold: %d\n", e.getKey(), e.getValue());
        }
    }
    public void loadFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0].trim();
                double price = Double.parseDouble(parts[1].trim());

                MenuItem item = name.toLowerCase().contains("milo") || name.toLowerCase().contains("tea") || name.toLowerCase().contains("drink")
                        ? new Drink(name, price)
                        : new Food(name, price);

                addItem(item);
            }
        } catch (IOException e) {
            System.out.println("Menu file not found.");
        }
    }
    public int getSize() {
        return items.size();
    }
}
