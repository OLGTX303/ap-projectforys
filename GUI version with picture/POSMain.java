// Java Swing POS system with dish cards, image previews from URL, and admin authentication

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

// --- Models ---
class Customer {
    private int customerID;
    private String customerName;
    private int phoneNo;
    private String address;
    public void setCustomerID(int id) { this.customerID = id; }
    public void setCustomerName(String name) { this.customerName = name; }
    public void setPhoneNo(int phone) { this.phoneNo = phone; }
    public void setAddress(String address) { this.address = address; }
    public int getCustomerID() { return customerID; }
    public String getCustomerName() { return customerName; }
    public int getPhoneNo() { return phoneNo; }
    public String getAddress() { return address; }
}

class MenuItem {
    private String name;
    private int price;
    private String description;
    private String imageUrl;
    public void setName(String name) { this.name = name; }
    public void setPrice(int price) { this.price = price; }
    public void setDescription(String desc) { this.description = desc; }
    public void setImagePath(String url) { this.imageUrl = url; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImagePath() { return imageUrl; }
    public void showMainMenu() {
        System.out.println(name + ": RM " + (price / 100.0) + " - " + description);
    }
}

class OrderItem {
    private MenuItem menuItem;
    private int quantity;
    private List<MenuItem> sideOrders;
    public OrderItem(MenuItem item, int qty, List<MenuItem> sides) {
        this.menuItem = item;
        this.quantity = qty;
        this.sideOrders = sides;
    }
    public MenuItem getMenuItem() { return menuItem; }
    public int getQuantity() { return quantity; }
    public List<MenuItem> getSideOrders() { return sideOrders; }
}

class Order {
    private int orderID;
    private Customer customerInfo;
    private Date date = new Date();
    private List<OrderItem> orderItems = new ArrayList<>();
    private int total;
    private String paymentMethod;
    public void setOrderID(int id) { this.orderID = id; }
    public void setCustomerInfo(Customer c) { this.customerInfo = c; }
    public void setPaymentMethod(String method) { this.paymentMethod = method; }
    public int getOrderID() { return orderID; }
    public void addNewOrder(OrderItem item) {
        orderItems.add(item);
        total += item.getMenuItem().getPrice() * item.getQuantity();
    }
    public void saveReceiptToFile() {
        try (FileWriter writer = new FileWriter("receipt_order_" + orderID + ".txt")) {
            writer.write("Receipt - Order ID: " + orderID + "\nDate: " + date + "\nCustomer: " + customerInfo.getCustomerName() + "\n\nItems:\n");
            for (OrderItem item : orderItems) {
                writer.write("- " + item.getMenuItem().getName() + " x" + item.getQuantity() + "\n");
            }
            writer.write("\nTotal: RM " + (total / 100.0) + "\nPayment Method: " + paymentMethod + "\n");
        } catch (IOException e) {
            System.out.println("Error writing receipt: " + e.getMessage());
        }
    }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public int getTotal() { return total; }
}

class Menu {
    private List<MenuItem> items = new ArrayList<>();
    public void addItem(MenuItem item) { items.add(item); }
    public List<MenuItem> getItems() { return items; }
}

class Admin {
    private String adpassword;
    public Admin(String password) {
        this.adpassword = password;
    }
    public void setPassword(String password) {
        this.adpassword = password;
    }
    public boolean checkAuthentication(String input) {
        return input != null && input.equals(adpassword);
    }
}

class MenuInitializer {
    public static MenuItem newItem(String name, int price, String description, String imageUrl) {
        MenuItem item = new MenuItem();
        item.setName(name);
        item.setPrice(price);
        item.setDescription(description);
        item.setImagePath(imageUrl);
        return item;
    }
    public static MenuItem[] getAllMenuItems() {
        return new MenuItem[] {
            newItem("Chicken Burger Combo", 880, "With Fries & Pepsi", "https://marrybrown.com/wp-content/uploads/2019/08/chicken-burger.png"),
            newItem("Hotouch Burger Combo", 1110, "With Fries & Pepsi", "https://marrybrown.com/wp-content/uploads/2019/08/hotouch-burger.png"),
            newItem("Tower Burger Combo", 1380, "With Fries & Pepsi", "https://marrybrown.com/wp-content/uploads/2019/08/Burger-Meal-Tower-Burger-Combo.png"),
            newItem("Nasi Marrybrown", 990, "Fried chicken and rice", "http://marrybrown.com/wp-content/uploads/2019/08/Local-Delight-Nasi-Lemak.png"),
        };
    }
}

// --- GUI ---
class POSGUI {
    private JFrame frame;
    private JPanel cardPanel;
    private JTextArea orderDisplay;
    private Order currentOrder;
    private Menu menu;
    private Admin admin;
    private int orderIDCounter = 1001;

