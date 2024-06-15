DROP TABLE IF EXISTS transport_type;

-- Create table
CREATE TABLE transport_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(16) NOT NULL UNIQUE,
    CONSTRAINT transport_type_chk_name CHECK (name <> '')
);
-- vehicle table
-- Drop table if it exists
DROP TABLE IF EXISTS vehicle;

-- Create table
CREATE TABLE vehicle (
    id SERIAL PRIMARY KEY,
    type INT NOT NULL,
    license_plate VARCHAR(10) NOT NULL UNIQUE,
    FOREIGN KEY (type) REFERENCES transport_type(id),
    CONSTRAINT vehicle_chk_lp CHECK (license_plate <> '')
);
-- validator table
-- Drop table if it exists
DROP TABLE IF EXISTS validator;

-- Create table
CREATE TABLE validator (
    id SERIAL PRIMARY KEY,
    vehicle INT NOT NULL,
    FOREIGN KEY (vehicle) REFERENCES vehicle(id)
);
-- fare_type table
-- Drop table if it exists
DROP TABLE IF EXISTS fare_type;

-- Create table
CREATE TABLE fare_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(16) NOT NULL UNIQUE,
    daily_limit NUMERIC(8,2) NOT NULL,
    weekly_limit NUMERIC(8,2) NOT NULL,
    CONSTRAINT fare_type_chk_name CHECK (name <> ''),
    CONSTRAINT fare_type_chk_dl CHECK (daily_limit > 0),
    CONSTRAINT fare_type_chk_wl CHECK (weekly_limit > 0)
);
-- fare table
-- Drop table if it exists
DROP TABLE IF EXISTS fare;

-- Create table
CREATE TABLE fare (
    id SERIAL PRIMARY KEY,
    type INT NOT NULL,
    transport_type INT NOT NULL,
    fare NUMERIC(8,2) NOT NULL,
    FOREIGN KEY (type) REFERENCES fare_type(id),
    FOREIGN KEY (transport_type) REFERENCES transport_type(id),
    CONSTRAINT fare_chk_unique UNIQUE (type, transport_type),
    CONSTRAINT fare_chk_fare CHECK (fare > 0)
);
-- machine table
-- Drop table if it exists
DROP TABLE IF EXISTS machine;

-- Create table
CREATE TABLE machine (
    id SERIAL PRIMARY KEY,
    address VARCHAR(100) NOT NULL,
    CONSTRAINT machine_chk_address CHECK (address <> '')
);
-- card table
-- Drop table if it exists
DROP TABLE IF EXISTS card;

-- Create table
CREATE TABLE card (
    id SERIAL PRIMARY KEY,
    type INT NOT NULL,
    balance NUMERIC(8,2) NOT NULL DEFAULT 0,
    FOREIGN KEY (type) REFERENCES fare_type(id),
    CONSTRAINT card_chk_balance CHECK (balance >= 0)
);
-- top_up table
-- Drop table if it exists
DROP TABLE IF EXISTS top_up;

-- Create table
CREATE TABLE top_up (
    id SERIAL PRIMARY KEY,
    machine INT NOT NULL,
    card INT NOT NULL,
    date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    amount NUMERIC(8,2) NOT NULL,
    FOREIGN KEY (machine) REFERENCES machine(id),
    FOREIGN KEY (card) REFERENCES card(id),
    CONSTRAINT top_up_chk_amount CHECK (amount > 0)
);
-- card_tap table
-- Drop table if it exists
DROP TABLE IF EXISTS card_tap;

-- Create a ENUM note_type
CREATE TYPE note_type AS ENUM(
    'DAILY_LIMIT',
    'WEEKLY_LIMIT',
    'ALREADY_PAID',
    'INSUFFICIENT_BALANCE'
);

-- Create table
CREATE TABLE card_tap (
    id SERIAL PRIMARY KEY,
    card INT NOT NULL,
    validator INT NOT NULL,
    date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fare NUMERIC(8,2),
    withdraw_amount NUMERIC(8,2),
    is_successful BOOLEAN,
    note note_type,
    FOREIGN KEY (card) REFERENCES card(id),
    FOREIGN KEY (validator) REFERENCES validator(id)
);
-- functions
-- calculate daily spending
-- Create function
CREATE OR REPLACE FUNCTION fn_calculate_daily_spending(
    card_id INT,
    travel_date DATE
) RETURNS NUMERIC(8,2) AS $$
DECLARE
    total_withdraw_amount NUMERIC(8,2) DEFAULT 0;
