DELETE FROM person_roles;
DELETE FROM votes;
DELETE FROM persons;
DELETE FROM dishes;
DELETE FROM restaurants;

ALTER SEQUENCE global_seq RESTART WITH 10000;

INSERT INTO persons (name, email, password, has_voted) VALUES
  ('User', 'user@yandex.ru', 'password', FALSE ),
  ('Admin', 'admin@gmail.com', 'password', TRUE ),
  ('User2', 'user2@yandex.ru', 'password', FALSE ),
  ('User3', 'user3@yandex.ru', 'password', FALSE );

INSERT INTO person_roles (role, person_id) VALUES
  ('ROLE_USER', 10000),
  ('ROLE_ADMIN', 10001),
  ('ROLE_USER', 10002),
  ('ROLE_USER', 10003);

INSERT INTO restaurants (name, votes) VALUES
  ('Ararat', 0),
  ('U Vano', 0),
  ('Белый Лебедь', 0),
  ('Черная Каракатица', 0),
  ('Последний причал', 0),
  ('Файна страва', 0),
  ('For Kings', 0);

INSERT INTO dishes (date, name, price, restaurant_id)VALUES
       ('2018-07-30 10:00:00', 'Завтрак', 500, 10004),
       ('2018-07-30 13:00:00', 'Обед', 1000, 10004),
       ('2018-07-30 20:00:00', 'Ужин', 500, 10005),
       ('2018-07-31 10:00:00', 'Завтрак', 500, 10005),
       ('2018-07-31 13:00:00', 'Обед', 1000, 10006),
       ('2018-07-31 20:00:00', 'Ужин', 510, 10006),
       ('2018-07-01 14:00:00', 'Super ланч', 510, 10005),
       ('2018-07-01 21:00:00', 'Super ужин', 1500, 10005);

