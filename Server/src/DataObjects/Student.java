package bgu.spl.net.DataObjects;
import bgu.spl.net.impl.BGRSServer.Database;

public class Student implements User {

    private String Username;
    private String Password;
    private Boolean isLoggedIn;
    private Database database=Database.getInstance();


    public Student(String Username, String Password){
        this.Username=Username;
        this.Password=Password;
        isLoggedIn=false;
    }

    public void RegisterToCourse (Course course){
        Short courseShort=new Short(course.getCourseNum());
        course.decreasenumOfSeatsAvailable();
        course.addStudent(this);
        database.coursesPerStudent.get(Username).add(courseShort.intValue());
        database.coursesLinesPerStudent.get(Username).add(course.getLine());

    }


public Boolean isRegisteredToCourse(int num){return database.coursesPerStudent.get(Username).contains(num);}

public void unRegisterToCourse (Course course){
        Short courseShort=new Short(course.getCourseNum());
        course.increasenumOfSeatsAvailable();
        course.removeStudent(this);
        int index=database.coursesPerStudent.get(Username).indexOf(courseShort.intValue());
        database.coursesPerStudent.get(Username).remove(index);
        int indexofCourseLine=database.coursesLinesPerStudent.get(Username).indexOf(course.getLine());
        database.coursesLinesPerStudent.get(Username).remove(indexofCourseLine);
    }

    public String getUsername(){return Username;}
    public String getPassword(){return Password;}
    public Boolean getIsLoggedIn(){return isLoggedIn;}
    public void logIn(){isLoggedIn=true;}
    public void logOut(){isLoggedIn=false;}
    public void setUsername(String username){this.Username=username;}
    public void setPassword(String password){this.Password=password;}

}
