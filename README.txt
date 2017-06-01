AirSittingApplication:
======================

Software or library version used:
--------------------------------------
Java Version: 1.8.0_131
Maven Version: 3.5.0

Libraries: Will be downloaded automatically by Maven while running 'mvn clean install' on project
M2_REPO/junit/junit/4.12/junit-4.12.jar
M2_REPO/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
M2_REPO/args4j/args4j/2.0.10/args4j-2.0.10.jar
M2_REPO/commons-io/commons-io/2.5/commons-io-2.5.jar
----------------------------------------


EntryPoint class of AirSittingApplication:
--------------------------------- ---------
AirSittingApplication/src/main/java/com/boxever/air/Application.java

Tests:
------------------------------------------
Total: 13 tests
12 tests for different scenarios for SeatArrangement
1 test for PaxGroup


How to Run Application:
-----------------------------------------
- Extract the AirSittingApplication.rar or .zip file.
- Change current directory to AirSittingApplication. Command-->: cd AirSittingApplication
- Run command: java -jar target/AirSittingApplication-1.0-SNAPSHOT-jar-with-dependencies.jar --file <file path containing passenger information>
For example:
java -jar target/AirSittingApplication-1.0-SNAPSHOT-jar-with-dependencies.jar --file src/main/resources/input/file.txt

- There is step by step view of seat map whenever a px group is added into the seat map for better understanding.

You can also import the project into Eclipse and run Application.java
Also run the tests.

Points/Assumption for Implementation:
----------------------------------------
- The passenger will be assigned seat on First come first serve basis i.e. pax info parsed from the file will be assigned seat at the same time.
- Group of passengers to sit in same row is first priority if more than 2 passengers from group has window preference.
- If two passengers from group has window seat preference then both will be assigned window preference in seat row.
- If there are more passengers than the seats in the seat map then only passengers equals to the seats will get seat on first come first serve. Remaining will not get seat and will be counted in not satisfied passengers.
- On parsing passenger information from line, if there is any invalid passenger details then whole group will be discarded cause passengers from group should sit in same row.  
- If passenger is assigned the seat with the group then the person should count in satisfied passenger. Also if passenger has window preference and get assigned window seat, the passenger will be counted in satisfied passenger.
----------------------------------------


Implementation Strategy/ Algorithm:
----------------------------------------
1. Read file and parse line by line
2. Parse pax group information from line
3. Find the seat row for the pax group in seat map.
4. Sort the pax group with seat preference passenger first
5. Assign window seats for window seat pref pax in the seat row
6. Increase satisfied passenger count
7. Do Steps from 2-6 until file is finished
8. Return the seat map
9. Calculate percentage of satisfied passenger out of total passengers parsed from file
----------------------------------------


Further Improvements
----------------------------------------
- Can further re-factor SeatAssignment class 
- Have a queue of empty seats and a queue of passengers rather than searching everytime for seatrow having empty seats
- Track previous seat rows which has empty seats in it.
----------------------------------------

Project Summary or details
----------------------------------------
You run an Airline that has several planes that fly to different destinations around the world.
You pride yourself on having a high customer satisfaction with those that fly with you. This
you achieve by ensuring that:
- Groups of travellers are seated together on the same row.
- Providing travellers with a window seat if request.

To determine the best sitting arrangements on the flight create a program that takes an input
file as a command line argument and prints the results to standard out. An example input file
is:

4 4
1W 2 3
4 5 6 7
8
9 10 11W
12W
13 14
15 16


The first line specifies the dimensions of the plane. The first digit is the number of seats in a
row and the second digit is the number of rows on the plane.

Each subsequent line describes a group of travellers. For example, the first line of travelers
describes a group of three where the first traveller has a preference for a window seat. Each
number uniquely identifies the traveller on the flight.

The output for the above file should be:
1 2 3 8
4 5 6 7
11 9 10 12
13 14 15 16
100%

The program should aim to maximize customer satisfaction. The last line in the above output
indicates the percentage of customers that have had their preferences satisfied. If the plane
is over subscribed the program should aim to maximize customer satisfaction of those
customers waiting for the flight.

When you are submitting your program please provide a brief description of the approach in
a README.txt file and zipped folder that includes a buildable project with the source code
and appropriate tests.
----------------------------------------