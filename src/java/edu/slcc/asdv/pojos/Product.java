package edu.slcc.asdv.pojos;

import java.util.ArrayList;

public class Product {
    private String id;
    private String name;
    private String type;
    private int width;
    private int height;
    private int qty;
    private String desc;
    private double price;
    ArrayList<Byte> image;
    private String fakeImage = "resources/images/iPhone11.png";
    /**
     * POJO for Product with standard setters/getters
     */
    public Product() {}
    
    public Product(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public void setFakeImage(String fakeImage) {
        this.fakeImage = fakeImage;
    }

    public String getFakeImage() {
        return fakeImage;
    }

    public ArrayList<Byte> getImage() {
        return image;
    }

    public void setImage(ArrayList<Byte> image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public String toString() {
        return "Product{" + "id=" + id + ", type=" + type + ", width=" + width + ", height=" + height + ", qty=" + qty + ", desc=" + desc + ", price=" + price + ", image=" + image + ", fakeImage=" + fakeImage + '}';
    }
}
