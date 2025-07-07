INSERT INTO Customers (id_Customers, name, phone_number, address) VALUES
(1, 'Andi Saputra', '081234567890', 'Jl. Melati No.10'),
(2, 'Budi Santoso', '082345678901', 'Jl. Mawar No.25'),
(3, 'Citra Ayu', '083456789012', 'Jl. Kenanga No.1'),
(4, 'Citra Saputra', '083412345678', 'Jl. Kenanga No.2'),
(5, 'Citra Santoso', '083409876543', 'Jl. Kenangan No.5'),
(6, 'Santoso Ayu', '083425361821', 'Jl. Kenanga No.10')

INSERT INTO Vehicles (id_Kendaraan, id_Customers, type_Kendaraan, merek, plat_Nomer) VALUES
(1, 1, 'Mobil', 'Toyota Avanza', 'B 1234 CD'),
(2, 2, 'Motor', 'Honda Beat', 'B 5678 EF'),
(3, 3, 'Mobil', 'Daihatsu Xenia', 'B 9876 GH'),
(4, 4, 'Mobil', 'Daihatsu Rush', 'B 1234 AB'),
(5, 5, 'Mobil', 'Toyota Terios', 'B 4201 OK'),
(6, 6, 'Mobil', 'Honda CRV', 'B 1201 CE'),
(7, 6, 'Mobil', 'Daihatsu Agya', 'B 4912 AR')

INSERT INTO Services (service_id, service_name, price) VALUES
(1, 'Ganti Oli', 100000),
(2, 'Servis Rem', 150000),
(3, 'Tune Up Mesin', 250000),
(4, 'Cuci Mobil', 50000);

INSERT INTO Orders (order_id, customer_id, vehicle_id, service_id, order_date, repair_status, total_price) VALUES
(1, 1, 1, 1, '2025-05-01', 'completed', 100000),
(2, 2, 2, 2, '2025-05-02', 'in_progress', 150000),
(3, 3, 3, 3, '2025-05-03', 'pending', 250000),
(4, 1, 1, 1, '2025-05-04', 'completed', 100000),
(5, 4, 4, 3, '2025-05-05', 'completed', 250000),
(6, 5, 5, 4, '2025-05-06', 'completed', 50000),
(7, 6, 6, 1, '2025-05-07', 'completed', 100000),
(8, 6, 6, 2, '2025-05-07', 'completed', 150000)


SELECT * FROM Customers
SELECT * FROM Orders
SELECT * FROM Services
SELECT * FROM Vehicles