    public POSGUI(Menu menu) {
        this.menu = menu;
        this.admin = new Admin("admin123");
        initComponents();
    }

    private void initComponents() {
        frame = new JFrame("Modern POS Order System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(1200, 700);
        frame.setResizable(false);

        cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(0, 3, 15, 15));
        cardPanel.setBackground(new Color(245, 245, 245));

        orderDisplay = new JTextArea(12, 30);
        orderDisplay.setEditable(false);
        orderDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane orderScroll = new JScrollPane(orderDisplay);

        JButton finishButton = new JButton("Finish Order (Admin Only)");
        finishButton.setBackground(new Color(220, 53, 69));
        finishButton.setForeground(Color.WHITE);
        finishButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        finishButton.setFocusPainted(false);
        finishButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter admin password:");
            if (admin.checkAuthentication(input)) {
                currentOrder.saveReceiptToFile();
                JOptionPane.showMessageDialog(frame, "Saved: receipt_order_" + currentOrder.getOrderID() + ".txt");
                newOrder();
            } else {
                JOptionPane.showMessageDialog(frame, "Access Denied: Incorrect Password");
            }
        });

        JButton addMenuButton = new JButton("+ Add Menu Item");
        addMenuButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        addMenuButton.addActionListener(e -> openMenuEditor());

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rightPanel.add(orderScroll, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.add(addMenuButton);
        buttonPanel.add(finishButton);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
        newOrder();
        createCards();
        frame.setVisible(true);
    }

    private void newOrder() {
        currentOrder = new Order();
        currentOrder.setOrderID(orderIDCounter++);

        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Customer Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Phone No:"));
        panel.add(phoneField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Enter Customer Information", JOptionPane.OK_CANCEL_OPTION);
        Customer c = new Customer();
        if (result == JOptionPane.OK_OPTION) {
            c.setCustomerID(1);
            c.setCustomerName(nameField.getText());
            try {
                c.setPhoneNo(Integer.parseInt(phoneField.getText()));
            } catch (NumberFormatException e) {
                c.setPhoneNo(0);
            }
            c.setAddress(addressField.getText());
        } else {
            c.setCustomerID(1);
            c.setCustomerName("Guest");
        }

        currentOrder.setCustomerInfo(c);
        currentOrder.setPaymentMethod("Cash");
        orderDisplay.setText("Order ID: " + currentOrder.getOrderID() + "\nCustomer: " + c.getCustomerName() + "\n\n");
    }

    private void createCards() {
        cardPanel.removeAll();
        for (MenuItem item : menu.getItems()) {
            cardPanel.add(new ModernPOSCard(item, currentOrder, orderDisplay));
        }
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    private void openMenuEditor() {
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField descField = new JTextField();
        JTextField imgUrlField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Item Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Price (in cents):"));
        panel.add(priceField);
        panel.add(new JLabel("Description:"));
        panel.add(descField);
        panel.add(new JLabel("Image URL:"));
        panel.add(imgUrlField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Add New Menu Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                MenuItem item = new MenuItem();
                item.setName(nameField.getText());
                item.setPrice(Integer.parseInt(priceField.getText()));
                item.setDescription(descField.getText());
                item.setImagePath(imgUrlField.getText());
                menu.addItem(item);
                createCards();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Invalid input.");
            }
        }
    }
}


public class POSMain {
    public static void main(String[] args) {
        Menu menu = new Menu();
        for (MenuItem item : MenuInitializer.getAllMenuItems()) {
            menu.addItem(item);
        }
        SwingUtilities.invokeLater(() -> new POSGUI(menu));
    }
}
