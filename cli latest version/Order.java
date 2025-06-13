

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

    public void updateItemQuantity(int index, int quantity) {
        if (index < 0 || index >= orderItems.size()) return;
        OrderItem current = orderItems.get(index);
        orderItems.set(index, new OrderItem(current.getMenuItem(), quantity, current.getSideOrders()));
        recalculateTotal();
    }

    public void removeItem(int index) {
        if (index < 0 || index >= orderItems.size()) return;
        orderItems.remove(index);
        recalculateTotal();
    }

    private void recalculateTotal() {
        total = 0;
        for (OrderItem i : orderItems) {
            total += i.getMenuItem().getPrice() * i.getQuantity();
        }
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
