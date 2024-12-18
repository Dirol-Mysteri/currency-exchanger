-- CREATE TABLE IF NOT EXISTS Currencies (
-- 	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
-- 	code VARCHAR(10) UNIQUE NOT NULL,
-- 	fullName VARCHAR(100), 
-- 	sign VARCHAR(10)
-- );

-- CREATE TABLE IF NOT EXISTS ExchangeRates (
--     id INTEGER PRIMARY KEY AUTOINCREMENT,
--     baseCurrencyId INTEGER NOT NULL REFERENCES Currencies (id),
--     targetCurrencyId INTEGER NOT NULL REFERENCES Currencies (id),
--     rate DECIMAL(10, 6) NOT NULL,
--     UNIQUE (baseCurrencyId, targetCurrencyId),
--     CHECK (baseCurrencyId != targetCurrencyId)
-- );

-- INSERT INTO Currencies (code, fullName, sign) VALUES('USD','US Dollar', 'U+00024');
-- INSERT INTO Currencies (code, fullName, sign) VALUES('EUR','Euro', 'U+020AC');
-- INSERT INTO Currencies (code, fullName, sign) VALUES('GBP','Pound Sterling', 'U+000A3');
-- INSERT INTO Currencies (code, fullName, sign) VALUES('RUB','Russian Ruble', 'U+020BD');
-- INSERT INTO Currencies (code, fullName, sign) VALUES('CNY','Chinese Yuan', 'U+00A5');


-- INSERT INTO ExchangeRates(baseCurrencyId, targetCurrencyId, rate) VALUES(1, 2, 0.946978);
-- INSERT INTO ExchangeRates(baseCurrencyId, targetCurrencyId, rate) VALUES(2, 1, 1.055991);
-- INSERT INTO ExchangeRates(baseCurrencyId, targetCurrencyId, rate) VALUES(2, 3, 0.833434);
-- INSERT INTO ExchangeRates(baseCurrencyId, targetCurrencyId, rate) VALUES(3, 2, 1.199854);
-- INSERT INTO ExchangeRates(baseCurrencyId, targetCurrencyId, rate) VALUES(3, 4, 141.097462);
-- INSERT INTO ExchangeRates(baseCurrencyId, targetCurrencyId, rate) VALUES(4, 3, 0.007092);