-- Soal 6: ORDER BY ASC
-- Tampilkan semua produk dari tabel Products, diurutkan berdasarkan harga terendah ke tertinggi.

SELECT *
FROM Products
ORDER BY Price ASC
 
-- Soal 7: ORDER BY DESC
-- Tampilkan semua pelanggan dari tabel Customers, diurutkan berdasarkan nama secara descending.
 
SELECT *
FROM Customers
ORDER BY CustomerName DESC

-- Soal 8: Kombinasi WHERE dan ORDER BY
-- Tampilkan semua karyawan dari tabel Employees yang bekerja di departemen "IT", lalu urutkan berdasarkan nama secara ascending.
 
 SELECT *
 FROM Employees 
 WHERE Department = 'IT'
 ORDER BY EmployeeName ASC

-- Soal 9: WHERE dengan AND
-- Tampilkan semua produk dari tabel Products yang memiliki harga lebih dari 50 dan stok lebih dari 10.

SELECT *
FROM Products
WHERE PRICE > 50 AND Stock > 10

-- Soal 10: WHERE dengan OR
-- Tampilkan semua pelanggan dari tabel Customers yang berasal dari "Jakarta" atau "Surabaya".
 
 SELECT *
 FROM Customers
 WHERE CITY = 'Jakarta' or City = 'Surabaya'
