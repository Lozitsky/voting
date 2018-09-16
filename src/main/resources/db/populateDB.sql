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

INSERT INTO restaurants (name, description) VALUES
  ('Ararat', 'some description'),
  ('U Vano', 'some description'),
  ('Белый Лебедь', 'some description'),
  ('Черная Каракатица', 'some description'),
  ('Последний причал', 'some description'),
  ('Файна страва', ''),
  ('For Kings', 'some description');

INSERT INTO votes (restaurant_id) VALUES
  (10004),
  (10005),
  (10006),
  (10007),
  (10008),
  (10009),
  (10010);

INSERT INTO dishes (name, price, restaurant_id)VALUES
       ('Завтрак', 500, 10004),
       ('Обед', 1000, 10004),
       ('Ужин', 500, 10005),
       ('Завтрак', 500, 10005),
       ('Обед', 1000, 10006),
       ('Ужин', 510, 10006),
       ('Super ланч', 510, 10005),
       ('Super ужин', 1500, 10005);

