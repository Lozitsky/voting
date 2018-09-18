DELETE FROM person_roles;
DELETE FROM votes;
DELETE FROM persons;
DELETE FROM dishes;
DELETE FROM restaurants;

ALTER SEQUENCE global_seq RESTART WITH 10000;

INSERT INTO persons (name, email, password, last_voting, last_id) VALUES
  ('User', 'user@yandex.ru', 'password', '2018-09-15', 111),
  ('Admin', 'admin@gmail.com', 'password', '2018-09-14', 111),
  ('User2', 'user2@yandex.ru', 'password', '2018-09-13', 111),
  ('User3', 'user3@yandex.ru', 'password', '2018-09-12', 111);

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

INSERT INTO votes (restaurant_id, date) VALUES
  (10004, '2018-09-18'),
  (10005, '2018-09-18'),
  (10006, '2018-09-18'),
  (10007, '2018-09-18'),
  (10008, '2018-09-18'),
  (10009, '2018-09-18'),
  (10010, '2018-09-18');

INSERT INTO dishes (name, price, restaurant_id)VALUES
       ('Завтрак', 500, 10004),
       ('Обед', 1000, 10004),
       ('Ужин', 500, 10005),
       ('Завтрак', 500, 10005),
       ('Обед', 1000, 10006),
       ('Ужин', 510, 10006),
       ('Super ланч', 510, 10005),
       ('Super ужин', 1500, 10005);

