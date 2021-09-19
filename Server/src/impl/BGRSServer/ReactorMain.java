package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.ImplMessageEncoderDecoder;
import bgu.spl.net.api.ImplMessagingProtocol;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.srv.Server;
import java.io.IOException;

public class ReactorMain {
    public static void main (String[] args){
        Database database = Database.getInstance();
        database.initialize("./Courses.txt");
        try(Server<Command> srv=Server.reactor(Integer.parseInt(args[1]),Integer.parseInt(args[0]),
                ()->new ImplMessagingProtocol(),()->new ImplMessageEncoderDecoder())){
            srv.serve();
        }catch(IOException e){System.out.println("IOException in ReactorCMain");}

    }
}


