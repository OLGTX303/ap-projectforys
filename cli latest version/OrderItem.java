

import java.util.List;

public class OrderItem {
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
