-- ============================================================
-- Farmer-to-Market Direct Selling Platform
-- MySQL Database Setup Script
-- ============================================================
-- How to run this file:
--   1. Open a terminal / command prompt.
--   2. Log in to MySQL:  mysql -u root -p
--   3. Enter your MySQL root password (default in this project: root)
--   4. Run:  SOURCE full/path/to/schema.sql;
--      (or copy-paste the contents into the MySQL prompt)
-- ============================================================

-- 1. Create the database
CREATE DATABASE IF NOT EXISTS farmer_market;

-- 2. Select it for use
USE farmer_market;

-- 3. Farmers table
CREATE TABLE IF NOT EXISTS farmers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    location VARCHAR(150) NOT NULL
);

-- 4. Customers table
CREATE TABLE IF NOT EXISTS customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    address VARCHAR(200) NOT NULL
);

-- 5. Products table (each product belongs to one farmer)
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    farmer_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_products_farmer
        FOREIGN KEY (farmer_id) REFERENCES farmers(id)
        ON DELETE CASCADE
);

-- 6. Orders table (each order links a customer to a product)
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    order_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    CONSTRAINT fk_orders_customer
        FOREIGN KEY (customer_id) REFERENCES customers(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_orders_product
        FOREIGN KEY (product_id) REFERENCES products(id)
        ON DELETE CASCADE
);

-- ============================================================
-- Quick sanity check: list all tables that were created
-- ============================================================
SHOW TABLES;
