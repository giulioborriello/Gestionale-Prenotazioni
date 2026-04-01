-- =========================
-- RESET DATABASE
-- =========================
-- TRUNCATE resettando le sequenze e rispettando le dipendenze
TRUNCATE TABLE formerjob.ticket RESTART IDENTITY CASCADE;
TRUNCATE TABLE formerjob.payment RESTART IDENTITY CASCADE;
TRUNCATE TABLE formerjob.cart RESTART IDENTITY CASCADE;
TRUNCATE TABLE formerjob.event RESTART IDENTITY CASCADE;
TRUNCATE TABLE formerjob.place RESTART IDENTITY CASCADE;
TRUNCATE TABLE formerjob."user" RESTART IDENTITY CASCADE;

-- =========================
-- INSERT USER (50 record di esempio)
-- =========================
INSERT INTO formerjob."user" (name, surname, email, password, tax_code, date_of_birth, creation_date, status, role) VALUES
                                                                                                                        ('Filippo','Rossi','filippo.rossi01@mail.com','pwd001','RSS01XXX','1980-01-01', now(), true, 'ADMIN'),
                                                                                                                        ('Giacomo','Bianchi','giacomo.bianchi02@mail.com','pwd002','RSS02XXX','1981-02-15', now(), true, 'USER'),
                                                                                                                        ('Martina','Verdi','martina.verdi03@mail.com','pwd003','RSS03XXX','1982-03-20', now(), true, 'USER'),
                                                                                                                        ('Laura','Conti','laura.conti04@mail.com','pwd004','RSS04XXX','1983-04-25', now(), true, 'USER'),
                                                                                                                        ('Luca','De Santis','luca.desantis05@mail.com','pwd005','RSS05XXX','1984-05-30', now(), true, 'USER'),
                                                                                                                        ('Francesca','Galli','francesca.galli06@mail.com','pwd006','RSS06XXX','1985-06-10', now(), true, 'USER'),
                                                                                                                        ('Andrea','Moretti','andrea.moretti07@mail.com','pwd007','RSS07XXX','1986-07-15', now(), true, 'USER'),
                                                                                                                        ('Simone','Ricci','simone.ricci08@mail.com','pwd008','RSS08XXX','1987-08-20', now(), true, 'USER'),
                                                                                                                        ('Giulia','Romano','giulia.romano09@mail.com','pwd009','RSS09XXX','1988-09-25', now(), true, 'USER'),
                                                                                                                        ('Alessandro','Ferrari','alessandro.ferrari10@mail.com','pwd010','RSS10XXX','1989-10-30', now(), true, 'USER');

-- =========================
-- INSERT CART
-- =========================
INSERT INTO formerjob.cart (total_price, user_id) VALUES
                                                      (120.50, 1),
                                                      (75.00, 2),
                                                      (200.00, 3),
                                                      (50.00, 4),
                                                      (180.30, 5),
                                                      (95.50, 6),
                                                      (110.10, 7),
                                                      (60.00, 8),
                                                      (150.00, 9),
                                                      (80.25, 10);

-- =========================
-- INSERT PAYMENT
-- =========================
INSERT INTO formerjob.payment (method, checked, date, cart_id) VALUES
                                                                   ('CREDIT_CARD', true, now() - interval '2 days', 1),
                                                                   ('PAYPAL', true, now() - interval '5 days', 2),
                                                                   ('CREDIT_CARD', true, now() - interval '1 day', 3),
                                                                   ('PAYPAL', true, now() - interval '3 days', 4),
                                                                   ('CREDIT_CARD', true, now() - interval '4 days', 5),
                                                                   ('PAYPAL', true, now() - interval '2 days', 6),
                                                                   ('CREDIT_CARD', true, now() - interval '6 days', 7),
                                                                   ('PAYPAL', true, now() - interval '1 day', 8),
                                                                   ('CREDIT_CARD', true, now() - interval '7 days', 9),
                                                                   ('PAYPAL', true, now() - interval '3 days', 10);

