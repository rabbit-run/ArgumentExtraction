#!/usr/bin/bash

if [ ! -d "./bin" ]
then
    mkdir bin
fi

javac -d bin -cp stanford-parser.jar:guava-12.0.jar src/edu/nyu/cs/final_project/*.java

