-- Insert roles
INSERT INTO roles (role) VALUES ('ADMIN'), ('USER');

-- Insert admin user with hashed password (use BCrypt for hashing)
-- The password is 'adminpass' hashed using BCrypt
INSERT INTO users (email, first_name, last_name, password, active)
VALUES ('admin@example.com', 'Admin', 'User', 'adminpass', true);
-- VALUES ('admin@example.com', 'Admin', 'User', '$2a$10$examplehashedpassword1', true);


-- Insert regular user with hashed password (use BCrypt for hashing)
-- The password is 'userpass' hashed using BCrypt
INSERT INTO users (email, first_name, last_name, password, active)
VALUES ('admin@example.com', 'Admin', 'User', 'userpass', true);
-- VALUES ('user@example.com', 'Regular', 'User', '$2a$10$examplehashedpassword2', true);


-- Assign ROLE_ADMIN to the admin user (assuming admin has user_id = 1 and role_id = 1 for ROLE_ADMIN)
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);

-- Assign ROLE_USER to the regular user (assuming regular user has user_id = 2 and role_id = 2 for ROLE_USER)
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
