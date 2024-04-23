
INSERT INTO app_Restaurant (id, name, cep, state, city, street, district) VALUES (10, 'Restaurante A', '12345678', 'Estado A', 'Cidade A', 'Rua A', 'Bairro A');
INSERT INTO app_Restaurant (id, name, cep, state, city, street, district) VALUES (20, 'Restaurante B', '23456789', 'Estado B', 'Cidade B', 'Rua B', 'Bairro B');
INSERT INTO app_Restaurant (id, name, cep, state, city, street, district) VALUES (30, 'Restaurante C', '34567890', 'Estado C', 'Cidade C', 'Rua C', 'Bairro C');

INSERT INTO app_users (id, name, username, password, role) VALUES (100, 'Luis', 'luis@email.com', '$2a$12$RK43Ni/UANSiMFsT78wqKeavNY44D2RxWJbr8kRrVw8FLWwQ.o1p.', 'ROLE_DEFAULT');
INSERT INTO app_users (id, name, username, password, role) VALUES (103, 'Admin', 'admin@email.com', '$2a$12$RK43Ni/UANSiMFsT78wqKeavNY44D2RxWJbr8kRrVw8FLWwQ.o1p.', 'ROLE_ADMIN');
