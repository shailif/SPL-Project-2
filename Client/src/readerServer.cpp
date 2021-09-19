//
// Created by spl211 on 01/01/2021.
//

#include "../include/readerServer.h"
using namespace std;

readerServer:: readerServer (ConnectionHandler &connectionHandler_, bool &terminate):connectionHandler(connectionHandler_), terminate(terminate){}

void readerServer :: run(){

    while(!terminate) {
        char bytes1[2];
        connectionHandler.getBytes(bytes1, 2);
        short opcode = connectionHandler.bytesToShort(bytes1);
        char bytes2[2];
        connectionHandler.getBytes(bytes2, 2);
        short messageOpcode = connectionHandler.bytesToShort(bytes2);
        if (opcode == 13) {
            std::cout << "ERROR " << messageOpcode << endl;
        } else {
            string str = "";
            connectionHandler.getFrameAscii(str, '\0');


            std::cout << "ACK " << messageOpcode << str << endl;
            if (messageOpcode == 4)
                terminate=true;

        }
    }

    exit(0);

}