INSERT INTO roles (id, role_name) values (1, 'ADMIN');
INSERT INTO roles (id, role_name) values (2, 'VIP');
INSERT INTO roles (id, role_name) values (3, 'USER');

INSERT INTO utilisateur (id, util_enabled, util_first_name, util_last_name, util_login, util_mail_addr, util_pwd, roles_id) values (UNHEX(REPLACE(UUID(),'-','')), 1, 'admin', 'admin', 'admbdd', 'nomail', '$2y$10$88jDbl/0nxfZTDMNiZO0Vejh2VhhUx9QQZXC6Sz/i9TjbDu9hZCCe', 1);