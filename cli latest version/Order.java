

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
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
