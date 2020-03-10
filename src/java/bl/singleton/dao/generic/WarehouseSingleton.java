package bl.singleton.dao.generic;

import static bl.singleton.dao.generic.SelectData.selectAllProducts;
import java.sql.SQLException;
import java.util.Collection;

public class WarehouseSingleton<K, V extends Keyable> {
    private static ProductsForSale<String, Keyable> productsForSale;
    private static WarehouseSingleton warehouse;

    private WarehouseSingleton() throws SQLException {
        productsForSale = new ProductsForSale();
        for (edu.slcc.asdv.pojos.Product e : selectAllProducts()) {
            productsForSale.create((Item)new Item(e.getType(), e.getId(), e.getName(), e.getWidth(),
                    e.getHeight(), e.getQty(), e.getDesc(), e.getPrice(), e.getFakeImage()));
        }
    }

    public static WarehouseSingleton instantiateWarehouse() throws SQLException {
        if (warehouse == null) {
            warehouse = new WarehouseSingleton();
        }
        return warehouse;
    }
    public ProductsForSale<String, Keyable> getProducts() {
        return productsForSale;
    }

    public static void main(String[] args) throws SQLException {
        WarehouseSingleton ws = WarehouseSingleton.instantiateWarehouse();
        ProductsForSale<String, Keyable> pfs = ws.getProducts();
        pfs.create(new Item("phone", "iphone1 serial number", "good"));
        pfs.create(new Item("phone", "iphone2 serial number", "very good"));
        pfs.create(new Item("phone", "android1 serial number", "very very good"));
        pfs.create(new Item("computer", "HP serial number", "good PC"));
        pfs.create(new Item("computer", "Macbook Pro serial number", "good laptop"));
        Collection<Keyable> c = pfs.findAll();
        System.out.println(c);
        Item item = new Item("phone", "iphone2 serial number", "bad, very bad");
        pfs.update(item);
        System.out.println(pfs.find(item));
        pfs.delete(item);
        System.out.println(pfs.findAll());
    }

}
