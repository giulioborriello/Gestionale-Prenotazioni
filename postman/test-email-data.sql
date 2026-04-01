-- =====================================================
-- SCRIPT DI TEST - Dati per testare EmailController
-- =====================================================
-- Basato sullo stesso pattern di postman/seed-postman.sql
-- con tabelle in minuscolo e "user" quoted.

BEGIN;

-- Pulizia dati test precedenti (solo dataset MAILTEST)
UPDATE formerjob.place
SET event_id = NULL
WHERE code = 'MAILTEST-A1';

DELETE FROM formerjob.ticket
WHERE place_id IN (SELECT id FROM formerjob.place WHERE code = 'MAILTEST-A1');

DELETE FROM formerjob.event
WHERE name = 'MAILTEST - Concerto EventIO 2026';

DELETE FROM formerjob.place
WHERE code = 'MAILTEST-A1';

DELETE FROM formerjob."user"
WHERE email = 'mailtest.mario.rossi@example.com';

-- 1) User
INSERT INTO formerjob."user"
    (name, surname, email, password, tax_code, date_of_birth, creation_date, status)
VALUES
    ('Mario', 'Rossi', 'mailtest.mario.rossi@example.com', 'pwd123', 'RSSMRA90A01H501U', '1990-01-01', NOW(), true);

-- 2) Place (inizialmente senza event_id per evitare il ciclo con Event.place_id)
INSERT INTO formerjob.place
    (nome, code, status, type, event_id)
VALUES
    ('Poltrona A1', 'MAILTEST-A1', false, 'STANDARD', NULL);

-- 3) Event (obbliga place_id NOT NULL)
INSERT INTO formerjob.event
    (name, description, location, date, type, place_id)
VALUES
    (
        'MAILTEST - Concerto EventIO 2026',
        'Concerto di prova per invio email',
        'Napoli',
        NOW() + INTERVAL '30 days',
        'CONCERTI',
        (SELECT id FROM formerjob.place WHERE code = 'MAILTEST-A1' LIMIT 1)
    );

-- 4) Completo il link inverso su place.event_id
UPDATE formerjob.place
SET event_id = (SELECT id FROM formerjob.event WHERE name = 'MAILTEST - Concerto EventIO 2026' LIMIT 1)
WHERE code = 'MAILTEST-A1';

-- 5) Ticket (richiesto per test end-to-end)
INSERT INTO formerjob.ticket
    (name, surname, price, creation_date, cart_id, place_id, user_id, event_id)
VALUES
    (
        'Mario',
        'Rossi',
        29.99,
        NOW(),
        NULL,
        (SELECT id FROM formerjob.place WHERE code = 'MAILTEST-A1' LIMIT 1),
        (SELECT id FROM formerjob."user" WHERE email = 'mailtest.mario.rossi@example.com' LIMIT 1),
        (SELECT id FROM formerjob.event WHERE name = 'MAILTEST - Concerto EventIO 2026' LIMIT 1)
    );

-- Verifica dati test
SELECT id, email FROM formerjob."user" WHERE email = 'mailtest.mario.rossi@example.com';
SELECT id, code, event_id FROM formerjob.place WHERE code = 'MAILTEST-A1';
SELECT id, name, place_id FROM formerjob.event WHERE name = 'MAILTEST - Concerto EventIO 2026';
SELECT id, user_id, place_id, event_id FROM formerjob.ticket WHERE place_id = (SELECT id FROM formerjob.place WHERE code = 'MAILTEST-A1' LIMIT 1);

COMMIT;


