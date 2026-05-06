USE bilabonnement;

-- Brands
INSERT INTO car_brand (brand_name) VALUES
                                       ('Toyota'),
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
INSERT INTO car_model (car_brand_id, model_name, equipment_level, shift_gear_type) VALUES
                                                                                       (1, 'Yaris', 'Base', 'Manual'),
                                                                                       (1, 'Corolla', 'Comfort', 'Automatic'),
                                                                                       (1, 'RAV4', 'Executive', 'Automatic'),

                                                                                       (2, 'Polo', 'Life', 'Manual'),
                                                                                       (2, 'Golf', 'Style', 'Automatic'),
                                                                                       (2, 'Passat', 'Business', 'Automatic'),

                                                                                       (3, '1 Series', 'Sport', 'Automatic'),
                                                                                       (3, '3 Series', 'M Sport', 'Automatic'),
                                                                                       (3, 'X1', 'X-Line', 'Automatic'),

                                                                                       (4, 'A-Class', 'Progressive', 'Automatic'),
                                                                                       (4, 'C-Class', 'AMG Line', 'Automatic'),
                                                                                       (4, 'GLA', 'Urban', 'Automatic'),

                                                                                       (5, 'A3', 'Advanced', 'Automatic'),
                                                                                       (5, 'A4', 'S Line', 'Automatic'),
                                                                                       (5, 'Q3', 'Prestige', 'Automatic'),

                                                                                       (6, 'Fabia', 'Essence', 'Manual'),
                                                                                       (6, 'Octavia', 'Selection', 'Automatic'),
                                                                                       (6, 'Enyaq', 'Sportline', 'Automatic'),

                                                                                       (7, 'i20', 'Essential', 'Manual'),
                                                                                       (7, 'i30', 'Advanced', 'Automatic'),
                                                                                       (7, 'Tucson', 'N Line', 'Automatic'),

                                                                                       (8, 'Rio', 'Vision', 'Manual'),
                                                                                       (8, 'Ceed', 'Upgrade', 'Automatic'),
                                                                                       (8, 'Sportage', 'GT Line', 'Automatic'),

                                                                                       (9, '208', 'Active', 'Manual'),
                                                                                       (9, '308', 'Allure', 'Automatic'),
                                                                                       (9, '3008', 'GT', 'Automatic'),

                                                                                       (10, 'Clio', 'Evolution', 'Manual'),
                                                                                       (10, 'Megane', 'Techno', 'Automatic'),
                                                                                       (10, 'Austral', 'Iconic', 'Automatic');

