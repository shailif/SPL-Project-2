package bgu.spl.net.api;
import bgu.spl.net.Commands.*;
import bgu.spl.net.DataObjects.Admin;
import bgu.spl.net.DataObjects.Student;
import bgu.spl.net.DataObjects.User;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.rci.Command;

public class ImplMessagingProtocol  implements MessagingProtocol <Command> {

    private boolean shouldTerminate;
    private User myStudent;
    private User myAdmin;
    Boolean loggedIn=false;
    private Database database = Database.getInstance();


    public ImplMessagingProtocol() {
        shouldTerminate = false;
        myAdmin = new Admin("","");
        myStudent =new Student("","");
    }

    @Override
    public Command process(Command msg) {
        if (msg instanceof Adminreg) {
            if (loggedIn==true)
                return new ErrorMessage(new Short("13"),new Short("1"));
            myStudent = null;
            myAdmin=new Admin("","");
            return(Command) msg.execute(myAdmin);
        }

        if (msg instanceof Studentreg) {
            if (loggedIn==true)
                return new ErrorMessage(new Short("13"),new Short("2"));
            myAdmin = null;
            myStudent=new Student("","");
            return(Command) msg.execute(myStudent);
        }

        if (msg instanceof Login){
            Command c;
            if (myAdmin != null & database.usernamesReg.get(((Login) msg).getusername()) instanceof Admin){//login to admin when registered to admin
                if (loggedIn==true)
                    return new ErrorMessage(new Short("13"),new Short("3"));
                c = (Command) msg.execute(myAdmin);
                if (c instanceof Ack){//if login succes
                    myStudent=null;
                    myAdmin=database.usernamesReg.get(((Login) msg).getusername());
                    myAdmin.logIn();
                }
            }
            else {
                if (myAdmin != null & database.usernamesReg.get(((Login) msg).getusername()) instanceof Student) {//login to student but registered to admin
                    if (loggedIn==true)
                        return new ErrorMessage(new Short("13"),new Short("3"));
                    myStudent = new Student("", "");
                    c = (Command) msg.execute(myStudent);
                    if (c instanceof Ack) {
                        myAdmin=null;
                        myStudent = database.usernamesReg.get(((Login) msg).getusername());
                        myStudent.logIn();
                    }
                } else {
                    if (myStudent != null & database.usernamesReg.get(((Login) msg).getusername()) instanceof Student) {//login to student when registered to student
                        if (loggedIn==true)
                            return new ErrorMessage(new Short("13"),new Short("3"));
                        c = (Command) msg.execute(myStudent);
                        if (c instanceof Ack) {
                            myAdmin=null;
                            myStudent = database.usernamesReg.get(((Login) msg).getusername());
                            myStudent.logIn();
                        }

                    } else {//login to admin but registered to student
                        if (loggedIn==true)
                            return new ErrorMessage(new Short("13"),new Short("3"));
                        myAdmin = new Admin("", "");
                        c = (Command) msg.execute(myAdmin);
                        if (c instanceof Ack) {
                            myStudent = null;
                            myAdmin = database.usernamesReg.get(((Login) msg).getusername());
                            myAdmin.logIn();

                        }

                    }
                }
            }
            if (c instanceof Ack)
                loggedIn = true;
            return c;
        }

        if(msg instanceof Logout) {
            Command c;
            if (myAdmin != null)
                c = (Command) msg.execute(myAdmin);
            else
                c = (Command) msg.execute(myStudent);
            if (c instanceof Ack)
                shouldTerminate = true;
            return c;
        }

        if (myAdmin != null)
            return (Command) msg.execute(myAdmin);

        else   //if (myStudent != null)
            return (Command) msg.execute(myStudent);
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

}

