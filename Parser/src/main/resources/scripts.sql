

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
group by LOGLINE.ip having count(LOGLINE.ip) >= 100;


# Find requests made by a given IP
select * from access_log where ip = '192.168.228.188';