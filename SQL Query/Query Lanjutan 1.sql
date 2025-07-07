-- Soal 1: Dasar SELECT
-- Tampilkan semua kolom dari tabel Customers.

SELECT *
FROM Customers

-- Soal 2: SELECT Kolom Tertentu
-- Tampilkan hanya kolom CustomerID, CustomerName, dan City dari tabel Customers.

SELECT CustomerID, CustomerName, City
FROM Customers

-- Soal 3: WHERE dengan Kondisi
-- Tampilkan semua pelanggan dari tabel Customers yang berasal dari kota "Jakarta".

SELECT *
FROM Customers
WHERE City = 'Jakarta'

--Soal 4: WHERE dengan Operator Perbandingan
--Tampilkan semua produk dari tabel Products yang memiliki harga lebih dari 100.

SELECT *
FROM Products
WHERE Price > 100

--Soal 5: WHERE dengan LIKE
--Cari semua pelanggan yang memiliki nama mengandung kata "Store" dalam tabel Customers.

SELECT *
FROM Customers
WHERE CustomerName LIKE '%Store%'