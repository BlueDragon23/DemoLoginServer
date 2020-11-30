CREATE TABLE user (
     email VARCHAR(128) NOT NULL,
     password VARCHAR(128) NOT NULL,
     enabled BIT NOT NULL,
     PRIMARY KEY (email)
);