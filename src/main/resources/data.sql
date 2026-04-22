-- =========================
-- RESET DATABASE
-- =========================
TRUNCATE TABLE formerjob.ticket RESTART IDENTITY CASCADE;
TRUNCATE TABLE formerjob.payment RESTART IDENTITY CASCADE;
TRUNCATE TABLE formerjob.event RESTART IDENTITY CASCADE;
TRUNCATE TABLE formerjob."user" RESTART IDENTITY CASCADE;

-- =========================
-- INSERT USER
-- =========================
INSERT INTO formerjob."user" (name, surname, email, password, date_of_birth, role) VALUES
('Mario','Merola','admin@test.com','pwd001','1980-01-01', 0),
('Giacomo','Bianchi','user@test.com','pwd002','1981-02-15', 1);

-- =========================
-- INSERT EVENT
-- =========================
INSERT INTO formerjob.event (name, description, location, image_path, date, max_tickets, selled_tickets, type, ticket_price) VALUES
('Coldplay - World Cup Final', 'Halftime Show Finale Mondiali 2026', 'MetLife Stadium, NY', '/media/coldplay_wc.jpg', '2026-07-19 20:00:00', 82500, 75000, 'CONCERTI', 250.00),
('America Cup World Series', 'Regate preliminari Americas Cup', 'Cagliari', '/media/americas_cup.jpg', '2026-06-15 20:00:00', 5000, 3200, 'SPORT', 0.00),
('Liveplay - Coldplay Experience', 'Tribute band ufficiale Coldplay', 'Teatro Repower, Milano', '/media/liveplay_mi.jpg', '2026-05-20 21:00:00', 1346, 890, 'CONCERTI', 35.00),
('Mostra Van Gogh', 'Esposizione immersiva post-impressionismo', 'Palazzo Reale, Milano', '/media/vangogh.jpg', '2026-05-03 20:00:00', 500, 420, 'MOSTRA_E_MUSEI', 18.00),
('Vasco Rossi Tour 2026', 'Live stadi estate 2026', 'Stadio Olimpico, Roma', '/media/vasco_roma.jpg', '2026-07-20 21:00:00', 60000, 58000, 'CONCERTI', 75.00),
('Primavera Sound 2026', 'Festival musica internazionale', 'Barcellona', '/media/ps_2026.jpg', '2026-06-10 20:00:00', 30000, 24500, 'CONCERTI', 120.00),
('Festival del Cinema 2026', 'Proiezione anteprime internazionali', 'Cannes', '/media/cannes_2026.jpg', '2026-05-25 20:00:00', 1000, 950, 'CINEMA', 50.00),
('Marracash - Marrageddon', 'Festival Hip Hop italiano', 'Ippodromo SNAI, Milano', '/media/marrageddon.jpg', '2026-07-30 21:00:00', 50000, 41000, 'CONCERTI', 65.00),
('Il Lago dei Cigni', 'Balletto classico prestigioso', 'Teatro alla Scala, Milano', '/media/scala_balletto.jpg', '2026-06-06 20:00:00', 2000, 1850, 'TEATRO', 120.00),
('Giro d Italia 2026 Arrivo', 'Tappa finale iconica', 'Roma', '/media/giro_italia.jpg', '2026-06-26 16:00:00', 10000, 0, 'SPORT', 0.00),
('Sonar Barcelona', 'Musica, Creativita e Tecnologia', 'Barcellona', '/media/sonar.jpg', '2026-07-15 22:00:00', 15000, 11200, 'CONCERTI', 90.00);

-- =========================
-- INSERT TICKET
-- =========================
INSERT INTO formerjob.ticket (name, surname, price, creation_date, user_id, event_id) VALUES
('Filippo','Rossi', 50.00, '2026-04-19 14:13:00', 1, 1),
('Giacomo','Bianchi', 35.00, '2026-04-18 14:13:00', 2, 2),
('Martina','Verdi', 45.00, '2026-04-19 14:13:00', 1, 3),
('Laura','Conti', 40.00, '2026-04-17 14:13:00', 2, 4);

-- =========================
-- INSERT PAYMENT
-- =========================
INSERT INTO formerjob.payment (method, total_price, date, user_id, event_id) VALUES
('CREDIT_CARD', 120.50, '2026-04-19 14:13:00', 1,1),
('PAYPAL', 75.00, '2026-04-16 14:13:00', 2,2),
('CREDIT_CARD', 200.00, '2026-04-20 14:13:00', 1,3),
('PAYPAL', 50.00, '2026-04-18 14:13:00', 2,4),
('CREDIT_CARD', 180.30, '2026-04-17 14:13:00', 1,5);