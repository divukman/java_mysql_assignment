

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