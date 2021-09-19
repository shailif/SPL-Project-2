package bgu.spl.net.DataObjects;

import bgu.spl.net.impl.BGRSServer.Database;

public class Admin implements User {
    private String Username;
    private String Password;
    private Boolean isLoggedIn;
    private Database database=Database.getInstance();

    public Admin(String Username, String Password){
        this.Username=Username;
        this.Password=Password;
        isLoggedIn=false;
    }

    public String getUsername(){return Username;}
    public String getPassword(){return Password;}
    public Boolean getIsLoggedIn(){return isLoggedIn;}
    public void logIn(){isLoggedIn=true;}
    public void logOut(){isLoggedIn=false;}
    public void setUsername(String username){this.Username=username;}
    public void setPassword(String password){this.Password=password;}

}
