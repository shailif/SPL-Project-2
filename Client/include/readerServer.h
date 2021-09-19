//
// Created by spl211 on 01/01/2021.
//

#ifndef BOOST_ECHO_CLIENT_READERSERVER_H
#define BOOST_ECHO_CLIENT_READERSERVER_H
#include "../include/connectionHandler.h"
#include <string>
using namespace std;
class readerServer {
private:
    ConnectionHandler &connectionHandler;
    bool &terminate;

public:
    readerServer (ConnectionHandler &connectionHandler, bool &terminate);
    void run();
};


#endif //BOOST_ECHO_CLIENT_READERSERVER_H
