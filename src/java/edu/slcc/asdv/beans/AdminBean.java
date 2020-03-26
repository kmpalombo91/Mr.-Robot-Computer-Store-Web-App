package edu.slcc.asdv.beans;

import bl.singleton.dao.generic.Item;
import bl.singleton.dao.generic.Keyable;
import bl.singleton.dao.generic.ProductsForSale;
import bl.singleton.dao.generic.WarehouseSingleton;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;

@Named(value = "adminBean")
@SessionScoped
public class AdminBean implements Serializable {
// <editor-fold defaultstate="collapsed" desc="Variables">
    private WarehouseSingleton ws;
    private ProductsForSale<String, Keyable> products;
    private Item product = new Item();
    private List<Item> p = new ArrayList();
    private String action = "Insert";
    private String temp = "template/contentAdmin.xhtml";
    private String admin = "To make changes to the products and for more options, select one of the options from the list on the left!";
// </editor-fold>

    public AdminBean() {
        ws = InitializationBean.getWarehouse();
        products = ws.getProducts();
        for (Keyable e : products.findAll()) {
            Item i = (Item) products.find(new Item(e.getKey().toString()));
            p.add(i);
        }
    }
// <editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
    
    public WarehouseSingleton getWs() {
        return ws;
    }

    public void setWs(WarehouseSingleton ws) {
        this.ws = ws;
    }

    public ProductsForSale<String, Keyable> getProducts() {
        return products;
    }

    public void setProducts(ProductsForSale<String, Keyable> products) {
        this.products = products;
    }
    
    public Item getProduct() {
        return product;
    }

    public void setProduct(Item product) {
        this.product = product;
    }
    
    public List<Item> getP() {
        return p;
    }

    public void setP(List<Item> p) {
        this.p = p;
    }
// </editor-fold>

    public static String checkAdmin(String status) {
        if (status.equals("admin")) {
            return "admin-function";
        }
        return "login";
    }
    
    public String insertProduct() throws SQLException, IOException {
        products.create(product);
        p = new ArrayList();
        for (Keyable e : products.findAll()) {
            Item i = (Item) products.find(new Item(e.getKey().toString()));
            p.add(i);
        }
        admin = "ADMIN has successfully inserted Product: " + this.product.getDescription() + " into our system!";
        return "confirmation";
    }
    
    public String updateProduct() throws SQLException, IOException {
        products.update(product);
        p = new ArrayList();
        for (Keyable e : products.findAll()) {
            Item i = (Item) products.find(new Item(e.getKey().toString()));
            p.add(i);
        }
        admin = "ADMIN has successfully updated Product: " + this.product.getDescription() + " in our system!";
        return "confirmation";
    }
    
    public String deleteProduct() throws SQLException, IOException {
        Item i = (Item) products.find(new Item(product.getId()));
        products.delete(product);
        product = i;
        p = new ArrayList();
        for (Keyable e : products.findAll()) {
            Item j = (Item) products.find(new Item(e.getKey().toString()));
            p.add(j);
        }
        admin = "ADMIN has successfully deleted Product: " + this.product.getDescription() + " from our system!";
        return "confirmation";
    }
    
    public String doAction() throws SQLException, IOException {
        if (action.equals("Insert")) {
            return insertProduct();
        } else if (action.equals("Update")) {
            return updateProduct();
        } else {
            return deleteProduct();
        }
    }
    
    public void clickAction(String a) throws IOException {
        action = a;
        if (!action.equals("Insert") && !action.equals("Update")) {
            temp = "template/content" + action + ".xhtml";
        }
        else {
            temp = "template/contentAdmin.xhtml";
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/admin-function.xhtml");
    }
}
