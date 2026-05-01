INSERT INTO app_user (id, full_name, email, password, user_role)
VALUES (nextval('user_id_seq'),
        'Admin User',
        'admin@constructionsite.com',
        '$2a$10$bHjpskQtaSW8g5AvDjoK2O4T2QRefLgYSJERw0ZQWldid5VQp6Wg6',
        'ADMIN'),
       (nextval('user_id_seq'),
        'Regular User',
        'user@constructionsite.com',
        '$2a$10$bHjpskQtaSW8g5AvDjoK2O4T2QRefLgYSJERw0ZQWldid5VQp6Wg6',
        'USER');