package bgu.spl.net.Commands;

import bgu.spl.net.DataObjects.Admin;
import bgu.spl.net.DataObjects.User;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class Mycourses implements Command<User> {

    private short opcode;
    private Database database = Database.getInstance();

    public Mycourses(short opcode){this.opcode=opcode;}

    public Serializable execute(User myUser){
            if (!myUser.getIsLoggedIn())
                return new ErrorMessage(new Short("13"),opcode);
            if (myUser instanceof Admin)
                return new ErrorMessage(new Short("13"),opcode);

            String s=""+database.getcoursesPerStudent(myUser.getUsername());
            s=s.replaceAll("\\s","");
            return new Ack(new Short("12"),opcode,"\n"+s);
        }
    }

