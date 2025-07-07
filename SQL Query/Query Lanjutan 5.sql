--Soal 20: GROUP BY dengan COUNT
--Tampilkan jumlah pesanan yang dilakukan oleh setiap pelanggan.

SELECT CustomerID, COUNT(OrderID) AS JUMLAH_PEMESANAN
FROM Orders
GROUP BY CustomerID

--Soal 21: GROUP BY dengan SUM
--Tampilkan total harga (TotalAmount) yang dibelanjakan oleh setiap pelanggan.

SELECT CustomerID, SUM(TotalAmount) AS JUMLAH_PEMBELANJAAN
FROM Orders
GROUP BY CustomerID

--Soal 22: GROUP BY dengan HAVING
--Tampilkan pelanggan yang telah melakukan lebih dari 5 pesanan.

SELECT CustomerID, COUNT(OrderID) AS JUMLAH_PEMESANAN
FROM Orders
GROUP BY CustomerID
HAVING COUNT(OrderID) > 5

SELECT *
FROM Orders

--Soal 23: DELETE dengan WHERE
--Hapus semua pesanan yang dibuat sebelum tahun 2023.

INSERT INTO Orders (CustomerID, OrderDate, TotalAmount, Status, ProductID)
VALUES
(1, '2021-01-15', 15250000.00, 'Shipped', 1),  
(1, '2020-02-10', 500000.00, 'Pending', 2),    
(1, '2021-03-20', 1250000.00, 'Shipped', 3),   
(1, '2022-04-05', 250000.00, 'Pending', 4),    
(1, '2022-06-06', 5000000.00, 'Pending', 2),   
(2, '2022-02-10', 500000.00, 'Pending', 5),    
(3, '2022-12-20', 1800000.00, 'Shipped', 1),   
(4, '2022-01-05', 120000.00, 'Shipped', 3),    
(5, '2022-02-01', 250000.00, 'Pending', 2),    
(3, '2022-06-06', 5000000.00, 'Pending', 1);


SELECT *
FROM Orders
WHERE OrderDate < '2023-01-01'

DELETE
FROM Orders
WHERE OrderDate < '2023-01-01'
-- Soal 24: UPDATE dengan WHERE Update status semua pesanan yang masih 'Pending' menjadi 'Shipped'..

UPDATE Orders
SET Status = 'Shipped' 
WHERE Status = 'Pending'

SELECT *
FROM Orders
WHERE Status = 'Pending'

-- Soal 25: UPDATE dengan WHERE Update dan exctact status semua pesanan yang masih Shipped dan order date bulan 12 2023 menjadi Done.

UPDATE Orders
SET Status = 'Done'
WHERE Status = 'Shipped' 
AND OrderDate BETWEEN '2023-12-01' AND '2023-12-31';

SELECT *
FROM Orders
WHERE Status = 'Done'

