package bgu.spl.net.Commands;

import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.DataObjects.Student;
import bgu.spl.net.DataObjects.User;
import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class Studentstat implements Command<User> {
        private short opcode;
        private String userName;
        private Database database=Database.getInstance();

        public Studentstat(short opcode,String userName){
            this.opcode=opcode;
            this.userName=userName;
        }

        public Serializable execute(User myUser){
            if (!myUser.getIsLoggedIn())
                return new ErrorMessage(new Short("13"),opcode);
            if(myUser instanceof Student)
                return new ErrorMessage(new Short("13"),opcode);

            synchronized (database.usernameRegLock) {
                if (!database.usernamesReg.containsKey(userName))
                    return new ErrorMessage(new Short("13"), opcode);

                if (!database.coursesLinesPerStudent.containsKey(userName))
                    return new ErrorMessage(new Short("13"), opcode);
                String s = "" + database.getcoursesPerStudent(userName);
                s = s.replaceAll("\\s", "");

                return new Ack(new Short("12"), opcode, "\n" + "Student: " + userName + "\n" + "Courses: " + s);
            }
            }
        }