-- Cars
INSERT INTO car (car_model_id, vin_number, license_plate, monthly_price, status, colour) VALUES
                                                                                             (1,  'JT100000000000001', 'AB12345', 2999.00, 'Ledig', 'Hvid'),
                                                                                             (1,  'JT100000000000002', 'AB12346', 2999.00, 'Udlejet', 'Sort'),
                                                                                             (1,  'JT100000000000003', 'AB12347', 2999.00, 'Tilbageleveret', 'Sølv'),

                                                                                             (2,  'JT200000000000001', 'AC12345', 3499.00, 'Ledig', 'Blå'),
                                                                                             (2,  'JT200000000000002', 'AC12346', 3499.00, 'Udlejet', 'Grå'),
                                                                                             (2,  'JT200000000000003', 'AC12347', 3499.00, 'Klar til transport', 'Rød'),

                                                                                             (3,  'JT300000000000001', 'AD12345', 4599.00, 'Ledig', 'Sort'),
                                                                                             (3,  'JT300000000000002', 'AD12346', 4599.00, 'Udlejet', 'Hvid'),
                                                                                             (3,  'JT300000000000003', 'AD12347', 4599.00, 'Tilbageleveret', 'Grøn'),

                                                                                             (4,  'VW100000000000001', 'BA12345', 2799.00, 'Ledig', 'Hvid'),
                                                                                             (4,  'VW100000000000002', 'BA12346', 2799.00, 'Udlejet', 'Blå'),
                                                                                             (4,  'VW100000000000003', 'BA12347', 2799.00, 'Tilbageleveret', 'Sort'),

                                                                                             (5,  'VW200000000000001', 'BB12345', 3599.00, 'Ledig', 'Sølv'),
                                                                                             (5,  'VW200000000000002', 'BB12346', 3599.00, 'Udlejet', 'Grå'),
                                                                                             (5,  'VW200000000000003', 'BB12347', 3599.00, 'Klar til transport', 'Rød'),

                                                                                             (6,  'VW300000000000001', 'BC12345', 3999.00, 'Ledig', 'Sort'),
                                                                                             (6,  'VW300000000000002', 'BC12346', 3999.00, 'Udlejet', 'Hvid'),
                                                                                             (6,  'VW300000000000003', 'BC12347', 3999.00, 'Tilbageleveret', 'Blå'),

                                                                                             (7,  'BM100000000000001', 'CA12345', 3899.00, 'Ledig', 'Sort'),
                                                                                             (7,  'BM100000000000002', 'CA12346', 3899.00, 'Udlejet', 'Hvid'),
                                                                                             (7,  'BM100000000000003', 'CA12347', 3899.00, 'Tilbageleveret', 'Grå'),

                                                                                             (8,  'BM200000000000001', 'CB12345', 4999.00, 'Ledig', 'Blå'),
                                                                                             (8,  'BM200000000000002', 'CB12346', 4999.00, 'Udlejet', 'Sort'),
                                                                                             (8,  'BM200000000000003', 'CB12347', 4999.00, 'Klar til transport', 'Sølv'),

                                                                                             (9,  'BM300000000000001', 'CC12345', 5299.00, 'Ledig', 'Grøn'),
                                                                                             (9,  'BM300000000000002', 'CC12346', 5299.00, 'Udlejet', 'Hvid'),
                                                                                             (9,  'BM300000000000003', 'CC12347', 5299.00, 'Tilbageleveret', 'Sort'),

                                                                                             (10, 'MB100000000000001', 'DA12345', 3799.00, 'Ledig', 'Hvid'),
                                                                                             (10, 'MB100000000000002', 'DA12346', 3799.00, 'Udlejet', 'Sort'),
                                                                                             (10, 'MB100000000000003', 'DA12347', 3799.00, 'Tilbageleveret', 'Rød'),

                                                                                             (11, 'MB200000000000001', 'DB12345', 5699.00, 'Ledig', 'Grå'),
                                                                                             (11, 'MB200000000000002', 'DB12346', 5699.00, 'Udlejet', 'Blå'),
                                                                                             (11, 'MB200000000000003', 'DB12347', 5699.00, 'Klar til transport', 'Sølv'),

                                                                                             (12, 'MB300000000000001', 'DC12345', 5499.00, 'Ledig', 'Sort'),
                                                                                             (12, 'MB300000000000002', 'DC12346', 5499.00, 'Udlejet', 'Hvid'),
                                                                                             (12, 'MB300000000000003', 'DC12347', 5499.00, 'Tilbageleveret', 'Grå'),

                                                                                             (13, 'AU100000000000001', 'EA12345', 3699.00, 'Ledig', 'Blå'),
                                                                                             (13, 'AU100000000000002', 'EA12346', 3699.00, 'Udlejet', 'Sort'),
                                                                                             (13, 'AU100000000000003', 'EA12347', 3699.00, 'Tilbageleveret', 'Hvid'),

                                                                                             (14, 'AU200000000000001', 'EB12345', 4799.00, 'Ledig', 'Grå'),
                                                                                             (14, 'AU200000000000002', 'EB12346', 4799.00, 'Udlejet', 'Sølv'),
                                                                                             (14, 'AU200000000000003', 'EB12347', 4799.00, 'Klar til transport', 'Sort'),

                                                                                             (15, 'AU300000000000001', 'EC12345', 5199.00, 'Ledig', 'Hvid'),
                                                                                             (15, 'AU300000000000002', 'EC12346', 5199.00, 'Udlejet', 'Blå'),
                                                                                             (15, 'AU300000000000003', 'EC12347', 5199.00, 'Tilbageleveret', 'Grøn'),

                                                                                             (16, 'SK100000000000001', 'FA12345', 2599.00, 'Ledig', 'Hvid'),
                                                                                             (16, 'SK100000000000002', 'FA12346', 2599.00, 'Udlejet', 'Rød'),
                                                                                             (16, 'SK100000000000003', 'FA12347', 2599.00, 'Tilbageleveret', 'Sort'),

                                                                                             (17, 'SK200000000000001', 'FB12345', 3399.00, 'Ledig', 'Grå'),
                                                                                             (17, 'SK200000000000002', 'FB12346', 3399.00, 'Udlejet', 'Blå'),
                                                                                             (17, 'SK200000000000003', 'FB12347', 3399.00, 'Klar til transport', 'Sølv'),

                                                                                             (18, 'SK300000000000001', 'FB22345', 4299.00, 'Ledig', 'Sort'),
                                                                                             (18, 'SK300000000000002', 'FB22346', 4299.00, 'Udlejet', 'Hvid'),
                                                                                             (18, 'SK300000000000003', 'FB22347', 4299.00, 'Tilbageleveret', 'Grøn'),

                                                                                             (19, 'HY100000000000001', 'GA12345', 2499.00, 'Ledig', 'Blå'),
                                                                                             (19, 'HY100000000000002', 'GA12346', 2499.00, 'Udlejet', 'Sort'),
                                                                                             (19, 'HY100000000000003', 'GA12347', 2499.00, 'Tilbageleveret', 'Hvid'),

                                                                                             (20, 'HY200000000000001', 'GB12345', 3199.00, 'Ledig', 'Grå'),
                                                                                             (20, 'HY200000000000002', 'GB12346', 3199.00, 'Udlejet', 'Rød'),
                                                                                             (20, 'HY200000000000003', 'GB12347', 3199.00, 'Klar til transport', 'Sølv'),

                                                                                             (21, 'HY300000000000001', 'GC12345', 4099.00, 'Ledig', 'Sort'),
                                                                                             (21, 'HY300000000000002', 'GC12346', 4099.00, 'Udlejet', 'Blå'),
                                                                                             (21, 'HY300000000000003', 'GC12347', 4099.00, 'Tilbageleveret', 'Hvid'),

                                                                                             (22, 'KI100000000000001', 'HA12345', 2399.00, 'Ledig', 'Hvid'),
                                                                                             (22, 'KI100000000000002', 'HA12346', 2399.00, 'Udlejet', 'Sort'),
                                                                                             (22, 'KI100000000000003', 'HA12347', 2399.00, 'Tilbageleveret', 'Blå'),

                                                                                             (23, 'KI200000000000001', 'HB12345', 3099.00, 'Ledig', 'Grå'),
                                                                                             (23, 'KI200000000000002', 'HB12346', 3099.00, 'Udlejet', 'Sølv'),
                                                                                             (23, 'KI200000000000003', 'HB12347', 3099.00, 'Klar til transport', 'Rød'),

                                                                                             (24, 'KI300000000000001', 'HC12345', 3999.00, 'Ledig', 'Grøn'),
                                                                                             (24, 'KI300000000000002', 'HC12346', 3999.00, 'Udlejet', 'Hvid'),
                                                                                             (24, 'KI300000000000003', 'HC12347', 3999.00, 'Tilbageleveret', 'Sort'),

                                                                                             (25, 'PE100000000000001', 'JA12345', 2299.00, 'Ledig', 'Gul'),
                                                                                             (25, 'PE100000000000002', 'JA12346', 2299.00, 'Udlejet', 'Blå'),
                                                                                             (25, 'PE100000000000003', 'JA12347', 2299.00, 'Tilbageleveret', 'Hvid'),

                                                                                             (26, 'PE200000000000001', 'JB12345', 2999.00, 'Ledig', 'Sort'),
                                                                                             (26, 'PE200000000000002', 'JB12346', 2999.00, 'Udlejet', 'Grå'),
                                                                                             (26, 'PE200000000000003', 'JB12347', 2999.00, 'Klar til transport', 'Rød'),

                                                                                             (27, 'PE300000000000001', 'JC12345', 3899.00, 'Ledig', 'Hvid'),
                                                                                             (27, 'PE300000000000002', 'JC12346', 3899.00, 'Udlejet', 'Blå'),
                                                                                             (27, 'PE300000000000003', 'JC12347', 3899.00, 'Tilbageleveret', 'Sort'),

                                                                                             (28, 'RE100000000000001', 'KA12345', 2199.00, 'Ledig', 'Rød'),
                                                                                             (28, 'RE100000000000002', 'KA12346', 2199.00, 'Udlejet', 'Hvid'),
                                                                                             (28, 'RE100000000000003', 'KA12347', 2199.00, 'Tilbageleveret', 'Sort'),

                                                                                             (29, 'RE200000000000001', 'KB12345', 2899.00, 'Ledig', 'Blå'),
                                                                                             (29, 'RE200000000000002', 'KB12346', 2899.00, 'Udlejet', 'Grå'),
                                                                                             (29, 'RE200000000000003', 'KB12347', 2899.00, 'Klar til transport', 'Sølv'),

                                                                                             (30, 'RE300000000000001', 'KC12345', 3799.00, 'Ledig', 'Sort'),
                                                                                             (30, 'RE300000000000002', 'KC12346', 3799.00, 'Udlejet', 'Grøn'),
                                                                                             (30, 'RE300000000000003', 'KC12347', 3799.00, 'Tilbageleveret', 'Hvid');