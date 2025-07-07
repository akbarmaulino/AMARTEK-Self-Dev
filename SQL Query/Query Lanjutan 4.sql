-- Soal 16: LIMIT dengan TOP (SQL Server)
-- Tampilkan 5 produk dengan harga tertinggi dari tabel Products.

SELECT TOP 5 * 
FROM Products
ORDER BY PRICE DESC

-- Soal 17: LIKE dengan Wildcard
-- Tampilkan semua pelanggan yang namanya dimulai dengan huruf "A".

SELECT *
FROM Customers
WHERE CustomerName LIKE '%a'


-- Soal 18: WHERE dengan DATE
--Tampilkan semua pesanan yang dibuat setelah tanggal 1 Januari 2024.

SELECT *
FROM Orders
WHERE OrderDate > '2024-01-01'

--Soal 19: INNER JOIN
-- Tampilkan daftar pesanan (Orders) beserta nama pelanggan (CustomerName) dari tabel Customers.

SELECT a.CustomerName, b.OrderID
FROM Customers a
INNER JOIN Orders b
ON a.CustomerID = b.CustomerID


--Soal 20: LEFT JOIN
--Tampilkan semua pelanggan beserta pesanan mereka (jika ada). Jika pelanggan belum pernah memesan, tetap tampilkan namanya.

SELECT a.CustomerName, b.OrderID
FROM Customers a
LEFT JOIN Orders b
ON a.CustomerID = b.CustomerID
