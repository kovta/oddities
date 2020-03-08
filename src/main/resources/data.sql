INSERT INTO products (line_id, name, description, price, start_time, end_time) VALUES
  (RANDOM_UUID(), 'Compass', NULL, 1.5, {ts '2020-03-01 12:00:00'}, NULL),
  (RANDOM_UUID(), 'Torch', NULL, 0.5, {ts '2020-03-01 12:00:00'}, NULL),
  (RANDOM_UUID(), 'Talisman', NULL, 5.5, {ts '2020-03-01 12:00:00'}, NULL);

INSERT INTO orders (buyer, value, time) VALUES
  ('alasdair.hopps@mail.com', 2, {ts '2020-03-02 18:00:00'});

INSERT INTO order_products (order_id, product_id) VALUES
  (1, 1),
  (1, 2);