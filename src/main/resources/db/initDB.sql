DROP TABLE IF EXISTS person_roles;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS persons;
DROP TABLE IF EXISTS citizens;
DROP TABLE IF EXISTS candidates;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS restaurants;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE persons
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  password         VARCHAR                 NOT NULL,
  date             TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOL DEFAULT TRUE       NOT NULL,
  has_voted        BOOL DEFAULT TRUE       NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON persons (email);

CREATE TABLE person_roles
(
  person_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT person_roles_idx UNIQUE (person_id, role),
  FOREIGN KEY (person_id) REFERENCES persons (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  date             TIMESTAMP DEFAULT now() NOT NULL,
  votes            INTEGER                 NOT NULL,
  enabled          BOOL DEFAULT TRUE       NOT NULL
);
CREATE UNIQUE INDEX restaurants_unique_name_idx ON restaurants (name);

CREATE TABLE dishes (
  id                INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  restaurant_id     INTEGER   NOT NULL,
  date              TIMESTAMP DEFAULT now(),
  name              TEXT NOT NULL,
  price             INT       NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX dishes_unique_person_date_idx
  ON dishes (restaurant_id, date);

CREATE TABLE votes (
  id                INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
--   user_id         INTEGER   NOT NULL,
  restaurant_id     INTEGER   NOT NULL,
  votes   INTEGER             NOT NULL DEFAULT 0,
  date              TIMESTAMP DEFAULT now() NOT NULL,
--   FOREIGN KEY (user_id) REFERENCES persons (id) ON DELETE CASCADE,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX voters_unique_restaurant_data_idx ON votes (restaurant_id, date);