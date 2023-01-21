CREATE TYPE role AS ENUM ('ADMIN', 'USER');

CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255),
    role role
);

INSERT INTO users (email, password, role)
VALUES ('4336399@gmail.com', '$2a$10$hHINZ1WVjQltKiMiFYyp9OsxnblocMA7yBoCXEn6yarNG.53RR9DK', 'USER');