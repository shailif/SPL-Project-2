package bgu.spl.net.Commands;

import bgu.spl.net.DataObjects.Course;
import bgu.spl.net.DataObjects.Student;
import bgu.spl.net.DataObjects.User;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class Coursestat implements Command<User> {
        private short opcode;
        private short courseNum;
        private Database database = Database.getInstance();

        public Coursestat(short opcode,short courseNum){
            this.opcode=opcode;
            this.courseNum=courseNum;
        }

        public Serializable execute(User myUser){
            if (!myUser.getIsLoggedIn())
                return new ErrorMessage(new Short("13"),opcode);
            if(myUser instanceof Student)
               return new ErrorMessage(new Short("13"),opcode);
            synchronized (database.coursesLock) {
                Course course = database.getCourseByNum(courseNum);
                if( course==null)
                    return new ErrorMessage(new Short("13"),opcode);
                String output = "Course: (" + courseNum + ")" + " " + course.getCourseName() + "\n" + "Seats Available: " +
                        +course.getnumOfSeatsAvailable() + "/" + course.getNumOfMaxStudents() + "\n";
                java.util.Collections.sort(course.getListOfSudents());
                String s = "" + course.getListOfSudents();
                s = s.replaceAll("\\s", "");
                return new Ack(new Short("12"), opcode, "\n" + output + "Students Registered: " + s);
            }
        }
}


