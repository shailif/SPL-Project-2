package bgu.spl.net.Commands;

import bgu.spl.net.DataObjects.Student;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Vector;

public class Studentreg implements Command<Student> {
    private short opcode;
    private String userName;
    private String password;
    private Database database = Database.getInstance();

    public Studentreg(short opcode, String userName, String password){
        this.opcode=opcode;
        this.userName=userName;
        this.password=password;
    }


    public Serializable execute(Student myStudent){
        synchronized (database.usernameRegLock){
            if (database.usernamesReg.containsKey(userName))
                return new ErrorMessage(new Short("13"),opcode);
            else {
                myStudent.setUsername(userName);
                myStudent.setPassword(password);
                database.usernamesReg.put(userName,myStudent);
                database.coursesPerStudent.put(userName, new Vector<>());
                database.coursesLinesPerStudent.put(userName, new LinkedList<>());
                return new Ack(new Short("12"), opcode, "");
            }
        }
    }
}
