package bgu.spl.net.Commands;

import bgu.spl.net.DataObjects.User;
import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class Logout implements Command<User> {

    private short opcode;

    public Logout(short opcode) {
        this.opcode = opcode;
    }

    public Serializable execute(User myUser) {
        if (!myUser.getIsLoggedIn())
            return new ErrorMessage(new Short("13"), opcode);
        else {
              myUser.logOut();
              return new Ack(new Short("12"), opcode, "");
        }
    }
}






