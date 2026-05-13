USE bilabonnement;

INSERT INTO employee (employee_id, first_name, last_name, password, work_email, role) VALUES (1, 'rune', 'runesen','demo','demo1','dataregistrering'),
                                                                                             (2, 'daniel', 'danielsen','demo','demo2','skade-udbedring'),
                                                                                             (3, 'nico', 'nicosen','demo','demo3','forretningsudvikler');


-- Brands
INSERT INTO car_brand (brand_name)
VALUES ('Toyota'),
       ('Volkswagen'),
       ('BMW'),
       ('Mercedes-Benz'),
       ('Audi'),
       ('Skoda'),
       ('Hyundai'),
       ('Kia'),
       ('Peugeot'),
       ('Renault');

-- Models
INSERT INTO car_model (car_brand_id, model_name, equipment_level, shift_gear_type, fuel_type)
VALUES (1, 'Yaris', 'Base', 'Manual', 'Petrol'),
       (1, 'Corolla', 'Comfort', 'Automatic', 'Hybrid'),
       (1, 'RAV4', 'Executive', 'Automatic', 'Hybrid'),

       (2, 'Polo', 'Life', 'Manual', 'Petrol'),
       (2, 'Golf', 'Style', 'Automatic', 'Petrol'),
       (2, 'Passat', 'Business', 'Automatic', 'Diesel'),

       (3, '1 Series', 'Sport', 'Automatic', 'Petrol'),
       (3, '3 Series', 'M Sport', 'Automatic', 'Diesel'),
       (3, 'X1', 'X-Line', 'Automatic', 'Hybrid'),

       (4, 'A-Class', 'Progressive', 'Automatic', 'Petrol'),
       (4, 'C-Class', 'AMG Line', 'Automatic', 'Diesel'),
       (4, 'GLA', 'Urban', 'Automatic', 'Hybrid'),

       (5, 'A3', 'Advanced', 'Automatic', 'Petrol'),
       (5, 'A4', 'S Line', 'Automatic', 'Diesel'),
       (5, 'Q3', 'Prestige', 'Automatic', 'Hybrid'),

       (6, 'Fabia', 'Essence', 'Manual', 'Petrol'),
       (6, 'Octavia', 'Selection', 'Automatic', 'Diesel'),
       (6, 'Enyaq', 'Sportline', 'Automatic', 'Electric'),

       (7, 'i20', 'Essential', 'Manual', 'Petrol'),
       (7, 'i30', 'Advanced', 'Automatic', 'Petrol'),
       (7, 'Tucson', 'N Line', 'Automatic', 'Hybrid'),

       (8, 'Rio', 'Vision', 'Manual', 'Petrol'),
       (8, 'Ceed', 'Upgrade', 'Automatic', 'Petrol'),
       (8, 'Sportage', 'GT Line', 'Automatic', 'Hybrid'),

       (9, '208', 'Active', 'Manual', 'Petrol'),
       (9, '308', 'Allure', 'Automatic', 'Hybrid'),
       (9, '3008', 'GT', 'Automatic', 'Diesel'),

       (10, 'Clio', 'Evolution', 'Manual', 'Petrol'),
       (10, 'Megane', 'Techno', 'Automatic', 'Hybrid'),
       (10, 'Austral', 'Iconic', 'Automatic', 'Hybrid');

