package bgu.spl.net.Commands;
import bgu.spl.net.DataObjects.User;
import bgu.spl.net.impl.rci.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class Ack implements Command<User> {
    private short opcode;
    private short messagaOpcode;
    private  String optional;

    public Ack(short opcode, short messagaOpcode, String optional){
        this.opcode=opcode;
        this.messagaOpcode=messagaOpcode;
        this.optional=optional;
    }

    @Override
    public Serializable execute(User myUser) {
        return null;
    }

    public byte [] AckToByteArray(){
        byte [] outputopcode=shortToBytes(opcode);
        byte [] outputmessageopcode=shortToBytes(messagaOpcode);
        byte [] theRest=(""+optional+"\0").getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        try {
           outputStream.write(outputopcode);
           outputStream.write(outputmessageopcode);
           outputStream.write(theRest);

        }catch (IOException e){};

        return outputStream.toByteArray();}

        public byte[] shortToBytes(short num){
            byte[] bytesarr=new byte[2];
            bytesarr[0]=(byte)((num >> 8) & 0xFF);
            bytesarr[1]=(byte)(num & 0xFF);
            return bytesarr;
        }
}
