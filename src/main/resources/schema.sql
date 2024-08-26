CREATE TABLE PRICES (
    id SERIAL PRIMARY KEY,
    brand_id INT,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    price_list INT,
    product_id INT,
    priority INT,
    price DECIMAL(10, 2),
    curr VARCHAR(3)
);