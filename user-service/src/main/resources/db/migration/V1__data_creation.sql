CREATE SEQUENCE user_seq START 1;

CREATE TABLE "user"
(
    id          SERIAL,
    name        VARCHAR(255),
    email       VARCHAR(255),
    password    VARCHAR(255),
    role    VARCHAR(255),
    PRIMARY KEY (id)
);