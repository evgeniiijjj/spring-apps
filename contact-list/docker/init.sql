CREATE SCHEMA IF NOT EXISTS contact_schema;

CREATE TABLE IF NOT EXISTS contact_schema.contacts
(
    id SERIAL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(20) NOT NULL,
    phone VARCHAR(20) NOT NULL
)