@echo off
start .\mongodb\bin\mongod.exe --dbpath=.\data\db --config=.\config\mongod.conf
