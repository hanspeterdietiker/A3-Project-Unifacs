INSERT INTO app_users (id, name, username, password, role) VALUES (100, 'Luis', 'luis@email.com', '$2a$12$RK43Ni/UANSiMFsT78wqKeavNY44D2RxWJbr8kRrVw8FLWwQ.o1p.', 'ROLE_DEFAULT');
INSERT INTO app_users (id, name, username, password, role) VALUES (101, 'Maria', 'maria@email.com', '$2a$12$RK43Ni/UANSiMFsT78wqKeavNY44D2RxWJbr8kRrVw8FLWwQ.o1p.', 'ROLE_DEFAULT');
INSERT INTO app_users (id, name, username, password, role) VALUES (102, 'Bob', 'bobs@email.com', '$2a$12$RK43Ni/UANSiMFsT78wqKeavNY44D2RxWJbr8kRrVw8FLWwQ.o1p.', 'ROLE_DEFAULT');
INSERT INTO app_users (id, name, username, password, role) VALUES (103, 'Admin', 'admin@email.com', '$2a$12$RK43Ni/UANSiMFsT78wqKeavNY44D2RxWJbr8kRrVw8FLWwQ.o1p.', 'ROLE_ADMIN');

-- Comentário 1
INSERT INTO app_Comment_User (text, creation_date, name_create, date_update, name_update, user_id, restaurant_id)
VALUES ('Este é o primeiro comentário.', '2024-04-12 10:30:00', 'João', '2024-04-12 10:30:00', 'João', 100, null);

-- Comentário 2
INSERT INTO app_Comment_User (text, creation_date, name_create, date_update, name_update, user_id, restaurant_id)
VALUES ('Este é o segundo comentário.', '2024-04-12 11:45:00', 'Maria', '2024-04-12 11:45:00', 'Maria', 100, null);

-- Comentário 3
INSERT INTO app_Comment_User (text, creation_date, name_create, date_update, name_update, user_id, restaurant_id)
VALUES ('Este é o terceiro comentário.', '2024-04-12 13:20:00', 'Pedro', '2024-04-12 13:20:00', 'Pedro', 101, null);
