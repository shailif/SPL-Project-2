package bgu.spl.net.Commands;

import bgu.spl.net.DataObjects.User;
import bgu.spl.net.impl.rci.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class ErrorMessage implements Command<User> {
    private short opcode;
    private short messagaOpcode;


    public ErrorMessage(short opcode, short messagaOpcode){
        this.opcode=opcode;
        this.messagaOpcode=messagaOpcode;
    }

    @Override
    public Serializable execute(User myUser) {
        return null;
    }


   public byte [] ErrorToByteArray(){
        byte [] outputopcode=shortToBytes(opcode);
        byte [] outputmessageopcode=shortToBytes(messagaOpcode);
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        try {
            outputStream.write(outputopcode);
            outputStream.write(outputmessageopcode);

        }catch (IOException e){};
        return outputStream.toByteArray();}

    public byte[] shortToBytes(short num){
        byte[] bytesarr=new byte[2];
        bytesarr[0]=(byte)((num >> 8) & 0xFF);
        bytesarr[1]=(byte)(num & 0xFF);
        return bytesarr;
    }
}


