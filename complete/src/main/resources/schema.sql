CREATE TABLE customer (id SERIAL PRIMARY KEY, first_name VARCHAR(255), last_name VARCHAR(255));

CREATE TABLE account (id SERIAL PRIMARY KEY, customer_id INTEGER, bank_name VARCHAR(255));
