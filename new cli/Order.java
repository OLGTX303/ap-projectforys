import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class Order implements Serializable {
    private static int count = 1;
    private int id;
    private Customer customer;
    private List<OrderItem> items;
    private String paymentMethod = "N/A";
    public Order(Customer customer, List<OrderItem> items) {
        this.id = count++;
        this.customer = customer;
        this.items = items;
    }
    public void setPaymentMethod(String method) { this.paymentMethod = method; }
    public double getTotal() {
        return items.stream().mapToDouble(OrderItem::getTotal).sum();
    }
    public void displaySummary() {
        System.out.println("\nOrder Summary:");
        for (OrderItem oi : items) {
            System.out.printf("- %s x%d = RM%.2f\n", oi.getItem().getName(), oi.getQuantity(), oi.getTotal());
        }
        System.out.printf("Total: RM%.2f\n", getTotal());
    }
    public List<OrderItem> getItems() {
    return items;
    }
    public void printReceipt() {
    String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    String timeStr = new SimpleDateFormat("hh:mm a").format(new Date());

    System.out.println("\n--- Receipt ---");
    System.out.println("Order ID: " + id);
    System.out.println("Customer Name: " + customer.getName());
    System.out.println("Phone: " + customer.getPhone());
    System.out.println("Address: " + customer.getAddress());
    System.out.println("Date: " + dateStr);
    System.out.println("Time: " + timeStr);
    System.out.println("\nOrder Items:");
    int counter = 1;
    for (OrderItem oi : items) {
        System.out.printf("%d. %s - %d x RM %.2f\n", counter++, oi.getItem().getName(), oi.getQuantity(), oi.getItem().getPrice());
    }
    System.out.printf("\nTotal Amount: RM %.2f\n", getTotal());
    System.out.println("Payment Method: " + paymentMethod);
    
    // Save to TXT file
    try (PrintWriter writer = new PrintWriter("receipt_" + id + ".txt")) {
        writer.println("Order ID: " + id);
        writer.println("Customer Name: " + customer.getName());
        writer.println("Phone: " + customer.getPhone());
        writer.println("Address: " + customer.getAddress());
        writer.println("Date: " + dateStr);
        writer.println("Time: " + timeStr);
        writer.println();
        writer.println("Order Items:");
        counter = 1;
        for (OrderItem oi : items) {
            writer.printf("%d. %s - %d x RM %.2f%n", counter++, oi.getItem().getName(), oi.getQuantity(), oi.getItem().getPrice());
        }
        writer.printf("%nTotal Amount: RM %.2f%n", getTotal());
        writer.println("Payment Method: " + paymentMethod);
        System.out.println("Receipt saved to receipt_" + id + ".txt");
        } catch (IOException e) {
        System.out.println("Failed to write receipt to file.");
      }
}

}
