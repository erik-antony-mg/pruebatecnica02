-- #creacion de roles
INSERT INTO roles (roles_id, role_name)
SELECT 1, 'ADMIN'
    WHERE NOT EXISTS (
  SELECT 1 FROM roles WHERE roles_id = 1
);
INSERT INTO roles (roles_id, role_name)
SELECT 2, 'INVITADO'
    WHERE NOT EXISTS (
  SELECT 2 FROM roles WHERE roles_id = 2
);

-- Inserción de usuarios
INSERT INTO usuarios (usuario_id, name, email, password, fecha_creacion, fecha_modificacion, is_active, last_login)
VALUES
    (UUID(), 'User1', 'user1@example.com', 'password1', NOW(), NOW(), TRUE, NOW()),
    (UUID(), 'User2', 'user2@example.com', 'password2', NOW(), NOW(), TRUE, NOW()),
    (UUID(), 'User3', 'user3@example.com', 'password3', NOW(), NOW(), TRUE, NOW()),
    (UUID(), 'User4', 'user4@example.com', 'password4', NOW(), NOW(), TRUE, NOW()),
    (UUID(), 'User5', 'user5@example.com', 'password5', NOW(), NOW(), TRUE, NOW()),
    (UUID(), 'User6', 'user6@example.com', 'password6', NOW(), NOW(), TRUE, NOW()),
    (UUID(), 'User7', 'user7@example.com', 'password7', NOW(), NOW(), TRUE, NOW()),
    (UUID(), 'User8', 'user8@example.com', 'password8', NOW(), NOW(), TRUE, NOW()),
    (UUID(), 'User9', 'user9@example.com', 'password9', NOW(), NOW(), TRUE, NOW()),
    (UUID(), 'User10', 'user10@example.com', 'password10', NOW(), NOW(), TRUE, NOW());

-- Inserción de teléfonos
INSERT INTO phones (phone_id, number, city_code, contry_code, usuario_id)
VALUES
    (UUID(), '1234567890', '01', '1', (SELECT usuario_id FROM usuarios WHERE email = 'user1@example.com')),
    (UUID(), '2345678901', '02', '1', (SELECT usuario_id FROM usuarios WHERE email = 'user2@example.com')),
    (UUID(), '3456789012', '03', '1', (SELECT usuario_id FROM usuarios WHERE email = 'user3@example.com')),
    (UUID(), '4567890123', '04', '1', (SELECT usuario_id FROM usuarios WHERE email = 'user4@example.com')),
    (UUID(), '5678901234', '05', '1', (SELECT usuario_id FROM usuarios WHERE email = 'user5@example.com')),
    (UUID(), '6789012345', '06', '1', (SELECT usuario_id FROM usuarios WHERE email = 'user6@example.com')),
    (UUID(), '7890123456', '07', '1', (SELECT usuario_id FROM usuarios WHERE email = 'user7@example.com')),
    (UUID(), '8901234567', '08', '1', (SELECT usuario_id FROM usuarios WHERE email = 'user8@example.com')),
    (UUID(), '9012345678', '09', '1', (SELECT usuario_id FROM usuarios WHERE email = 'user9@example.com')),
    (UUID(), '0123456789', '10', '1', (SELECT usuario_id FROM usuarios WHERE email = 'user10@example.com'));

-- Conexión de usuarios con roles
INSERT INTO usuario_roles (usuario_id, role_id)
VALUES
    ((SELECT usuario_id FROM usuarios WHERE email = 'user1@example.com'), 1),
    ((SELECT usuario_id FROM usuarios WHERE email = 'user2@example.com'), 1),
    ((SELECT usuario_id FROM usuarios WHERE email = 'user3@example.com'), 2),
    ((SELECT usuario_id FROM usuarios WHERE email = 'user4@example.com'), 2),
    ((SELECT usuario_id FROM usuarios WHERE email = 'user5@example.com'), 1),
    ((SELECT usuario_id FROM usuarios WHERE email = 'user6@example.com'), 2),
    ((SELECT usuario_id FROM usuarios WHERE email = 'user7@example.com'), 1),
    ((SELECT usuario_id FROM usuarios WHERE email = 'user8@example.com'), 2),
    ((SELECT usuario_id FROM usuarios WHERE email = 'user9@example.com'), 1),
    ((SELECT usuario_id FROM usuarios WHERE email = 'user10@example.com'), 2);

