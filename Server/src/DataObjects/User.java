package bgu.spl.net.DataObjects;

public interface User {

     String getUsername();
     String getPassword();
     Boolean getIsLoggedIn();
     void logIn();
     void logOut();
}
