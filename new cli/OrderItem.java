import java.io.Serializable;

class OrderItem implements Serializable {
    private MenuItem item;
    private int quantity;
    public OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
    public MenuItem getItem() { return item; }
    public int getQuantity() { return quantity; }
    public double getTotal() { return item.getPrice() * quantity; }
}
