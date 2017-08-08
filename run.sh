#!/bin/bash
echo "STARTING..."
java -cp antlr.jar antlr.Tool gramatica.g
echo "PARSER AND LEXER GENERATED"
if [ $? -eq 0 ]; then
   javac -classpath antlr.jar:. *.java
   echo "ALL CLASSES COMPILED"
   if [ $? -eq 0 ]; then
       java -cp antlr.jar:. MainClass < input.in
   else 
       echo "GAVE BAD COMPILING"
   fi
else
   echo "GAVE BAD TO GENERATE PARSER"
fi