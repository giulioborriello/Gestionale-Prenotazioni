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
                                                                                         ('Filippo','Rossi','filippo.rossi01@mail.com','pwd001','1980-01-01', 0),
                                                                                         ('Giacomo','Bianchi','giacomo.bianchi02@mail.com','pwd002','1981-02-15', 1),
                                                                                         ('Martina','Verdi','martina.verdi03@mail.com','pwd003','1982-03-20', 1),
                                                                                         ('Laura','Conti','laura.conti04@mail.com','pwd004','1983-04-25', 1),
                                                                                         ('Luca','De Santis','luca.desantis05@mail.com','pwd005','1984-05-30', 1),
                                                                                         ('Francesca','Galli','francesca.galli06@mail.com','pwd006','1985-06-10', 1),
                                                                                         ('Andrea','Moretti','andrea.moretti07@mail.com','pwd007','1986-07-15', 1),
                                                                                         ('Simone','Ricci','simone.ricci08@mail.com','pwd008','1987-08-20', 1),
                                                                                         ('Giulia','Romano','giulia.romano09@mail.com','pwd009','1988-09-25', 1),
                                                                                         ('Alessandro','Ferrari','alessandro.ferrari10@mail.com','pwd010','1989-10-30', 1);

-- =========================
-- INSERT PAYMENT
-- =========================
INSERT INTO formerjob.payment (method, total_price, date, user_id) VALUES
                                                                       ('CREDIT_CARD', 120.50, now() - interval '2 days', 1),
                                                                       ('PAYPAL', 75.00, now() - interval '5 days', 2),
                                                                       ('CREDIT_CARD', 200.00, now() - interval '1 day', 3),
                                                                       ('PAYPAL', 50.00, now() - interval '3 days', 4),
                                                                       ('CREDIT_CARD', 180.30, now() - interval '4 days', 5),
                                                                       ('PAYPAL', 95.50, now() - interval '2 days', 6),
                                                                       ('CREDIT_CARD', 110.10, now() - interval '6 days', 7),
                                                                       ('PAYPAL', 60.00, now() - interval '1 day', 8),
                                                                       ('CREDIT_CARD', 150.00, now() - interval '7 days', 9),
                                                                       ('PAYPAL', 80.25, now() - interval '3 days', 10);

-- =========================
-- INSERT EVENT (10)
-- =========================
INSERT INTO formerjob.event (name, description, location, date, max_tickets, selled_tickets, type, ticket_price) VALUES
                                                                                                                      ('Rock Night', 'Concerto Rock', 'Milano', now() + interval '10 days', 250, 120, 'CONCERTI', 50.00),
                                                                                                                      ('Cinema Italia', 'Proiezione film', 'Roma', now() + interval '5 days', 250, 90, 'CINEMA', 35.00),
                                                                                                                      ('Jazz Evening', 'Concerto Jazz', 'Firenze', now() + interval '7 days', 250, 70, 'CONCERTI', 45.00),
                                                                                                                      ('Teatro Classico', 'Rappresentazione teatrale', 'Torino', now() + interval '12 days', 250, 110, 'TEATRO', 40.00),
                                                                                                                      ('Pop Hits', 'Concerto Pop', 'Milano', now() + interval '15 days', 250, 150, 'CONCERTI', 55.00),
                                                                                                                      ('Film Premiere', 'Evento cinema', 'Roma', now() + interval '20 days', 250, 130, 'CINEMA', 38.00),
                                                                                                                      ('Blues Night', 'Concerto Blues', 'Firenze', now() + interval '25 days', 250, 80, 'CONCERTI', 42.00),
                                                                                                                      ('Opera Gala', 'Opera lirica', 'Torino', now() + interval '30 days', 250, 65, 'TEATRO', 60.00),
                                                                                                                      ('Electronic Fest', 'Musica elettronica', 'Milano', now() + interval '35 days', 250, 140, 'CONCERTI', 58.00),
                                                                                                                      ('Documentary Night', 'Proiezione doc', 'Roma', now() + interval '40 days', 250, 75, 'CINEMA', 30.00);


-- =========================
-- INSERT TICKET
-- =========================
INSERT INTO formerjob.ticket (name, surname, price, creation_date, user_id, event_id) VALUES
                                                                                         ('Filippo','Rossi', 50.00, now() - interval '1 day', 1, 1),
                                                                                         ('Giacomo','Bianchi', 35.00, now() - interval '2 days', 2, 2),
                                                                                         ('Martina','Verdi', 45.00, now() - interval '1 day', 3, 3),
                                                                                         ('Laura','Conti', 40.00, now() - interval '3 days', 4, 4);

-- =========================
-- FINE SCRIPT
-- =========================