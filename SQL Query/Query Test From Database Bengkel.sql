USE DB_BENGKEL
--Soal Paket 1
SELECT * FROM Customers
SELECT * FROM Orders
SELECT * FROM Services
SELECT * FROM Vehicles

SELECT type_Kendaraan,merek 
FROM Vehicles 

SELECT type_Kendaraan,merek 
FROM Vehicles 
WHERE type_Kendaraan = 'Motor'

SELECT order_id, total_price
FROM Orders 
where total_price <= 50000

select * from Customers

select name
from Customers
where name LIKE 'Citra%'


--Soal Paket 2
SELECT * FROM Customers
SELECT * FROM Orders
SELECT * FROM Services
SELECT * FROM Vehicles


SELECT service_name, price
FROM Services
ORDER BY price DESC


SELECT service_name, price
FROM Services
ORDER BY price ASC


SELECT customer_id, order_date, total_price
FROM Orders
WHERE customer_id = 6
ORDER BY total_price ASC

SELECT id_customers, type_kendaraan, merek, plat_nomer
FROM Vehicles
WHERE id_Customers = 6 and merek = 'Honda CRV'

SELECT id_customers, type_kendaraan, merek, plat_nomer
FROM Vehicles
WHERE merek = 'Honda Beat' or merek = 'Honda CRV'

-- Paket Soal 3
