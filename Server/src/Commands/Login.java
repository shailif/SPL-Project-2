package bgu.spl.net.Commands;

import bgu.spl.net.DataObjects.Student;
import bgu.spl.net.DataObjects.User;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class Login implements Command<User> {
    private short opcode;
    private String userName;
    private String password;
    private Database database = Database.getInstance();

    public Login(short opcode, String userName, String password){
        this.opcode=opcode;
        this.userName=userName;
        this.password=password;
    }
    public Serializable execute(User myUser){
        synchronized (database.usernameRegLock){
            if ((!database.usernamesReg.containsKey(userName)))
                return new ErrorMessage(new Short("13"), opcode);
            if((!database.usernamesReg.get(userName).getPassword().equals(password)))
                return new ErrorMessage(new Short("13"), opcode);
            if (database.usernamesReg.get(userName).getIsLoggedIn()) {
                return new ErrorMessage(new Short("13"), opcode);
            }
             else {
                 return new Ack(new Short("12"), opcode, "");

            }
        }
    }
    public String getusername(){return userName;}

}
