# Create the table schema
CREATE TABLE IF NOT EXISTS flight(
    flight_id bigint primary key auto_increment,
    flight_number char(4) not null ,
    departure varchar(20) not null,
    arrival varchar(20) not null ,
    departure_date date not null ,
    departure_time time not null ,
    arrival_date date not null ,
    arrival_time time not null ,
    created_date_time timestamp default current_timestamp not null,
    modified_date_time timestamp default null on update current_timestamp,
    version bigint not null default 0
)