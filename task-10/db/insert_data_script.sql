INSERT INTO task10db.product (maker, model, type) VALUES
('A','l1101','Laptop'),
('C','l1305','Laptop'),
('B','l7101','Laptop'),
('A','l9101','Laptop'),
('A','pc1201','PC'),
('B','pc5301','PC'),
('A','pc8201','PC'),
('E','pc5501','PC'),
('E','pc2401','PC'),
('A','pc8901','PC'),
('A','pr3301','Printer'),
('D','pr4501','Printer'),
('B','pr3501','Printer'),
('A','pr7301','Printer'),
('E','pr6301','Printer');

INSERT INTO task10db.laptop (model, speed, ram, hd, price, screen) VALUES
('l1101', 300, 32, 40, 650, 11),
('l1305', 550, 64, 100, 900, 12),
('l7101', 750, 128, 120, 1300, 15),
('l1101', 600, 64, 100, 1100, 12),
('l9101', 750, 128, 90, 1200, 14),
('l1101', 450, 32, 80, 650, 14);

INSERT INTO task10db.pc (model, speed, ram, hd, cd, price) VALUES
('pc1201', 500, 64, 10, '12x', 500),
('pc5301', 500, 64, 5, '40x', 600),
('pc1201', 750, 128, 20, '24x', 800),
('pc8201', 800, 128, 40, '40x', 900),
('pc5501', 800, 128, 40, '50x', 1000),
('pc1201', 500, 64, 10, '40x', 500),
('pc5301', 600, 64, 20, '24x', 650),
('pc2401', 500, 64, 20, '40x', 750),
('pc2401', 600, 64, 20, '24x', 800),
('pc5501', 500, 128, 40, '12x', 900),
('pc1201', 450, 32, 5, '4x', 350),
('pc8901', 400, 64, 10, '40x', 500),
('pc8201', 450, 32, 5, '12x', 450);

INSERT INTO task10db.printer (model, color, type, price) VALUES
('pr3301', 'n', 'Matrix', 150),
('pr4501', 'y', 'Matrix', 250),
('pr3501', 'n', 'Jet', 350),
('pr7301', 'y', 'Jet', 550),
('pr6301', 'y', 'Laser', 550),
('pr3301', 'n', 'Laser', 150);
