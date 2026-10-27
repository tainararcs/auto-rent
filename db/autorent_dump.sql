-- SCRIPT DE SEED COMPLETO AUTORENT -- 

-- Limpa os dados existentes e reinicia os IDs em cascata
TRUNCATE TABLE rental, vehicle, accessory, agency, users RESTART IDENTITY CASCADE;

-- 1. Inserção de Agências
INSERT INTO agency (name, address, city, phonenumber) VALUES
('AutoRent Central', 'Av. Rio Branco, 1000 - Centro', 'Juiz de Fora', '(32) 3215-0000'),
('AutoRent Aeroporto', 'Rodovia BR-040, Km 800 - Zona Aeroporto', 'Juiz de Fora', '(32) 3235-1111');

-- 2. Inserção de Usuários
INSERT INTO users (name, cpf, email, passwordhash, type) VALUES
('Admin', '111.222.333-44', 'admin@autorent.com', '1508416', 'EMPLOYEE'),
('Ana Silva', '222.333.444-55', 'ana@gmail.com', '1539200', 'CLIENT'),
('Bruno Souza', '333.444.555-66', 'bruno@gmail.com', '1569984', 'CLIENT'),
('Carla Costa', '444.555.666-77', 'carla@gmail.com', '1600768', 'CLIENT');

-- 3. Inserção de Acessórios
INSERT INTO accessory (description, dailyrate) VALUES
('Cadeira de Bebê', 35.00),
('GPS Satelital Garmin', 20.00),
('Suporte para Bike', 25.00);

-- 4. Inserção de Veículos
INSERT INTO vehicle (brand, model, year, plate, dailyrate, category, status, agency_id) VALUES
('Chevrolet', 'Onix Plus', 2023, 'ABC-1234', 120.00, 'CAR', 'AVAILABLE', 1),
('Fiat', 'Pulse', 2024, 'XYZ-5678', 180.00, 'UTILITY', 'AVAILABLE', 1),
('Volkswagen', 'Gol', 2022, 'DEF-9012', 95.00, 'CAR', 'AVAILABLE', 2),
('Toyota', 'Corolla', 2023, 'GHI-3456', 250.00, 'CAR', 'AVAILABLE', 2);

-- 5. Inserção de Locações Prontas (Utilizando 'COMPLETED' no lugar de FINISHED)
INSERT INTO rental (pickupdate, returndate, duedate, totalvalue, status, pickup_agency_id, return_agency_id, client_id, vehicle_id) VALUES
('2026-10-01', '2026-10-05', '2026-10-05', 600.00, 'COMPLETED', 1, 1, 2, 1),
('2026-10-10', '', '2026-10-15', 900.00, 'ACTIVE', 1, 2, 3, 2);
