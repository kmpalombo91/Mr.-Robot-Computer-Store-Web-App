package bl.factory.generic.dao;

import bl.singleton.dao.generic.Item;
import java.util.Collection;
import bl.singleton.dao.generic.Keyable;
import bl.singleton.dao.generic.Product;
import java.sql.SQLException;

public class ShoppingCart {
    FactoryStore fs = new FactoryStore<String, Keyable>();
    Product<Keyable> cart;
    public ShoppingCart() {
        fs = new FactoryStore<String, Keyable>();
        cart = fs.createDao();
    }
    
    public void addToCart(Item item) throws SQLException {
        this.cart.create(item);
    }
    
    public void removeFromCart(Item item) throws SQLException {
        this.cart.delete(item);
    }
    
    public Collection<Keyable> showCart() {
        return this.cart.findAll();
    }
    
    public Collection<Keyable> checkOut() {
        //this.cart.updateDBase();
        return this.cart.findAll();
    }
}
