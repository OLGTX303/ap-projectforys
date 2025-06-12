

public class MenuInitializer {
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
