import java.io.Serializable;

abstract class MenuItem implements Serializable {
    protected String name;
    protected double price;
    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public abstract String getType();
}
