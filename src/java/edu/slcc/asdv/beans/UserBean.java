package edu.slcc.asdv.beans;

import edu.slcc.asdv.pojos.User;
import edu.slcc.asdv.utils.Database;
import edu.slcc.asdv.utils.DESUtil;
import edu.slcc.asdv.utils.UserKey;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {
// <editor-fold defaultstate="collapsed" desc="Variables">
    private User users;
    private String user;
    private static int id = 0;
    private String pass;
    private UserKey u;
    private byte[] ar;
    private boolean isLoggedIn = false;
    private String encryptPass = "";
    Database database = new Database();
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        u = new UserKey(user, pass);
        ar = u.StringToKey(u.keyToString());
        this.pass = pass;
    }

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
// </editor-fold>

    /**
     * Sets User object to a new User with the user and pass values entered by the current user.
     * SELECTS all usernames and passwords from the table via query, and sets them each into
     * their own respective arrays.
     * Encrypts the user's password to match the other passwords in the array, and checks
     * for equality between the usernames and passwords.
     * @return value that either redirects to a login page that signals a successful login, 
     * or a register page that gives the user a form to fill out and create an account.
     * @throws SQLException 
     */
    public String findALL() throws SQLException {
        users = new User(user, pass);
        String userN = "", passW = "", what = "";
        Statement stmt = database.connection().createStatement();
        ResultSet result = stmt.executeQuery("SELECT username, password FROM accounts");
        while (result.next()) {
            userN += result.getString("username") + " ";
            passW += result.getString("password") + " ";
        }
        String[] userss = userN.split(" ");
        String[] passes = passW.split(" ");
        id = userss.length;
        String passE = DESUtil.encrypt(this.users.getPassword(), ar);
        for (int i = 0; i < userss.length; i++) {
            if (userss[i].equals(this.users.getUsername()) && passes[i].trim().equals(passE)) {
                isLoggedIn = true;
                what = "login";
            }
        }
        if (!isLoggedIn) {
            what = "register";
        }
        return what;
    }

    /**
     * Encrypts the password of the user and inserts the incrementing id value, along
     * with the username and password of the new user into the account table.
     * @return value that redirects to a successful login page.
     * @throws SQLException 
     */
    public String register() throws SQLException {
        users = new User(user, pass);
        id++;
        encryptPass = DESUtil.encrypt(u.getPassword(), ar);
        Statement stmt = database.connection().createStatement();
        stmt.executeUpdate("INSERT INTO accounts VALUES (" + id + ", '" + users.getUsername() + "', '" + encryptPass + "')");
        return "login";
    }
}
