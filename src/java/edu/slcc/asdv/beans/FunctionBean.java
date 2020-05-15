package edu.slcc.asdv.beans;
// <editor-fold defaultstate="collapsed" desc="Imports">

import bl.factory.generic.dao.ShoppingCart;
import bl.singleton.dao.generic.Item;
import bl.singleton.dao.generic.Keyable;
import edu.slcc.asdv.utils.Database;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
// </editor-fold>

@Named(value = "functionBean")
@SessionScoped
public class FunctionBean implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Variables">
    Map<String, String> searchResults = new LinkedHashMap<>();
    private List<String> keys = new ArrayList<>();
    private String search;
    private ProductsBean pb;
    ArrayList<Item> items = new ArrayList<>();
    private ShoppingCart cart = new ShoppingCart();
    // </editor-fold>

    public FunctionBean() throws SQLException {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        pb = (ProductsBean) elContext.getELResolver().getValue(elContext, null, "productsBean");
    }
// <editor-fold defaultstate="collapsed" desc="Setters and Getters"> 

    public ProductsBean getPb() {
        return pb;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public Map<String, String> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(Map<String, String> searchResults) {
        this.searchResults = searchResults;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
    // </editor-fold>

    /**
     * Searches list of products for any trace of the user's searched term in
     * the product name. Puts the product/products found in a Map. Redirects to
     * a page loaded with the products retrieved, along with the item's buy
     * page.
     *
     * @throws SQLException
     * @throws IOException
     */
    public void searchInventory() throws SQLException, IOException {
        searchResults.clear();
        for (Keyable e : pb.getProducts().findAll()) {
            Item i = (Item) pb.getProducts().find((Item) new Item(e.getKey().toString()));
            if (i.getDescription().toLowerCase().contains(search.toLowerCase())) {
                searchResults.put(i.getId(), i.getDescription());
            }
        }
        keys = new ArrayList<>(searchResults.keySet());
        FacesContext.getCurrentInstance().getExternalContext().redirect("searchResult.xhtml");
    }

    public void addToCart() throws SQLException {
        Item product = pb.getProduct();
        cart.addToCart(new Item(product.getId(), product));
        product.setQty(product.getQty() - 1);
    }

    public void removeFromCart(Item i) throws SQLException {
        Item product = (Item) pb.getProducts().find(new Item(i.getId(), i));
        cart.removeFromCart(new Item(i.getId(), i));
        items.remove(i);
        product.setQty(product.getQty() + 1);
    }

    public void clearCart() throws SQLException {
        for (int i = 0; i < items.size(); i++) {
            Item product = (Item) pb.getProducts().find(new Item(items.get(i).getId(), items.get(i)));
            product.setQty(product.getQty() + 1);
        }
        items = new ArrayList<>();
        cart = new ShoppingCart();
    }

    public void showCart() throws IOException {
        items = new ArrayList<>();
        for (Keyable e : cart.showCart()) {
            Item i = new Item(e.getKey().toString(), (Item) e);
            items.add(i);
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("cart.xhtml");
    }

    public int getTotalItems() {
        int total = 0;
        for (Item item : items) {
            total += item.getCount();
        }
        return total;
    }

    public String getTotalPrice() {
        double total = 0;
        for (Item item : items) {
            total += item.getPrice();
        }
        return String.format("%.2f", total);
    }

    public void add(Item i) {
        for (Item item : items) {
            if (item.getId().equals(i.getId())) {
                Item product = (Item) pb.getProducts().find(new Item(item.getId(), item));
                product.setQty(product.getQty() - 1);
                item.setCount(item.getCount() + 1);
                String price = String.format("%.2f", i.getPrice() * item.getCount());
                item.setPrice(Double.valueOf(price));
            }
        }
    }

    public void remove(Item i) throws SQLException {
        for (Item item : items) {
            if (item.getId().equals(i.getId())) {
                String price = String.format("%.2f", i.getPrice() / item.getCount());
                item.setPrice(Double.valueOf(price));
                if (item.getCount() > 1) {
                    item.setCount(item.getCount() - 1);
                    Item product = (Item) pb.getProducts().find(new Item(item.getId(), item));
                    product.setQty(product.getQty() + 1);
                } else {
                    break;
                }
            }
        }
    }
    
    public String checkout() throws SQLException {
        for (Item item : items) {
            pb.getProducts().updateQty(item.getId(), item.getCount());
        }
        items.clear();
        cart = new ShoppingCart();
        return "taskFlowReturnDone";
    }
}
