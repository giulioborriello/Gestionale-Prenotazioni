BEGIN;

-- Reset dei dati minimi usati dalla collection Postman.
-- NOTA: le DELETE della collection modificano lo stato del DB, quindi questo seed va rieseguito
-- prima di una nuova run completa.

DELETE FROM formerjob.ticket;
DELETE FROM formerjob.payment;
DELETE FROM formerjob.cart;
DELETE FROM formerjob.event;
DELETE FROM formerjob.place;
DELETE FROM formerjob."user";

-- Utenti usati dalle chiamate /User/* e dai riferimenti di cart/ticket/payment
INSERT INTO formerjob."user"
    (id, name, surname, email, password, tax_code, date_of_birth, creation_date, status)
VALUES
    (1, 'Mario',  'Rossi',   'mario.rossi@example.com',  'pwd123', 'RSSMRA90A01H501U', '1990-01-01', '2026-03-01', true),
    (2, 'Giulia', 'Bianchi', 'giulia.bianchi@example.com', 'pwd123', 'BNCGLI92B41F205X', '1992-02-10', '2026-03-02', true),
    (3, 'Luca',   'Verdi',   'luca.verdi@example.com',   'pwd123', 'VRDLCU88C12L219K', '1988-03-12', '2026-03-03', true);

-- Place: prima senza event_id per evitare il ciclo con Event.place_id
INSERT INTO formerjob.place
    (id, code, status, type, event_id)
VALUES
    (1, 'A1', false, 'STANDARD', NULL),
    (2, 'B1', true,  'STANDARD', NULL);

-- Eventi usati dalle chiamate /Event/* e dal ticket con eventId=1
INSERT INTO formerjob.event
    (id, name, description, location, date, type, place_id)
VALUES
    (1, 'Rock Night', 'Concerto serale rock', 'Milano', '2026-04-20 21:00:00', 'CONCERTI', 1),
    (2, 'Derby City', 'Partita di campionato', 'Roma',   '2026-04-22 18:00:00', 'SPORT',    2);

UPDATE formerjob.place SET event_id = 1 WHERE id = 1;
UPDATE formerjob.place SET event_id = 2 WHERE id = 2;

-- Cart coerenti con le chiamate /api/cart/*
INSERT INTO formerjob.cart
    (id, total_price, user_id)
VALUES
    (1, 120.00, 1),
    (2,  75.00, 2);

-- Payment coerente con le chiamate /api/payment/*
INSERT INTO formerjob.payment
    (id, method, checked, date, cart_id)
VALUES
    (1, 'CREDIT_CARD', true,  '2026-03-30 12:00:00', 1),
    (2, 'PAYPAL',      false, '2026-03-30 12:30:00', 2);

-- Ticket coerenti con le chiamate /Ticket/*
INSERT INTO formerjob.ticket
    (id, name, surname, price, creation_date, cart_id, place_id, user_id, event_id)
VALUES
    (1, 'Mario',  'Rossi',   60.00, '2026-03-31 12:00:00', 1, 1, 1, 1),
    (2, 'Giulia', 'Bianchi', 75.00, '2026-03-31 13:00:00', 2, 2, 2, 2),
    (3, 'Mario',  'Rossi',   45.00, '2026-03-15 09:30:00', 1, 1, 1, 1);

-- Riallineamento sequence PostgreSQL dopo insert con ID espliciti
SELECT setval(pg_get_serial_sequence('formerjob."user"', 'id'), COALESCE((SELECT MAX(id) FROM formerjob."user"), 1), true);
SELECT setval(pg_get_serial_sequence('formerjob.place',   'id'), COALESCE((SELECT MAX(id) FROM formerjob.place), 1), true);
SELECT setval(pg_get_serial_sequence('formerjob.event',   'id'), COALESCE((SELECT MAX(id) FROM formerjob.event), 1), true);
SELECT setval(pg_get_serial_sequence('formerjob.cart',    'id'), COALESCE((SELECT MAX(id) FROM formerjob.cart), 1), true);
SELECT setval(pg_get_serial_sequence('formerjob.payment', 'id'), COALESCE((SELECT MAX(id) FROM formerjob.payment), 1), true);
SELECT setval(pg_get_serial_sequence('formerjob.ticket',  'id'), COALESCE((SELECT MAX(id) FROM formerjob.ticket), 1), true);

COMMIT;

