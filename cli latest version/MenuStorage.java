import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MenuStorage {
    private static String md5ForFile(Path path) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] data = Files.readAllBytes(path);
            byte[] digest = md.digest(data);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    public static void loadMenuFromFile(Menu menu, String filePath) {
        Path path = Path.of(filePath);
        Path hashPath = Path.of(filePath + ".md5");
        if (!Files.exists(path) || !Files.exists(hashPath)) {
            for (MenuItem item : MenuInitializer.getAllMenuItems()) {
                menu.addItem(item);
            }
            saveMenuToFile(menu, filePath);
            return;
        }

        try {
            String expectedHash = Files.readString(hashPath).trim();
            String actualHash = md5ForFile(path);
            if (!actualHash.equals(expectedHash)) {
                System.out.println("Menu checksum mismatch. Loading default items.");
                for (MenuItem item : MenuInitializer.getAllMenuItems()) {
                    menu.addItem(item);
                }
                saveMenuToFile(menu, filePath);
                return;
            }
        } catch (IOException e) {
            System.out.println("Error reading checksum: " + e.getMessage());
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
        Path hashPath = Path.of(filePath + ".md5");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (MenuItem item : menu.getItems()) {
                writer.write(item.getName() + "|" + item.getPrice() + "|" + item.getDescription() + "|" + item.getImagePath());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving menu: " + e.getMessage());
        }

        try {
            String hash = md5ForFile(path);
            Files.writeString(hashPath, hash);
        } catch (IOException e) {
            System.out.println("Error writing checksum: " + e.getMessage());
        }
    }
}
