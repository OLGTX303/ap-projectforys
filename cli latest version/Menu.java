

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuItem> items = new ArrayList<>();
    public void addItem(MenuItem item) { items.add(item); }
    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) items.remove(index);
    }
    public List<MenuItem> getItems() { return items; }
}
