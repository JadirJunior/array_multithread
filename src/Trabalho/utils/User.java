package Trabalho.utils;

public class User {
    private String user;
    private String pass;
    private boolean authenticated;

    public User(String user, String pass, boolean authenticated) {
        this.user = user;
        this.pass = pass;
        this.authenticated = authenticated;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
