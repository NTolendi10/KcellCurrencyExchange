CREATE TABLE IF NOT EXISTS currency_rate (
    currency VARCHAR(255) PRIMARY KEY,
    rate DOUBLE
    );

INSERT INTO currency_rate (currency, rate) VALUES ('KZT', 1.0);
INSERT INTO currency_rate (currency, rate) VALUES ('USD', 456.22);
INSERT INTO currency_rate (currency, rate) VALUES ('EUR', 502.39);