-- Cars
INSERT INTO car (car_model_id, vin_number, license_plate, monthly_price, status, colour, registration_date)
VALUES (1, 'JT100000000000001', 'AB12345', 2999.00, 'Ledig', 'White', '2022-01-15'),
       (1, 'JT100000000000002', 'AB12346', 2999.00, 'Ledig', 'Black', '2022-03-11'),
       (1, 'JT100000000000003', 'AB12347', 2999.00, 'Ledig', 'Silver', '2021-11-09'),

       (2, 'JT200000000000001', 'AC12345', 3499.00, 'Ledig', 'Blue', '2023-02-20'),
       (2, 'JT200000000000002', 'AC12346', 3499.00, 'Ledig', 'Grey', '2023-04-03'),
       (2, 'JT200000000000003', 'AC12347', 3499.00, 'Ledig', 'Red', '2022-12-14'),

       (3, 'JT300000000000001', 'AD12345', 4599.00, 'Ledig', 'Black', '2024-01-05'),
       (3, 'JT300000000000002', 'AD12346', 4599.00, 'Ledig', 'White', '2024-02-18'),
       (3, 'JT300000000000003', 'AD12347', 4599.00, 'Ledig', 'Green', '2023-10-29'),

       (4, 'VW100000000000001', 'BA12345', 2799.00, 'Ledig', 'White', '2021-08-17'),
       (4, 'VW100000000000002', 'BA12346', 2799.00, 'Ledig', 'Blue', '2022-05-23'),
       (4, 'VW100000000000003', 'BA12347', 2799.00, 'Ledig', 'Black', '2021-12-02'),

       (5, 'VW200000000000001', 'BB12345', 3599.00, 'Ledig', 'Silver', '2023-01-12'),
       (5, 'VW200000000000002', 'BB12346', 3599.00, 'Ledig', 'Grey', '2023-06-08'),
       (5, 'VW200000000000003', 'BB12347', 3599.00, 'Ledig', 'Red', '2022-09-27'),

       (6, 'VW300000000000001', 'BC12345', 3999.00, 'Ledig', 'Black', '2020-07-30'),
       (6, 'VW300000000000002', 'BC12346', 3999.00, 'Ledig', 'White', '2021-03-16'),
       (6, 'VW300000000000003', 'BC12347', 3999.00, 'Ledig', 'Blue', '2020-11-21'),

       (7, 'BM100000000000001', 'CA12345', 3899.00, 'Ledig', 'Black', '2022-04-10'),
       (7, 'BM100000000000002', 'CA12346', 3899.00, 'Ledig', 'White', '2022-07-01'),
       (7, 'BM100000000000003', 'CA12347', 3899.00, 'Ledig', 'Grey', '2021-10-05'),

       (8, 'BM200000000000001', 'CB12345', 4999.00, 'Ledig', 'Blue', '2023-03-24'),
       (8, 'BM200000000000002', 'CB12346', 4999.00, 'Ledig', 'Black', '2023-08-19'),
       (8, 'BM200000000000003', 'CB12347', 4999.00, 'Ledig', 'Silver', '2022-06-13'),

       (9, 'BM300000000000001', 'CC12345', 5299.00, 'Ledig', 'Green', '2024-01-27'),
       (9, 'BM300000000000002', 'CC12346', 5299.00, 'Ledig', 'White', '2024-03-15'),
       (9, 'BM300000000000003', 'CC12347', 5299.00, 'Ledig', 'Black', '2023-09-08'),

       (10, 'MB100000000000001', 'DA12345', 3799.00, 'Ledig', 'White', '2022-02-09'),
       (10, 'MB100000000000002', 'DA12346', 3799.00, 'Ledig', 'Black', '2022-06-30'),
       (10, 'MB100000000000003', 'DA12347', 3799.00, 'Ledig', 'Red', '2021-12-12'),

       (11, 'MB200000000000001', 'DB12345', 5699.00, 'Ledig', 'Grey', '2023-04-28'),
       (11, 'MB200000000000002', 'DB12346', 5699.00, 'Ledig', 'Blue', '2023-07-17'),
       (11, 'MB200000000000003', 'DB12347', 5699.00, 'Ledig', 'Silver', '2022-10-01'),

       (12, 'MB300000000000001', 'DC12345', 5499.00, 'Ledig', 'Black', '2024-02-11'),
       (12, 'MB300000000000002', 'DC12346', 5499.00, 'Ledig', 'White', '2024-04-22'),
       (12, 'MB300000000000003', 'DC12347', 5499.00, 'Ledig', 'Grey', '2023-11-06'),

       (13, 'AU100000000000001', 'EA12345', 3699.00, 'Ledig', 'Blue', '2022-01-19'),
       (13, 'AU100000000000002', 'EA12346', 3699.00, 'Ledig', 'Black', '2022-05-05'),
       (13, 'AU100000000000003', 'EA12347', 3699.00, 'Ledig', 'White', '2021-09-14'),

       (14, 'AU200000000000001', 'EB12345', 4799.00, 'Ledig', 'Grey', '2023-02-07'),
       (14, 'AU200000000000002', 'EB12346', 4799.00, 'Ledig', 'Silver', '2023-06-26'),
       (14, 'AU200000000000003', 'EB12347', 4799.00, 'Ledig', 'Black', '2022-08-30'),

       (15, 'AU300000000000001', 'EC12345', 5199.00, 'Ledig', 'White', '2024-01-09'),
       (15, 'AU300000000000002', 'EC12346', 5199.00, 'Ledig', 'Blue', '2024-03-28'),
       (15, 'AU300000000000003', 'EC12347', 5199.00, 'Ledig', 'Green', '2023-10-17'),

       (16, 'SK100000000000001', 'FA12345', 2599.00, 'Ledig', 'White', '2021-07-04'),
       (16, 'SK100000000000002', 'FA12346', 2599.00, 'Ledig', 'Red', '2022-02-25'),
       (16, 'SK100000000000003', 'FA12347', 2599.00, 'Ledig', 'Black', '2021-11-18'),

       (17, 'SK200000000000001', 'FB12345', 3399.00, 'Ledig', 'Grey', '2023-01-16'),
       (17, 'SK200000000000002', 'FB12346', 3399.00, 'Ledig', 'Blue', '2023-05-12'),
       (17, 'SK200000000000003', 'FB12347', 3399.00, 'Ledig', 'Silver', '2022-09-03'),

       (18, 'SK300000000000001', 'FB22345', 4299.00, 'Ledig', 'Black', '2024-02-01'),
       (18, 'SK300000000000002', 'FB22346', 4299.00, 'Ledig', 'White', '2024-04-14'),
       (18, 'SK300000000000003', 'FB22347', 4299.00, 'Ledig', 'Green', '2023-12-07'),

       (19, 'HY100000000000001', 'GA12345', 2499.00, 'Ledig', 'Blue', '2021-06-22'),
       (19, 'HY100000000000002', 'GA12346', 2499.00, 'Ledig', 'Black', '2022-01-31'),
       (19, 'HY100000000000003', 'GA12347', 2499.00, 'Ledig', 'White', '2021-10-26'),

       (20, 'HY200000000000001', 'GB12345', 3199.00, 'Ledig', 'Grey', '2023-03-04'),
       (20, 'HY200000000000002', 'GB12346', 3199.00, 'Ledig', 'Red', '2023-07-09'),
       (20, 'HY200000000000003', 'GB12347', 3199.00, 'Ledig', 'Silver', '2022-11-15'),

       (21, 'HY300000000000001', 'GC12345', 4099.00, 'Ledig', 'Black', '2024-01-21'),
       (21, 'HY300000000000002', 'GC12346', 4099.00, 'Ledig', 'Blue', '2024-03-10'),
       (21, 'HY300000000000003', 'GC12347', 4099.00, 'Ledig', 'White', '2023-09-25'),

       (22, 'KI100000000000001', 'HA12345', 2399.00, 'Ledig', 'White', '2021-05-13'),
       (22, 'KI100000000000002', 'HA12346', 2399.00, 'Ledig', 'Black', '2021-12-28'),
       (22, 'KI100000000000003', 'HA12347', 2399.00, 'Ledig', 'Blue', '2021-09-07'),

       (23, 'KI200000000000001', 'HB12345', 3099.00, 'Ledig', 'Grey', '2023-02-14'),
       (23, 'KI200000000000002', 'HB12346', 3099.00, 'Ledig', 'Silver', '2023-06-18'),
       (23, 'KI200000000000003', 'HB12347', 3099.00, 'Ledig', 'Red', '2022-10-11'),

       (24, 'KI300000000000001', 'HC12345', 3999.00, 'Ledig', 'Green', '2024-01-03'),
       (24, 'KI300000000000002', 'HC12346', 3999.00, 'Ledig', 'White', '2024-03-22'),
       (24, 'KI300000000000003', 'HC12347', 3999.00, 'Ledig', 'Black', '2023-11-30'),

       (25, 'PE100000000000001', 'JA12345', 2299.00, 'Ledig', 'Yellow', '2021-04-08'),
       (25, 'PE100000000000002', 'JA12346', 2299.00, 'Ledig', 'Blue', '2021-11-19'),
       (25, 'PE100000000000003', 'JA12347', 2299.00, 'Ledig', 'White', '2021-08-24'),

       (26, 'PE200000000000001', 'JB12345', 2999.00, 'Ledig', 'Black', '2023-01-29'),
       (26, 'PE200000000000002', 'JB12346', 2999.00, 'Ledig', 'Grey', '2023-05-27'),
       (26, 'PE200000000000003', 'JB12347', 2999.00, 'Ledig', 'Red', '2022-09-19'),

       (27, 'PE300000000000001', 'JC12345', 3899.00, 'Ledig', 'White', '2024-02-06'),
       (27, 'PE300000000000002', 'JC12346', 3899.00, 'Ledig', 'Blue', '2024-04-09'),
       (27, 'PE300000000000003', 'JC12347', 3899.00, 'Ledig', 'Black', '2023-12-20'),

       (28, 'RE100000000000001', 'KA12345', 2199.00, 'Ledig', 'Red', '2021-03-02'),
       (28, 'RE100000000000002', 'KA12346', 2199.00, 'Ledig', 'White', '2021-10-16'),
       (28, 'RE100000000000003', 'KA12347', 2199.00, 'Ledig', 'Black', '2021-07-28'),

       (29, 'RE200000000000001', 'KB12345', 2899.00, 'Ledig', 'Blue', '2023-02-01'),
       (29, 'RE200000000000002', 'KB12346', 2899.00, 'Ledig', 'Grey', '2023-06-02'),
       (29, 'RE200000000000003', 'KB12347', 2899.00, 'Ledig', 'Silver', '2022-10-24'),

       (30, 'RE300000000000001', 'KC12345', 3799.00, 'Ledig', 'Black', '2024-01-14'),
       (30, 'RE300000000000002', 'KC12346', 3799.00, 'Ledig', 'Green', '2024-03-31'),
       (30, 'RE300000000000003', 'KC12347', 3799.00, 'Ledig', 'White', '2023-11-12');

