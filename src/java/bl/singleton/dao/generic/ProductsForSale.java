package bl.singleton.dao.generic;

import edu.slcc.asdv.utils.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProductsForSale<K, V extends Keyable>
        implements Product<Keyable> {

    private Map<K, V> map = new HashMap<K, V>();
    private Database db = new Database();

    @Override
    public void create(Keyable t) throws SQLException {
        map.put((K) t.getKey(), (V) t);
        String id = "";
        Item i = (Item) new Item(t.getKey().toString());
        Statement stmt = db.connection().createStatement();
        ResultSet result = stmt.executeQuery("SELECT id FROM products;");
        while (result.next()) {
            id += result.getString("id") + " ";
        }
        if (!id.contains(i.getId())) {
            stmt.executeUpdate("INSERT INTO products VALUES ('" + i.getId() + "', '" + i.getCategory() + "', '"
                    + i.getDescription() + "', " + i.getWidth() + ", " + i.getHeight()
                    + ", " + i.getQty() + ", '" + i.getDescription() + "', " + i.getPrice() + ");");
        }
    }

    @Override
    public void delete(Keyable t) throws SQLException {
        map.remove((K) t.getKey());
        Statement stmt = db.connection().createStatement();
        Item i = (Item) new Item(t.getKey().toString());
        stmt.executeUpdate("DELETE FROM producs WHERE id = '" + i.getId() + "'");
    }

    @Override
    public void update(Keyable t) throws SQLException {
        map.replace((K) t.getKey(), (V) map.get((K) t.getKey()));
        Statement stmt = db.connection().createStatement();
        Item i = (Item) new Item(t.getKey().toString());
        stmt.executeUpdate("UPDATE products SET id = '" + i.getId() + "', type= '" + i.getCategory() + "', name= '"
                + i.getDescription() + "', width= " + i.getWidth() + ", height= " + i.getHeight()
                + ", qty= " + i.getQty() + ", description= '" + i.getDesc() + "', price= " + i.getPrice() + " WHERE id= '" + i.getId() + "';");
    }

    @Override
    public Keyable find(Keyable t) {
        return map.get((K) t.getKey());
    }

    @Override
    public Collection<Keyable> findAll() {
        return (Collection<Keyable>) map.values();
    }

    public void updateQty(String buy) throws SQLException {
        Statement stmt = db.connection().createStatement();
        stmt.executeUpdate("UPDATE products SET qty = qty - 1 WHERE name='" + buy + "'");
    }
}
