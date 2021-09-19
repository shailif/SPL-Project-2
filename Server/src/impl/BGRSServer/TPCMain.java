package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.ImplMessageEncoderDecoder;
import bgu.spl.net.api.ImplMessagingProtocol;
import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.srv.Server;
import java.io.IOException;

public class TPCMain  {
    public static void main (String[] args){
         Database database = Database.getInstance();
        database.initialize("Courses.txt");
        try(Server<Command> srv=Server.threadPerClient(Integer.parseInt(args[0]),
                ()->new ImplMessagingProtocol(),()->new ImplMessageEncoderDecoder())){
            srv.serve();
        }catch(IOException e){System.out.println("IOException in TPCMain");}

    }
    }

