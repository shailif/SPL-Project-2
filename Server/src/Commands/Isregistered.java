package bgu.spl.net.Commands;

import bgu.spl.net.DataObjects.Admin;
import bgu.spl.net.DataObjects.Course;
import bgu.spl.net.DataObjects.User;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class Isregistered implements Command<User> {
        private short opcode;
        private short courseNum;
        private Database database = Database.getInstance();

        public Isregistered(short opcode,short courseNum){
            this.opcode=opcode;
            this.courseNum=courseNum;
        }

        public Serializable execute(User myUser){
                if (!myUser.getIsLoggedIn())
                    return new ErrorMessage(new Short("13"),opcode);

                if (myUser instanceof Admin)
                    return new ErrorMessage(new Short("13"),opcode);
                synchronized (database.coursesLock) {
                    Course course = database.getCourseByNum(courseNum);
                    if( course==null)
                        return new ErrorMessage(new Short("13"),opcode);
                    if (course.getListOfSudents().contains(myUser.getUsername())) {
                        return new Ack(new Short("12"), opcode, "\n" + "REGISTERED");
                    } else
                        return new Ack(new Short("12"), opcode, "\n" + "NOT REGISTERED");
                }
            }
        }

