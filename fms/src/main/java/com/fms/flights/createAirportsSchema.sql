### Create Airports table
CREATE TABLE IF NOT EXISTS airports(
    airport_code CHAR(3) PRIMARY KEY,
    airport VARCHAR(20) NOT NULL UNIQUE
);