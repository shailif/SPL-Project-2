//
// Created by spl211 on 01/01/2021.
//
#include "../include/readerKeyboard.h"
#include <string>
#include <iostream>
#include "boost/lexical_cast.hpp"

using namespace std;

readerKeyboard::readerKeyboard(ConnectionHandler &connectionHandler_, bool &terminate): connectionHandler(connectionHandler_), terminate(terminate){}

void readerKeyboard:: run(){
    while (!terminate) {
        const short bufsize = 1024;
        char buf[bufsize];
        cin.getline(buf, bufsize);
        string line(buf);

        string opcodestring=getopcode(line);
        if(0==opcodestring.compare("ADMINREG"))
            firstgroup(1,line);

        if(0==opcodestring.compare("STUDENTREG"))
            firstgroup(2,line);

        if(0==opcodestring.compare("LOGIN"))
            firstgroup(3,line);

        if (0==opcodestring.compare("MYCOURSES")){
            char b[2];
            connectionHandler.shortToBytes(11, b);
            connectionHandler.sendBytes(b, 2);}

        if (0==opcodestring.compare("LOGOUT")){
            char b[2];
            connectionHandler.shortToBytes(4, b);
            connectionHandler.sendBytes(b, 2);}

        if (0==opcodestring.compare("COURSEREG"))
            secondgroup(5,line);

        if (0==opcodestring.compare("KDAMCHECK"))
            secondgroup(6,line);

        if (0==opcodestring.compare("COURSESTAT"))
            secondgroup(7,line);

        if (0==opcodestring.compare("ISREGISTERED"))
            secondgroup(9,line);

        if (0==opcodestring.compare("UNREGISTER"))
            secondgroup(10,line);

        if(0==opcodestring.compare("STUDENTSTAT"))
            thirdgroup(8, line);
    }
}
vector<char>  readerKeyboard :: split (string& input){
    vector <char > arr;
    for (unsigned int i=0;i<input.length();i++ ){
        if (input.at(i)==' ')
            arr.push_back('\0');
        else
            arr.push_back(input.at(i));
    }
    arr.push_back('\0');
    return arr;}

void readerKeyboard:: firstgroup(short opcode, string &line) {
    char charArray[line.length()+1] ;
    int index =0;
    for (char& c:split(line)){
        charArray[index]=c;
        index ++;}
    char b[2];
    connectionHandler.shortToBytes(opcode, b);
    connectionHandler.sendBytes(b, 2);
    connectionHandler.sendBytes(charArray, line.length()+1);
}

void  readerKeyboard :: secondgroup (short opcode, string &line){
        short courseNum= boost ::lexical_cast<short>(line);
         char b2[2] ;//courseNum
         char b[2]; // opcode
        connectionHandler.shortToBytes(courseNum,b2);
        connectionHandler.shortToBytes(opcode, b);
        connectionHandler.sendBytes(b, 2);
        connectionHandler.sendBytes(b2, 2);
}

void readerKeyboard:: thirdgroup(short opcode, string &line) {
    char  charArray [line.length()+1];
    for(unsigned int i=0; i<line.length();i++)
        charArray[i]=line.at(i);

    charArray[line.length()]='\0';
    char b[2];
    connectionHandler.shortToBytes(opcode, b);
    connectionHandler.sendBytes(b, 2);
    connectionHandler.sendBytes(charArray, line.length()+1);
}

string readerKeyboard:: getopcode(string & input){
    int index = input.find_first_of(" ");
    string s=input.substr(0,index);
    input.erase(0,index+1);
    return s;
}
