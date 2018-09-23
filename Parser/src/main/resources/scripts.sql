

# Create the database schema;
create schema ef;

# Use schema
use ef;

#Create the access_logs table
create table access_log (
	id bigint not null auto_increment,
    `timestamp` datetime not null,
    `ip` varchar(45),
    `method` varchar(255),
    `client` varchar(255),
    primary key (id)
);