//
// Created by spl211 on 01/01/2021.
//

#ifndef BOOST_ECHO_CLIENT_READERKEYBOARD_H
#define BOOST_ECHO_CLIENT_READERKEYBOARD_H
#include "../include/connectionHandler.h"
#include <string>
using namespace std;


class readerKeyboard {
private:
    ConnectionHandler &connectionHandler;
    bool &terminate;
    void firstgroup(short opcode, string &line);
    void secondgroup(short opcode, string &line );
    void thirdgroup(short opcode, string &line);
public:
    readerKeyboard (ConnectionHandler &connectionHandler, bool &terminate);
    void run();
    vector<char> split(string& input);
    string getopcode(string & input);
};

#endif //BOOST_ECHO_CLIENT_READERKEYBOARD_H