BEGIN
    SELECT COALESCE(SUM(withdraw_amount), 0) INTO total_withdraw_amount
    FROM card_tap
    WHERE card = card_id AND DATE(date_time) = travel_date;

    RETURN total_withdraw_amount;
END;
$$ LANGUAGE plpgsql;
-- calculate weekly spending
-- Create function
CREATE OR REPLACE FUNCTION fn_calculate_weekly_spending(
    card_id INT,
    travel_date DATE
) RETURNS NUMERIC(8,2) AS $$
DECLARE
    total_withdraw_amount NUMERIC(8,2) DEFAULT 0;
    iter_date DATE;
BEGIN
    /* Selecting Monday */
    iter_date := travel_date - EXTRACT(ISODOW FROM travel_date)::INT + 1;

    WHILE iter_date <= travel_date LOOP
        total_withdraw_amount := total_withdraw_amount + fn_calculate_daily_spending(card_id, iter_date);
        iter_date := iter_date + 1;
    END LOOP;

    RETURN COALESCE(total_withdraw_amount, 0);
END;
$$ LANGUAGE plpgsql;
-- insert card
CREATE OR REPLACE FUNCTION fn_card_insert(
    fn_type INT,
    fn_balance NUMERIC(8,2)
)
RETURNS INT AS $$
DECLARE
    inserted_id INT;
BEGIN
    INSERT INTO card (type, balance)
    VALUES (fn_type, fn_balance)  
    RETURNING id INTO inserted_id;
    
    RETURN inserted_id;
END;
$$ LANGUAGE plpgsql;
-- insert card tap
CREATE OR REPLACE FUNCTION fn_card_tap_insert(
    card_id INT,
    validator_id INT
)
RETURNS INT AS $$
DECLARE
    inserted_id INT;
BEGIN
    INSERT INTO card_tap (card, validator)
    VALUES (card_id, validator_id)
    RETURNING id INTO inserted_id;
    
    RETURN inserted_id;
END;
$$ LANGUAGE plpgsql;
-- triggers
-- top_up table
-- Drop trigger if it exists
DROP TRIGGER IF EXISTS tg_update_card_balance ON top_up;

-- Create the trigger function
CREATE OR REPLACE FUNCTION update_card_balance()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE card
    SET balance = balance + NEW.amount
    WHERE id = NEW.card;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create the trigger
CREATE TRIGGER tg_update_card_balance
AFTER INSERT ON top_up
FOR EACH ROW
EXECUTE FUNCTION update_card_balance();
-- card_tap table
-- Drop trigger if it exists
DROP TRIGGER IF EXISTS tg_card_tap_insert ON card_tap;

-- Create function for trigger
CREATE OR REPLACE FUNCTION tg_card_tap_insert() RETURNS TRIGGER AS $$
DECLARE
    tg_fare NUMERIC(8,2);
    tg_withdraw_amount NUMERIC(8,2);
    tg_daily_limit NUMERIC(8,2);
    tg_weekly_limit NUMERIC(8,2);
    tg_daily_spent NUMERIC(8,2);
    tg_weekly_spent NUMERIC(8,2);
    tg_daily_withdraw_amount NUMERIC(8,2);
    tg_weekly_withdraw_amount NUMERIC(8,2);
    tg_note TEXT;
    tg_card_balance NUMERIC(8,2);
    tg_last_tap TIMESTAMP;
