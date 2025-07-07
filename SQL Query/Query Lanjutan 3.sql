-- Soal 11: WHERE dengan IN
-- Tampilkan semua pesanan (Orders) yang statusnya "Shipped" atau "Pending".

SELECT *
FROM Orders
WHERE Status IN ('Shipped','Pending')

-- Soal 12: WHERE dengan BETWEEN
-- Tampilkan semua produk dari tabel Products yang memiliki harga antara 100 dan 500.

SELECT *
FROM Products
WHERE Price BETWEEN 100 AND 500

-- Soal 13: ORDER BY Multiple Kolom
-- Tampilkan semua pelanggan dari tabel Customers, urutkan berdasarkan City secara ascending lalu CustomerName secara descending.

SELECT *
FROM Customers
ORDER BY CITY ASC, CUSTOMERNAME DESC

-- Soal 14: WHERE dengan NOT
-- Tampilkan semua karyawan dari tabel Employees yang bukan berasal dari departemen "HR".

SELECT *
FROM Employees
WHERE NOT Department = 'HR'

-- Soal 15: WHERE dengan NULL
-- Tampilkan semua pelanggan yang belum mengisi nomor telepon (PhoneNumber masih NULL).

SELECT *
FROM Customers
WHERE PhoneNumber IS NULL