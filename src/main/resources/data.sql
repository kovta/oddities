INSERT INTO products (name, description, price, start_time, end_time) VALUES
  ('Compass', NULL, 1.5, {ts '2020-03-01 12:00:00'}, NULL),
  ('Torch', NULL, 0.5, {ts '2020-03-01 12:00:00'}, NULL),
  ('Talisman', NULL, 5.5, {ts '2020-03-01 12:00:00'}, NULL);

INSERT INTO orders (buyer, time) VALUES
  ('alasdair.hopps@mail.com', {ts '2020-03-02 18:00:00'});

INSERT INTO order_products (order_id, product_id) VALUES
  (1, 1),
  (1, 2);