-- =========================
-- INSERT PLACE
-- =========================
INSERT INTO formerjob.place (code, status, nome, type) VALUES
                                                                     ('A1', true, 'Curva','STANDARD'),
                                                                     ('A2', true, 'Distinti','STANDARD'),
                                                                     ('A3', false, 'Tribuna','STANDARD'),
                                                                     ('B1', true, 'Curva','STANDARD'),
                                                                     ('B2', false, 'Distinti','STANDARD'),
                                                                     ('B3', true, 'Tribuna','STANDARD'),
                                                                     ('C1', true, 'Curva','STANDARD'),
                                                                     ('C2', true, 'Distinti','STANDARD'),
                                                                     ('C3', false, 'Tribuna','STANDARD'),
                                                                     ('D1', true, 'Curva','STANDARD');

-- =========================
-- INSERT EVENT (10)
-- =========================
INSERT INTO formerjob.event (name, description, location, date, type, place_id) VALUES
                                                                          ('Rock Night', 'Concerto Rock', 'Milano', now() + interval '10 days', 'CONCERTI', 1),
                                                                          ('Cinema Italia', 'Proiezione film', 'Roma', now() + interval '5 days', 'CINEMA', 2),
                                                                          ('Jazz Evening', 'Concerto Jazz', 'Firenze', now() + interval '7 days', 'CONCERTI', 3),
                                                                          ('Teatro Classico', 'Rappresentazione teatrale', 'Torino', now() + interval '12 days', 'TEATRO', 4),
                                                                          ('Pop Hits', 'Concerto Pop', 'Milano', now() + interval '15 days', 'CONCERTI', 5),
                                                                          ('Film Premiere', 'Evento cinema', 'Roma', now() + interval '20 days', 'CINEMA', 6),
                                                                          ('Blues Night', 'Concerto Blues', 'Firenze', now() + interval '25 days', 'CONCERTI', 7),
                                                                          ('Opera Gala', 'Opera lirica', 'Torino', now() + interval '30 days', 'TEATRO', 8),
                                                                          ('Electronic Fest', 'Musica elettronica', 'Milano', now() + interval '35 days', 'CONCERTI', 9),
                                                                          ('Documentary Night', 'Proiezione doc', 'Roma', now() + interval '40 days', 'CINEMA', 10);


-- =========================
-- INSERT TICKET
-- =========================
INSERT INTO formerjob.ticket (name, surname, price, creation_date, cart_id, place_id, user_id, event_id) VALUES
                                                                                                             ('Filippo','Rossi', 50.00, now() - interval '1 day', 1, 1, 1, 1),
                                                                                                             ('Giacomo','Bianchi', 65.50, now() - interval '2 days', 2, 2, 2, 2),
                                                                                                             ('Martina','Verdi', 70.00, now() - interval '1 day', 3, 3, 3, 3),
                                                                                                             ('Laura','Conti', 45.25, now() - interval '3 days', 4, 4, 4, 4);

-- =========================
-- AGGIORNA event_id NEI POSTI
-- =========================
UPDATE formerjob.place p
SET event_id = e.id
    FROM formerjob.event e
WHERE
-- associa ad esempio tramite codice
    (p.code = 'A1' AND e.name = 'Rock Night')
   OR (p.code = 'A2' AND e.name = 'Cinema Italia')
   OR (p.code = 'A3' AND e.name = 'Jazz Evening')
   OR (p.code = 'B1' AND e.name = 'Teatro Classico')
   OR (p.code = 'B2' AND e.name = 'Pop Hits')
   OR (p.code = 'B3' AND e.name = 'Film Premiere')
   OR (p.code = 'C1' AND e.name = 'Blues Night')
   OR (p.code = 'C2' AND e.name = 'Opera Gala')
   OR (p.code = 'C3' AND e.name = 'Electronic Fest')
   OR (p.code = 'D1' AND e.name = 'Documentary Night');

-- =========================
-- FINE SCRIPT
-- =========================