-- Customers
INSERT INTO customer (first_name, last_name, drivers_license_number, cpr_number, email, phone_number, status)
VALUES ('Anders', 'Jensen', 12345678, '010190-1234', 'demo@email.com', 12345678, 'Aktiv'),
       ('Mette', 'Hansen', 87654321, '020285-5678', 'demo2@email.com', 87654321, 'Aktiv');


-- Customer Addresses
INSERT INTO customer_address (customer_id, zip_code, street_name, house_number, floor)
VALUES (1, '2500', 'Demovej', '1', 'st'),
       (2, '4400', 'Testvej', '2', '1');


-- Rental Contracts (car_id 1 og 2 er begge Toyota Yaris med status Ledig)
INSERT INTO rental_contract (employee_id, customer_id, car_id, start_date, end_date, pickup_location, status, subscription_type)
VALUES (1, 1, 1, '2024-01-01', '2024-06-01', 'København', 'Afsluttet', 'Limited'),
       (1, 2, 2, '2024-02-01', '2024-07-01', 'København', 'Aktiv', 'Limited');


INSERT INTO damage_category (name, standard_price, description)
VALUES ('Ridset dør', 1500.00, 'Ridser på bildør'),
       ('Knust rude', 3000.00, 'Beskadiget forrude'),
       ('Bule', 2000.00, 'Bule på karrosseri');


