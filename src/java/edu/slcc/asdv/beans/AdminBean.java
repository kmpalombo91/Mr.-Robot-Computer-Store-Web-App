package edu.slcc.asdv.beans;
// <editor-fold defaultstate="collapsed" desc="Imports">
import bl.singleton.dao.generic.Item;
import bl.singleton.dao.generic.Keyable;
import bl.singleton.dao.generic.ProductsForSale;
import bl.singleton.dao.generic.WarehouseSingleton;
import edu.slcc.asdv.utils.Database;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
// </editor-fold>

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
    private Part image;
    private String file;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
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
        } else {
            temp = "template/contentAdmin.xhtml";
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/admin-function.xhtml");
    }

    public String doUpload() throws FileNotFoundException, SQLException, IOException {
        InputStream stream = image.getInputStream();
        Database db = new Database();
        PreparedStatement pre = db.connection().prepareStatement("INSERT INTO files VALUES (?,?)");
        pre.setString(1, Paths.get(image.getSubmittedFileName()).getFileName().toString());
        pre.setBinaryStream(2, (InputStream) stream, (int) image.getSize());
        pre.executeUpdate();
        admin = "ADMIN has successfully uploaded PNG image " + Paths.get(image.getSubmittedFileName()).getFileName().toString() + " to our system!";
        return "confirmation";
    }

    public String[] getFiles() throws SQLException {
        Database db = new Database();
        Statement stmt = db.connection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name FROM files WHERE name LIKE '%.pdf%'");
        String p = "";
        while (rs.next()) {
            p += rs.getString("name") + " ";
        }
        String[] files = p.split(" ");
        return files;
    }

    public void selectFile() throws IOException, SQLException {
        Database db = new Database();
        Statement stmt = db.connection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT file FROM files WHERE name='" + file + "'");
        while (rs.next()) {
            InputStream stream = rs.getBinaryStream("file");
            Files.copy(stream, Paths.get("C:\\Users\\Kamryn\\Desktop\\School\\Spring 2020\\Web App Development III\\mp1\\mp1\\web\\resources\\files\\" + file), StandardCopyOption.REPLACE_EXISTING);
            int len = stream.available();
            byte[] bt = new byte[len];
            FileOutputStream fout = new FileOutputStream(new File(file));
            while (stream.read(bt) > 0) {
                fout.write(bt);
            }
            fout.close();
        }
    }

    public void doDownload() throws IOException, InterruptedException {
        Thread.sleep(1000);
        FacesContext.getCurrentInstance().getExternalContext().redirect("DownloadServlet?name=" + file);
    }
}
