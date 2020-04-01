package bl.singleton.dao.generic;

import java.io.Serializable;
import java.util.Objects;

public class Item
        implements Keyable<String>, Comparable<Item>, Categorable<String> {
    private String category;
    private String id;
    private String description;
    private int width;
    private int height;
    private int qty;
    private String desc;
    private double price;
    private String fakeImage = "resources/images/iPhone11.png";

    public Item() {
    }
    
    public Item(String id) {
        this.id = id;
    }

    public Item(String category, String id, String description, int width, int height, int qty, String desc, double price, String fakeImage) {
        this.category = category;
        this.id = id;
        this.description = description;
        this.width = width;
        this.height = height;
        this.qty = qty;
        this.desc = desc;
        this.price = price;
        this.fakeImage = fakeImage;
    }
    
    public Item(String id, Item i) {
        this.id = id;
        this.category = i.category;
        this.description = i.description;
        this.desc = i.desc;
        this.width = i.width;
        this.height = i.height;
        this.qty = i.qty;
        this.price = i.price;
        this.fakeImage = i.fakeImage;
    }
    
    public Item(String category, String id, String description) {
        this.id = id;
        this.description = description;
        this.category = category;
    }

    public String getFakeImage() {
        return fakeImage;
    }

    public void setFakeImage(String fakeImage) {
        this.fakeImage = fakeImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getKey() {
        return getId();
    }

    @Override
    public void setKey(String key) {
        setKey(key);
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String t) {
        this.category = t;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int compareTo(Item o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Item{" + "category=" + category + ", id=" + id + ", description=" + description + ", width=" + width + ", height=" + height + ", qty=" + qty + ", desc=" + desc + ", price=" + price + ", fakeImage=" + fakeImage + '}';
    }
    
    
}
