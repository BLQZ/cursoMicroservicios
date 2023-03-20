INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES('andres', '$2a$10$RKOYgh.xogZ.iI1.UYbNBuYlkjxwFxGri63GTMUvjSMUjdUtrSDNa', 1, 'Andres', 'Medina', 'andres.medina@oficina.com');
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES('vanessa', '$2a$10$bYU5gCs8O1PEHPQtp9SABObnQDPOQL/Y.iwJyrdkVdKk3IviKeyGi', 1, 'Vanessa', 'Zambrano', 'vanessa@test.com');

INSERT INTO `roles` (nombre) VALUES ('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 1);