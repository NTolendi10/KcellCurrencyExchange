CREATE TABLE IF NOT EXISTS exchange_operation (
                                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                  from_currency VARCHAR(255),
    to_currency VARCHAR(255),
    amount DOUBLE,
    result DOUBLE,
    operation_time TIMESTAMP
    );
