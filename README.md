# DD-Away-Day
Away day organiser

This is a Maven project that generates a schedule for the employees participating in various activities organised in the event. The input is in the form of text file whose filename can be passed as command-line argument.

The number of teams will depend on duration of activities so that all activities can be covered in one day with the consideration for Lunch break and presentation time.

The program divides activities equally among several teams and generates a schedule in output.txt file. 

Prerequisites
===========================
* Java 1.8
* Maven 3.6.2

Build project
===========================
```
mvn clean install
```

This will generate a target folder in the project directory with a executable jar. All required dependencies are present in lib folder created in target directory.

Running project
===========================

```
java -jar target/dd-away-day-1.0.jar "src/main/resources/input/activities.txt"
```
The executable jar takes filename as the command-line argument.
This will create an output.txt file containing the schedule for different teams in project directory.
