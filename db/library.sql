DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS reserve;

CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN NOT NULL DEFAULT FALSE,
    is_blocked BOOLEAN NOT NULL DEFAULT FALSE,
    role_id INT NOT NULL,
    FOREIGN KEY(role_id) REFERENCES role(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE book (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(1024) NOT NULL,
    author VARCHAR(1024) NOT NULL,
    publisher VARCHAR(1024) NOT NULL,
    published_date DATE NOT NULL,
    quantity INT NOT NULL,
);

CREATE TABLE reserve (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT,
    book_id BIGINT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY(account_id) REFERENCES account(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY(book_id) REFERENCES book(id) ON DELETE RESTRICT ON UPDATE CASCADE
);


