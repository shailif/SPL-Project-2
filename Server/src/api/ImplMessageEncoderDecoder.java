package bgu.spl.net.api;
import bgu.spl.net.Commands.*;
import bgu.spl.net.impl.rci.Command;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ImplMessageEncoderDecoder implements MessageEncoderDecoder <Command> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    private int counter=-1; //  number of zero we expect from socket client
    private int opcount=0;
    private int nozero=-1; // relevent to masseges who has only four bytes
    private short opcode=0;

    @Override

    public Command decodeNextByte(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (opcount < 1){
            pushByte(nextByte);
            opcount++;
            return  null;
        }

        if (opcount==1) {
            pushByte(nextByte);
            byte[] bytesArray = {bytes[0], bytes[1]};
            opcode = bytesToShort(bytesArray);
            opcount=5;
            if (1 <= opcode && opcode <= 3)
                counter=2;

            else {
                if (4 == opcode || opcode == 11)
                    return popCommand(opcode);

                else {
                    if ((5 <= opcode && opcode <= 7) || (9 <= opcode && opcode <= 10)) {
                        nozero = 2;
                    } else {//opcode 8
                        counter = 1;
                    }
                }
            }
            return null;
        }
      else {
          if (nextByte=='\0')
              counter--;
          if (counter == 0 )
              return popCommand(opcode);
          else if (nozero == 1){
                pushByte(nextByte);
                return popCommand(opcode);
          }
          else {
                pushByte(nextByte);
                return null; //not a line yet
          }
      }
    }

    @Override
    public byte[] encode(Command command) {
        if(command instanceof Ack)
            return ((Ack)command).AckToByteArray(); //uses utf8 by default*/
        else
            return ((ErrorMessage)command).ErrorToByteArray();
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }
        nozero--;
        bytes[len++] = nextByte;
    }

    private Command popCommand(short opcode) {
        if (1 <= opcode && opcode <= 3) {
            String[] argsArray = new String[2];
            int j = 0;
            for (int i = 2; i < len; i++) {
                if (bytes[i] == '\0') {
                    argsArray[j] = new String(bytes, 2, i - 2, StandardCharsets.UTF_8);
                    j++;
                }
                else if (j==1) {
                    argsArray[j] = new String(bytes, i, len, StandardCharsets.UTF_8);
                    j++;
                }
            }
            Short tmp=opcode;
            reastart();
            if (tmp == 1)
                return new Adminreg(tmp, argsArray[0], argsArray[1]);
            if (tmp == 2)
                return new Studentreg(tmp, argsArray[0], argsArray[1]);
            else
                return new Login(tmp, argsArray[0], argsArray[1]);
        } else {
            if (4 == opcode || opcode == 11) {
                Short tmp=opcode;
                reastart();
                if (tmp == 4)
                    return new Logout(tmp);
                else
                    return new Mycourses(tmp);
            } else {
                if ((5 <= opcode && opcode <= 7) || (9 <= opcode && opcode <= 10)) {
                    byte []coursenum = { bytes[2], bytes[3]};
                   short course= bytesToShort(coursenum);
                    Short tmp=opcode;
                    reastart();

                    if (tmp == 5)
                        return new Coursereg(tmp, course);
                    if (tmp == 6)
                        return new Kdamcheck(tmp, course);
                    if (tmp == 7)
                        return new Coursestat(tmp, course);
                    if (tmp == 9)
                        return new Isregistered(tmp, course);
                    else
                        return new Unregister(tmp, course);
                } else {
                    String str = new String(bytes, 2, len - 2, StandardCharsets.UTF_8);
                    Short tmp=opcode;
                    reastart();
                    return new Studentstat(tmp, str);
                }


            }

        }
    }
    public short bytesToShort(byte[] byteArr) {
        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);
        return result;
    }



private void reastart(){
    bytes = new byte[1 << 10]; //start with 1k
    len = 0;
    counter=-1; //  number of zero we expect from socket client
    opcount=0;
    nozero=-1; // relevent to masseges who has only four bytes
    opcode=0;

}


}

