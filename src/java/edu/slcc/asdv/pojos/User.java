package edu.slcc.asdv.pojos;

public class User {
    private String username;
    private String password;
    
    public User() {}
    /**
     * Pojo for User with standard setters and getters
     * @param username
     * @param password 
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
