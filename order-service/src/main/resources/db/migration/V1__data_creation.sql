CREATE SEQUENCE order_seq START 1;

CREATE TABLE "user"
(
    id  SERIAL,
    PRIMARY KEY (id)
);

CREATE TABLE order
(
    id          BIGINT NOT NULL,
    user_id     BIGINT UNIQUE NOT NULL,
    product     VARCHAR(255),
    status      VARCHAR(255),
    quantity    INT,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES "user" (id)
);