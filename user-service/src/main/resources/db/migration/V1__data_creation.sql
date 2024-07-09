CREATE SEQUENCE user_sequence INCREMENT BY 10;

CREATE TABLE "user"
(
    id          BIGINT NOT NULL,
    name        VARCHAR(255),
    email       VARCHAR(255),
    password    VARCHAR(255),
    role        VARCHAR(255),
    PRIMARY KEY (id)
);