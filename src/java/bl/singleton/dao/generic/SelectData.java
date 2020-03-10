package bl.singleton.dao.generic;

import edu.slcc.asdv.pojos.Product;
import edu.slcc.asdv.utils.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SelectData {
    private static Database database = new Database();

    public static ArrayList<Product> selectAllProducts() throws SQLException {
        ArrayList<Product> list = new ArrayList();
        Product product = new Product();
        String[] images = {"resources/images/iPhone11.png", "resources/images/galaxys10.jpg", "resources/images/sony.jpg", "resources/images/pixel4xl.jpg",
            "resources/images/omen.jpg", "resources/images/acer.jpg", "resources/images/dell.jpg", "resources/images/msi.jpg"};
        Statement stmt = database.connection().createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM products");
        int count = 0;
        while (result.next()) {
            for (int i = 1; i <= 8;) {
                product = new Product();
                product.setId(result.getString(i));
                product.setType(result.getString(i + 1));
                product.setName(result.getString(i + 2));
                product.setWidth(result.getInt(i + 3));
                product.setHeight(result.getInt(i + 4));
                product.setQty(result.getInt(i + 5));
                product.setDesc(result.getString(i + 6));
                product.setPrice(result.getDouble(i + 7));
                product.setFakeImage(images[count]);
                count++;
                list.add(product);
                i += 8;
            }
        }
        return list;
    }
}
