DROP TABLE IF EXISTS USER;
CREATE TABLE USER
(
    ID int ,
    "LOGIN" varchar(55) NOT NULL,
    "PASSWORD" varchar(16) NOT NULL,
    "VERIFY_PASSWORD" varchar(16) NOT NULL
);
