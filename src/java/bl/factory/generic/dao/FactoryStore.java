package bl.factory.generic.dao;

import bl.singleton.dao.generic.Keyable;
import bl.singleton.dao.generic.Product;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FactoryStore<K, V extends Keyable> extends Factory<Keyable> {

    private Map<K, V> map = new HashMap<K, V>();
    @Override
    public Product<Keyable> createDao() {
        // shopping cart for products
        return new Product<Keyable>() {
            @Override
            public void create(Keyable t) {
                // shopping cart doesn't affect the database
                map.put((K) t.getKey(), (V) t);
            }

            @Override
            public void delete(Keyable t) {
                // shopping cart doesn't affect the database
                map.remove((K) t.getKey());
            }

            @Override
            public void update(Keyable t) {
                // shopping cart doesn't affect the database
                map.replace((K) t.getKey(), (V) t);
            }

            @Override
            public Keyable find(Keyable t) {
                // shopping cart doesn't affect the database
                return map.get((K) t.getKey());
            }

            @Override
            public Collection<Keyable> findAll() {
                // shopping cart doesn't affect the database
                return (Collection<Keyable>) map.values();
            }

            /*@Override
            public boolean updateDBase() {
                // shopping cart DOES affect the database
                // update the singleton of available products 
                // or the database if there is no singleton
                return true;
            }*/      
        };
    }
    
}
