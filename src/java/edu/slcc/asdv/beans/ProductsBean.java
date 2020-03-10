package edu.slcc.asdv.beans;
// <editor-fold defaultstate="collapsed" desc="Imports">

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
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;
// </editor-fold>

@Named(value = "productsBean")
@SessionScoped
public class ProductsBean implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Variables">
    private WarehouseSingleton ws;
    private ProductsForSale<String, Keyable> products;
    private Item product;
    private List<String> values = new ArrayList<>();
    private MenuModel model = new DefaultMenuModel();
    private MenuModel model2 = new DefaultMenuModel();
// </editor-fold>

    public ProductsBean() throws SQLException {
        ws = InitializationBean.getWarehouse();
        products = ws.getProducts();
        product = (Item) products.find(new Item("1"));
        for (Keyable e : products.findAll()) {
            Item i = (Item) products.find(new Item(e.getKey().toString()));
            values.add(i.getDescription());
        }
        populateMenu();
    }
// <editor-fold defaultstate="collapsed" desc="Setters and Getters">

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

    public MenuModel getModel() {
        return model;
    }

    public MenuModel getModel2() {
        return model2;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
    // </editor-fold>

    /**
     * Finds the product with the specified id in the parameter/redirects the
     * page to index loaded with new product values.
     *
     * @param id key value determining the product
     * @throws IOException
     */
    public void changeProduct(String id) throws IOException {
        product = (Item) products.find(new Item(id));
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/index.xhtml");
    }

    /**
     * Finds the index of the current product/uses changeProduct method to
     * redirect the page to index loaded with product values found at position:
     * index + 1.
     *
     * @throws IOException
     */
    public void getNext() throws IOException {
        List<String> list = new ArrayList<>();
        for (Keyable e : products.findAll()) {
            list.add(e.getKey().toString());
        }
        int index = list.indexOf(product.getId());
        if (index < list.size() - 1) {
            changeProduct(list.get(index + 1));
        } else {
            changeProduct(list.get(0));
        }
    }

    /**
     * Finds the index of the current product/uses changeProduct method to
     * redirect the page to index loaded with product values found at position:
     * index - 1.
     *
     * @throws IOException
     */
    public void getPrevious() throws IOException {
        List<String> list = new ArrayList<>();
        for (Keyable e : products.findAll()) {
            list.add(e.getKey().toString());
        }
        int index = list.indexOf(product.getId());
        if (index > 0) {
            changeProduct(list.get(index - 1));
        } else {
            changeProduct(list.get(list.size() - 1));
        }
    }

    public void populateMenu() {
        DefaultMenuItem item = new DefaultMenuItem();
        for (Keyable e : products.findAll()) {
            Item i = (Item) products.find(new Item(e.getKey().toString()));
            if (e.getKey().toString().equals("5") || e.getKey().toString().equals("6")
                    || e.getKey().toString().equals("7") || e.getKey().toString().equals("8")) {
                item = new DefaultMenuItem(i.getDescription());
                item.setCommand("#{productsBean.changeProduct('" + i.getId() + "')}");
                model.addElement(item);
            } else if (e.getKey().toString().equals("1") || e.getKey().toString().equals("2")
                    || e.getKey().toString().equals("3") || e.getKey().toString().equals("4")) {
                item = new DefaultMenuItem(i.getDescription());
                item.setCommand("#{productsBean.changeProduct('" + i.getId() + "')}");
                model2.addElement(item);
            }
        }
    }
}
