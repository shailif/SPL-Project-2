package bgu.spl.net.DataObjects;

import java.util.*;

public class Course {
    private short courseNum;
    private String courseName;
    private List<Integer> kdamCoursesList;
    private int numOfMaxStudents;
    private int numOfSeatsAvailable;
    private List<String> ListOfSudents;
    private int line;


    public Course(short courseNum,String courseName,List<Integer> kdamCoursesList, int numOfMaxStudents,int line) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.kdamCoursesList = kdamCoursesList;
        this.numOfMaxStudents = numOfMaxStudents;
        this.line=line;
        numOfSeatsAvailable = numOfMaxStudents;
        ListOfSudents = new LinkedList<>();
    }

    public short getCourseNum(){return courseNum;}
    public String getCourseName(){return courseName;}
    public List<Integer> getKdamCoursesList(){return kdamCoursesList;}
    public int getNumOfMaxStudents(){return numOfMaxStudents;}
    public int getnumOfSeatsAvailable(){return numOfSeatsAvailable;}
    public int getLine(){return line;}
    public List<String> getListOfSudents(){
        Collections.sort(ListOfSudents);
        return ListOfSudents;
    }
    public void decreasenumOfSeatsAvailable(){
        numOfSeatsAvailable--;
    }
    public void increasenumOfSeatsAvailable(){
        numOfSeatsAvailable++;
    }
    public void addStudent(Student student){
        ListOfSudents.add(student.getUsername());
    }
    public void removeStudent(Student student){
        int index=ListOfSudents.indexOf(student.getUsername());
        ListOfSudents.remove(index);
    }

}


