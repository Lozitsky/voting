DELETE FROM person_roles;
DELETE FROM votes;
DELETE FROM persons;
DELETE FROM dishes;
DELETE FROM restaurants;

ALTER SEQUENCE global_seq RESTART WITH 10000;

INSERT INTO persons (name, email, password, date, last_id, last_voting) VALUES
  ('User', 'user@ukr.net', 'password', '2018-09-15 01:01:01', 111, '2018-09-15'),
  ('Admin', 'admin@gmail.com', 'password', '2018-09-14 02:02:02', 111, '2018-09-15'),
  ('User2', 'user2@yandex.ru', 'password', '2018-09-13 03:03:03', 111, '2018-09-15'),
  ('User3', 'user3@yandex.ru', 'password', '2018-09-12 04:04:04', 111, '2018-09-15');

INSERT INTO person_roles (role, person_id) VALUES
  ('ROLE_USER', 10000),
  ('ROLE_ADMIN', 10001),
  ('ROLE_USER', 10002),
  ('ROLE_USER', 10003);

INSERT INTO restaurants (name, description, date) VALUES
  ('Ararat', 'some description', '2018-02-02 02:02:02'),
  ('U Vano', 'some description', '2017-11-11 11:11:11'),
  ('Белый Лебедь', 'some description', '2018-01-02 03:04:05'),
  ('Черная Каракатица', 'some description', '2018-09-10 11:12:13'),
  ('Последний причал', 'some description', '2018-08-22 23:24:25'),
  ('Файна страва', '...', '2017-12-13 14:15:16'),
  ('For Kings', 'some description', '2018-01-11 23:59:59');

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

