#!/bin/bash

cp vai_simples.sable sablecc.jar ..
cd ..
rm -rf vaiCompiler
java -jar sablecc.jar vai_simples.sable
rm vai_simples.sable sablecc.jar
