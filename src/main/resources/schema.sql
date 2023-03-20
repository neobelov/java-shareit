DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
     name VARCHAR NOT NULL,
     email VARCHAR NOT NULL,
     CONSTRAINT pk_user PRIMARY KEY (id),
     CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS items (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    available bool NOT NULL,
    owner_id BIGINT NOT NULL,
    CONSTRAINT pk_items PRIMARY KEY (id),
    CONSTRAINT fk_items_owner FOREIGN KEY(owner_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS bookings (
     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
     start_date timestamp NOT NULL,
     end_date timestamp NOT NULL,
     item_id BIGINT NOT NULL,
     booker_id BIGINT NOT NULL,
     status VARCHAR NOT NULL,
     CONSTRAINT pk_bookings PRIMARY KEY(id),
     CONSTRAINT fk_bookings_item_id FOREIGN KEY(item_id) REFERENCES items(id),
     CONSTRAINT fk_bookings_booker_id FOREIGN KEY(booker_id) REFERENCES users(id)
);