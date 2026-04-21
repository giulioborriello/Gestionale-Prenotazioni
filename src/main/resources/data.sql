-- =========================
-- RESET DATABASE
-- =========================
-- TRUNCATE resettando le sequenze e rispettando le dipendenze
TRUNCATE TABLE formerjob.ticket RESTART IDENTITY CASCADE;
TRUNCATE TABLE formerjob.payment RESTART IDENTITY CASCADE;
TRUNCATE TABLE formerjob.event RESTART IDENTITY CASCADE;
TRUNCATE TABLE formerjob."user" RESTART IDENTITY CASCADE;

-- =========================
-- INSERT USER
-- =========================
-- NOTE: role non e` annotato con @Enumerated in Model.User, quindi viene salvato come ORDINAL
-- ADMIN = 0, USER = 1
INSERT INTO formerjob."user" (name, surname, email, password, date_of_birth, role) VALUES
                                                                                         ('Mario','Merola','merola.mario01@mail.com','pwd001','1980-01-01', 0),
                                                                                         ('Giacomo','Bianchi','giacomo.bianchi02@mail.com','pwd002','1981-02-15', 1);
                                                                                         --('Martina','Smeraldi','martina.verdi03@mail.com','pwd003','1982-03-20', 1),
                                                                                         --('Silvio','Berlusconi','laura.conti04@mail.com','pwd004','1983-04-25', 1),
                                                                                         --('Luca','De Santis','luca.desantis05@mail.com','pwd005','1984-05-30', 1),
                                                                                         --('Francesca','Galli','francesca.galli06@mail.com','pwd006','1985-06-10', 1),
                                                                                         --('Andrea','Moretti','andrea.moretti07@mail.com','pwd007','1986-07-15', 1),
                                                                                         --('Simone','Ricci','simone.ricci08@mail.com','pwd008','1987-08-20', 1),
                                                                                         --('Giulia','Romano','giulia.romano09@mail.com','pwd009','1988-09-25', 1),
                                                                                         --('Alessandro','Ferrari','alessandro.ferrari10@mail.com','pwd010','1989-10-30', 1);

-- =========================
-- INSERT PAYMENT
-- =========================
--INSERT INTO formerjob.payment (method, total_price, date, user_id) VALUES
--                                                                       ('CREDIT_CARD', 120.50, now() - interval '2 days', 1),
--                                                                       ('PAYPAL', 75.00, now() - interval '5 days', 2),
--                                                                       ('CREDIT_CARD', 200.00, now() - interval '1 day', 3),
--                                                                       ('PAYPAL', 50.00, now() - interval '3 days', 4),
--                                                                       ('CREDIT_CARD', 180.30, now() - interval '4 days', 5),
--                                                                       ('PAYPAL', 95.50, now() - interval '2 days', 6),
--                                                                       ('CREDIT_CARD', 110.10, now() - interval '6 days', 7),
--                                                                       ('PAYPAL', 60.00, now() - interval '1 day', 8),
--                                                                       ('CREDIT_CARD', 150.00, now() - interval '7 days', 9),
--                                                                       ('PAYPAL', 80.25, now() - interval '3 days', 10);

-- =========================
-- INSERT EVENT (10)
-- =========================
INSERT INTO formerjob.event (name, description, location, image_path, date, max_tickets, selled_tickets, type, ticket_price) VALUES
                                                                                                                                ('Coldplay - World Cup Final', 'Halftime Show Finale Mondiali 2026', 'MetLife Stadium, NY', '/media/coldplay_wc.jpg', '2026-07-19 20:00:00', 82500, 75000, 'CONCERTI', 250.00),
                                                                                                                                ('America’s Cup World Series', 'Regate preliminari America’s Cup', 'Cagliari', '/media/americas_cup.jpg', now() + interval '45 days', 5000, 3200, 'SPORT', 0.00),
                                                                                                                                ('Liveplay - Coldplay Experience', 'Tribute band ufficiale Coldplay', 'Teatro Repower, Milano', '/media/liveplay_mi.jpg', '2026-05-20 21:00:00', 1346, 890, 'CONCERTI', 35.00),
                                                                                                                                ('Mostra Van Gogh', 'Esposizione immersiva post-impressionismo', 'Palazzo Reale, Milano', '/media/vangogh.jpg', now() + interval '12 days', 500, 420, 'MOSTRA_E_MUSEI', 18.00),
                                                                                                                                ('Vasco Rossi Tour 2026', 'Live stadi estate 2026', 'Stadio Olimpico, Roma', '/media/vasco_roma.jpg', now() + interval '60 days', 60000, 58000, 'CONCERTI', 75.00),
                                                                                                                                ('Primavera Sound 2026', 'Festival musica internazionale', 'Barcellona', '/media/ps_2026.jpg', now() + interval '40 days', 30000, 24500, 'CONCERTI', 120.00),
                                                                                                                                ('Festival del Cinema 2026', 'Proiezione anteprime internazionali', 'Cannes', '/media/cannes_2026.jpg', now() + interval '25 days', 1000, 950, 'CINEMA', 50.00),
                                                                                                                                ('Marracash - Marrageddon', 'Festival Hip Hop italiano', 'Ippodromo SNAI, Milano', '/media/marrageddon.jpg', now() + interval '70 days', 50000, 41000, 'CONCERTI', 65.00),
                                                                                                                                ('Il Lago dei Cigni', 'Balletto classico prestigioso', 'Teatro alla Scala, Milano', '/media/scala_balletto.jpg', now() + interval '15 days', 2000, 1850, 'TEATRO', 120.00),
                                                                                                                                ('Giro d’Italia 2026 - Arrivo', 'Tappa finale iconica', 'Roma', '/media/giro_italia.jpg', now() + interval '35 days', 10000, 0, 'SPORT', 0.00),
                                                                                                                                ('Sónar Barcelona', 'Musica, Creatività e Tecnologia', 'Barcellona', '/media/sonar.jpg', now() + interval '55 days', 15000, 11200, 'CONCERTI', 90.00);

-- =========================
-- INSERT TICKET
-- =========================
--INSERT INTO formerjob.ticket (name, surname, price, creation_date, user_id, event_id) VALUES
--                                                                                         ('Filippo','Rossi', 50.00, now() - interval '1 day', 1, 1),
--                                                                                         ('Giacomo','Bianchi', 35.00, now() - interval '2 days', 2, 2),
--                                                                                         ('Martina','Verdi', 45.00, now() - interval '1 day', 3, 3),
--                                                                                         ('Laura','Conti', 40.00, now() - interval '3 days', 4, 4);

-- =========================
-- FINE SCRIPT
-- =========================