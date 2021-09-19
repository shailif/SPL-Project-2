package bgu.spl.net.impl.BGRSServer;
import bgu.spl.net.DataObjects.Course;
import bgu.spl.net.DataObjects.User;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.io.*;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

    private static class DatabaseHolder{
        private static Database instance = new Database();
    }

    public Vector<Course> courses;
    public ConcurrentHashMap <String, User> usernamesReg;
    public ConcurrentHashMap <String, Vector<Integer>> coursesPerStudent;
    public ConcurrentHashMap <String, List<Integer>> coursesLinesPerStudent;
    public Object coursesLock=new Object();
    public Object usernameRegLock=new Object();


    private Database() {
        courses=new Vector<>();
        usernamesReg=new ConcurrentHashMap<>();
        coursesPerStudent= new ConcurrentHashMap<>();
        coursesLinesPerStudent=new ConcurrentHashMap<>();
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() {
        return DatabaseHolder.instance;
    }

    /**
     * loades the courses from the file path specified
     * into the Database, returns true if successful.
     */
    public boolean initialize(String coursesFilePath){
        try {
            List<String> lines = Files.readAllLines(Paths.get(coursesFilePath));
            int counter=1;
            for (String line : lines) {
                 line = line.replace('|', ':');
                 String[] str = line.split(":");
                 Short num = Short.parseShort(str[0]);
                 String name = str[1];
                 String s=str[2].substring(1,str[2].length()-1); // courses without parenthesis
                 List<Integer> kdamCoursesList = new LinkedList<>();
                 if (!s.equals("")) {
                     String[] kdams = s.split(",");
                     for (String Item : kdams)
                          kdamCoursesList.add(Integer.parseInt(Item));
                }
                String s1=str[3].substring(0,str[3].length()); // number of seats available
                Integer maxStudent = Integer.parseInt(s1);
                courses.add(new Course(num,name,kdamCoursesList,maxStudent, counter));
                counter ++;
            }

        } catch (IOException e) {return false;}
        return true;
    }
    public int getCourseNumByLine(int line){
        for (int i=0; i<courses.size(); i++) {
            if (courses.elementAt(i).getLine() == line)
                return courses.elementAt(i).getCourseNum();
        }
        return 0;
    }

    public Course getCourseByNum(short courseNum){
        for (int i=0; i<courses.size(); i++) {
            if (courses.elementAt(i).getCourseNum() == courseNum)
                return courses.elementAt(i);
        }
        return null;
    }

    public Vector<Integer> getcoursesPerStudent(String username){
        List <Integer> coursesLines = coursesLinesPerStudent.get(username);
        Collections.sort(coursesLines);
        Vector <Integer> output = new Vector<>();
        for(Integer line: coursesLines)
            output.add(getCourseNumByLine(line));

       return  output;
    }
}
