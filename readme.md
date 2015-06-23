readme

~OVERVIEW~
Application for reading in test cases from a text file into TestRail.  Application converts text file to JSON format, then sends a request to TestRail using an appropriate method.  Currently application only supports the update_case/ method from TestRail.  MainProcess contains the raw code while CleanMainProcess supports a command line user interface

Documentation for TestRail's API can be found here
http://docs.gurock.com/testrail-api2/start

Examples on using TestRail’s API can be found here (important files are included 
https://github.com/gurock/testrail-api

Required JSON library found here (libraries included for convenience)
https://github.com/fangyidong/json-simple

~TEXT FILE FORMATTING~
Application can only read in .txt files.  Use tabs to sepearte entries.  Make sure there is no trailing white space.  Application requires a row containing only the column heading to exit

Example

https://docs.google.com/spreadsheets/d/12NUQ97s9_a6wF9AmpwtuCGV9JzMUDM0ylmIHnOea8hY/edit#gid=0

formats as

caseID	priorityID	est	steps	
305446	4	5m	step1	result1
			step2	result2
