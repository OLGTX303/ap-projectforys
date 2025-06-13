import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MenuStorage {
    public static void loadMenuFromFile(Menu menu, String filePath) {
        Path path = Path.of(filePath);
        if (!Files.exists(path)) {
            for (MenuItem item : MenuInitializer.getAllMenuItems()) {
                menu.addItem(item);
            }
            saveMenuToFile(menu, filePath);
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                if (parts.length < 4) continue;
                MenuItem item = new MenuItem();
                item.setName(parts[0]);
                try {
                    item.setPrice(Integer.parseInt(parts[1]));
                } catch (NumberFormatException e) {
                    item.setPrice(0);
                }
                item.setDescription(parts[2]);
                item.setImagePath(parts[3]);
                menu.addItem(item);
            }
        } catch (IOException e) {
            System.out.println("Error loading menu: " + e.getMessage());
        }
    }

    public static void saveMenuToFile(Menu menu, String filePath) {
        Path path = Path.of(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (MenuItem item : menu.getItems()) {
                writer.write(item.getName() + "|" + item.getPrice() + "|" + item.getDescription() + "|" + item.getImagePath());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving menu: " + e.getMessage());
        }
    }
}
