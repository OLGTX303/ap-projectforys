

public class MenuItem {
    private String name;
    private int price; // in cents
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
}
