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
        if (!map.containsKey((K)t.getKey())) {
          map.put((K) t.getKey(), (V) t);  
        }
        String id = "";
        Item i = (Item) new Item(t.getKey().toString(), (Item)t);
        Statement stmt = db.connection().createStatement();
        ResultSet result = stmt.executeQuery("SELECT id FROM products;");
        while (result.next()) {
            id += result.getString("id") + " ";
        }
        String[] ar = id.split(" ");
        boolean is = false;
        for (String n : ar) {
            if (n.equals(i.getId())) {
                is = true;
            }
        }
        if (!is) {
            stmt.executeUpdate("INSERT INTO products VALUES ('" + i.getId() + "', '" + i.getCategory() + "', '"
                    + i.getDescription() + "', " + i.getWidth() + ", " + i.getHeight()
                    + ", " + i.getQty() + ", '" + i.getDesc() + "', " + (double)i.getPrice() + ", '"+ i.getFakeImage() +"');");
        }
    }

    @Override
    public void delete(Keyable t) throws SQLException {
        Statement stmt = db.connection().createStatement();
        stmt.executeUpdate("DELETE FROM products WHERE id = '" + t.getKey().toString() + "'");
        map.remove((K) t.getKey());
    }

    @Override
    public void update(Keyable t) throws SQLException {
        Statement stmt = db.connection().createStatement();
        Item i = (Item) new Item(t.getKey().toString(), (Item) t);
        stmt.executeUpdate("UPDATE products SET id = '" + i.getId() + "', type= '" + i.getCategory() + "', name= '"
                + i.getDescription() + "', width= " + i.getWidth() + ", height= " + i.getHeight()
                + ", qty= " + i.getQty() + ", description= '" + i.getDesc() + "', price= " + i.getPrice() + " WHERE id= '" + i.getId() + "';");
        map.replace((K) t.getKey(), (V) i);
    }

    @Override
    public Keyable find(Keyable t) {
        return map.get((K) t.getKey());
    }

    @Override
    public Collection<Keyable> findAll() {
        return (Collection<Keyable>) map.values();
    }

    public void updateQty(String id, int count) throws SQLException {
        Statement stmt = db.connection().createStatement();
        stmt.executeUpdate("UPDATE products SET qty = qty - " + count + " WHERE id='" + id + "'");
        System.out.println("UPDATE products SET qty = qty - " + count + " WHERE id='" + id + "'");
    }
}