BEGIN
    -- calculating fare
    SELECT fare.fare, fare_type.daily_limit, fare_type.weekly_limit INTO tg_fare, tg_daily_limit, tg_weekly_limit
    FROM fare
    JOIN card ON fare.type = card.type
    JOIN vehicle ON fare.transport_type = vehicle.type
    JOIN validator ON vehicle.id = validator.vehicle
    JOIN fare_type ON fare.type = fare_type.id
    WHERE card.id = NEW.card AND validator.id = NEW.validator;

    -- calculating daily and weekly spent amounts
    SELECT fn_calculate_daily_spending(NEW.card, DATE(NEW.date_time)) INTO tg_daily_spent;
    SELECT fn_calculate_weekly_spending(NEW.card, DATE(NEW.date_time)) INTO tg_weekly_spent;

    -- calculating withdraw amount
    tg_daily_withdraw_amount := tg_fare;
    tg_weekly_withdraw_amount := tg_fare;

    IF (tg_daily_spent >= tg_daily_limit) THEN
        tg_daily_withdraw_amount := 0;
    ELSIF (tg_daily_limit - tg_daily_spent < tg_fare) THEN
        tg_daily_withdraw_amount := tg_daily_limit - tg_daily_spent;
    END IF;

    IF (tg_weekly_spent >= tg_weekly_limit) THEN
        tg_weekly_withdraw_amount := 0;
    ELSIF (tg_weekly_limit - tg_weekly_spent < tg_fare) THEN
        tg_weekly_withdraw_amount := tg_weekly_limit - tg_weekly_spent;
    END IF;

    IF (tg_fare = tg_daily_withdraw_amount AND tg_fare = tg_weekly_withdraw_amount) THEN
        tg_withdraw_amount := tg_fare;
    ELSIF (tg_weekly_withdraw_amount <= tg_daily_withdraw_amount) THEN
        tg_withdraw_amount := tg_weekly_withdraw_amount;
        tg_note := 'WEEKLY_LIMIT';
    ELSE
        tg_withdraw_amount := tg_daily_withdraw_amount;
        tg_note := 'DAILY_LIMIT';
    END IF;

    -- Getting the last date with the same card & vehicle
    SELECT date_time INTO tg_last_tap
    FROM card_tap
    JOIN validator ON card_tap.validator = validator.id
    WHERE card = NEW.card AND is_successful = TRUE
    AND validator.vehicle = (SELECT validator.vehicle FROM validator WHERE validator.id = NEW.validator)
    ORDER BY date_time DESC
    LIMIT 1;

    -- Getting the card balance
    SELECT card.balance INTO tg_card_balance
    FROM card
    WHERE card.id = NEW.card;

    -- Checking if it was already paid within an hour
    IF (tg_last_tap IS NOT NULL AND EXTRACT(EPOCH FROM (NEW.date_time - tg_last_tap)) <= 3600) THEN
        NEW.fare := tg_fare;
        NEW.withdraw_amount := 0;
        NEW.is_successful := FALSE;
        NEW.note := 'ALREADY_PAID';

    -- Checking the card balance
    ELSIF (tg_card_balance < tg_withdraw_amount) THEN
        NEW.fare := tg_fare;
        NEW.withdraw_amount := 0;
        NEW.is_successful := FALSE;
        NEW.note := 'INSUFFICIENT_BALANCE';

    ELSE
        NEW.fare := tg_fare;
        NEW.withdraw_amount := tg_withdraw_amount;
        NEW.is_successful := TRUE;
        NEW.note := tg_note;
        -- Update card balance
        UPDATE card
        SET balance = balance - tg_withdraw_amount
        WHERE id = NEW.card;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create trigger
CREATE TRIGGER tg_card_tap_insert
BEFORE INSERT ON card_tap
FOR EACH ROW
EXECUTE FUNCTION tg_card_tap_insert();
-- users
-- admin_app
CREATE USER admin_app WITH ENCRYPTED PASSWORD 'A5IWo8rb0AzE';
GRANT SELECT ON ALL TABLES IN SCHEMA public TO admin_app;

-- machine_app
CREATE USER machine_app WITH ENCRYPTED PASSWORD'OBR0ffQJ22as';
GRANT INSERT ON card TO machine_app;
GRANT INSERT ON top_up TO machine_app;
GRANT USAGE, SELECT ON SEQUENCE card_id_seq TO machine_app;
GRANT USAGE, SELECT ON SEQUENCE top_up_id_seq TO machine_app;
GRANT UPDATE ON card TO machine_app;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO machine_app;


-- validator_app
CREATE USER validator_app WITH ENCRYPTED PASSWORD 'UcLFkmEKolek';
GRANT INSERT ON card_tap TO validator_app;
GRANT UPDATE ON card TO validator_app;
GRANT USAGE, SELECT ON SEQUENCE card_tap_id_seq TO validator_app;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO validator_app;
