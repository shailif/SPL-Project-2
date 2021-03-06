#include <stdlib.h>
#include "../include/connectionHandler.h"
#include <iostream>
#include "../include/readerKeyboard.h"
#include "../include/readerServer.h"
#include <thread>
/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/
int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);

    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }
    bool terminate=false;

    readerKeyboard readerKeyboard(connectionHandler, terminate);
    readerServer readerServer (connectionHandler, terminate);
    std:: thread th1(&readerServer:: run , &readerServer);
    readerKeyboard.run();
    th1.detach();

    exit(0) ;

}
