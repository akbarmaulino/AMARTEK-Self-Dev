Create Database DB_BENGKEL

USE DB_BENGKEL

CREATE TABLE Customers (
    id_Customers INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15),
    address VARCHAR(255)
)

CREATE TABLE Vehicles (
    id_Kendaraan INT PRIMARY KEY,
    id_Customers INT,
    type_Kendaraan VARCHAR(50),
    merek VARCHAR(50),
	plat_Nomer VARCHAR(15)
);

CREATE TABLE Services (
    service_id INT PRIMARY KEY,
    service_name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 0) NOT NULL
);

CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    customer_id INT,
    vehicle_id INT,
    service_id INT,
    order_date DATE,
    repair_status VARCHAR(19),
    total_price DECIMAL(10, 0)
)
