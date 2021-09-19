package bgu.spl.net.Commands;

import bgu.spl.net.DataObjects.Admin;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class Adminreg implements Command<Admin> {
    private short opcode;
    private String userName;
    private String password;
    private Database database = Database.getInstance();

    public Adminreg(short opcode, String userName, String password){
        this.opcode=opcode;
        this.userName=userName;
        this.password=password;
    }


    public Serializable execute(Admin myAdmin){
        synchronized (database.usernameRegLock) {
            if (database.usernamesReg.containsKey(userName))
                return new ErrorMessage(new Short("13"), opcode);
             else {
                myAdmin.setPassword(password);
                myAdmin.setUsername(userName);
                database.usernamesReg.put(userName,myAdmin);
                return new Ack(new Short("12"), opcode, "");
            }
        }
    }
}
