Assignment
----

The goal is to write a parser in Java that parses web server access log file, loads the log to MySQL and checks if a given IP makes more than a certain number of requests for the given duration. 

Java
----

(1) Create a java tool that can parse and load the given log file to MySQL. 
	The delimiter of the log file is pipe (|)
	

(2) The tool takes 
	"startDate", 
	"duration" and 
	"threshold" as command line arguments. 
	
	"startDate" is of "yyyy-MM-dd.HH:mm:ss" format, 
	"duration" can take only "hourly", "daily" as inputs and 
	"threshold" can be an integer.

	
	
(3) This is how the tool works:

    java -cp "parser.jar" com.ef.Parser --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100
	
		The tool will find any IPs that made more than 100 requests starting from 2017-01-01.13:00:00 to 2017-01-01.14:00:00 (one hour)
		and print them to console 
		AND also load them to another MySQL table with comments on why it's blocked.
		
		

	java -cp "parser.jar" com.ef.Parser --startDate=2017-01-01.13:00:00 --duration=daily --threshold=250

		The tool will find any IPs that made more than 250 requests starting from 2017-01-01.13:00:00 to 2017-01-02.13:00:00 (24 hours) 
		and print them to console 
		AND also load them to another MySQL table with comments on why it's blocked.


		
SQL
---

(1) Write MySQL query to find IPs that made more than a certain number of requests for a given time period.
    Ex: Write SQL to find IPs that made more than 100 requests starting from 2017-01-01.13:00:00 to 2017-01-01.14:00:00.

(2) Write MySQL query to find requests made by a given IP.


Use case:
----

(1) In root folder, run 'mvnw install'

(2) Copy access.log file to target directory and run from there.

	* Run with log file specified (deletes old table entries and loads the file into the DB):
	\Parser\target>java -jar Parser-0.0.1-SNAPSHOT.jar --logFile=access.log --startDate=2017-01-01.00:00:11 --duration=hourly --threshold=100
	Reading in the logs file... done (4431 ms)
	------------------------ Banned clients -------------------------------
	IP: 192.168.234.82 banned for making >= 100 requests, from: 2017-01-01T00:00:11 in specified duration: hourly
	IP: 192.168.169.194 banned for making >= 100 requests, from: 2017-01-01T00:00:11 in specified duration: hourly
	------------------------ done -------------------------------
	
	
	
	* Run without the log file specified, only runs the query and saves the result to the blacklist table:
	\Parser\target>java -jar Parser-0.0.1-SNAPSHOT.jar --startDate=2017-01-01.00:00:11 --duration=hourly --threshold=100
	------------------------ Banned clients -------------------------------
	IP: 192.168.234.82 banned for making >= 100 requests, from: 2017-01-01T00:00:11 in specified duration: hourly
	IP: 192.168.169.194 banned for making >= 100 requests, from: 2017-01-01T00:00:11 in specified duration: hourly
	------------------------ done -------------------------------

	
	SQLs:
	
	# Create the database schema;
	create schema ef;

	# Use schema
	use ef;

	#Create the access_logs table
	create table access_log (
		id bigint not null auto_increment,
		`timestamp` datetime not null,
		`ip` varchar(45) not null,
		`method` varchar(255) not null,
		`status_code` int not null,
		`client` varchar(255) not null,
		primary key (id)
	);


	# Find IPs that mode more than a certain number of requests for a given time period
	select LOGLINE.ip from access_log as LOGLINE
	where LOGLINE.timestamp between '2017-01-01 13:00:00' and '2017-01-01 14:00:00'
	group by LOGLINE.ip having count(LOGLINE.ip) >= 100


	# Find requests made by a given IP
	select * from access_log where ip = '192.168.228.188';
