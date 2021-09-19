CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system -lboost_thread -pthread

all: bin/BGRSclient

bin/BGRSclient: bin/connectionHandler.o bin/readerKeyboard.o bin/readerServer.o  bin/BGRSclient.o
	@echo 'Building target: main'
	@echo 'Invoking: C++ Linker'
	g++ -o bin/BGRSclient bin/connectionHandler.o bin/readerKeyboard.o bin/readerServer.o bin/BGRSclient.o  $(LDFLAGS)
	@echo 'Finished building target: main'
	@echo ' '

bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp

bin/readerKeyboard.o: src/readerKeyboard.cpp
	g++ $(CFLAGS) -o bin/readerKeyboard.o src/readerKeyboard.cpp

bin/readerServer.o: src/readerServer.cpp
	g++ $(CFLAGS) -o bin/readerServer.o src/readerServer.cpp

bin/BGRSclient.o: src/BGRSclient.cpp
	g++ $(CFLAGS) -o bin/BGRSclient.o src/BGRSclient.cpp



.PHONY: clean
clean:
	rm -f bin/*

