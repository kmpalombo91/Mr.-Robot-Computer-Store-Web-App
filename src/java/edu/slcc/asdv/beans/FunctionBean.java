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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
// </editor-fold>

@Named(value = "functionBean")
@SessionScoped
public class FunctionBean implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Variables">
    private WarehouseSingleton ws;
    private ProductsForSale<String, Keyable> products;
    Map<String, String> searchResults = new LinkedHashMap<>();
    private List<String> keys = new ArrayList<>();
    private String search;
    private String buy;
    // </editor-fold>

    public FunctionBean() {
        ws = InitializationBean.getWarehouse();
        products = ws.getProducts();
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

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
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
        for (Keyable e : products.findAll()) {
            Item i = (Item) products.find((Item) new Item(e.getKey().toString()));
            if (i.getDescription().toLowerCase().contains(search.toLowerCase())) {
                searchResults.put(i.getId(), i.getDescription());
            }
        }
        keys = new ArrayList<>(searchResults.keySet());
        FacesContext.getCurrentInstance().getExternalContext().redirect("searchResult.xhtml");
    }

    public String purchase() throws SQLException, IOException {
        products.updateQty(buy);
        return "bought";
    }
}
