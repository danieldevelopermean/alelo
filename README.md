How Run:


*********************************************************
Construct .jar

mvn clean install 

folder: back


*********************************************************
Build Image to Back

docker build -t back .

folder: back


*********************************************************
Build Image to Front

docker build -t front .

folder: front


*********************************************************
Up the Applications

docker-compose up

folder: test1


*********************************************************
Browser:

http://localhost:8081
