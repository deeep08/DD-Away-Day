# DD-Away-Day
Away day organiser

This is a Maven project that generates a schedule for the employees participating in various activities organised in the event. The input is in the form of text file whose filename can be passed as command-line argument.

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
This will create a output.txt file containing the schedule in prject directory.
