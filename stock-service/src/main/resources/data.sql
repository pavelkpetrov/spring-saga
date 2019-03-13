insert into good (id, active, name, price, transaction_id)
VALUES (1, true, 'Book', 10, '1');
insert into good (id, active, name, price, transaction_id)
VALUES (2, true, 'phone', 20, '2');
insert into good_stock (id, active, quantity, transaction_id, good_id)
VALUES (1, true, 10, '1', 1);
insert into good_stock (id, active, quantity, transaction_id, good_id)
VALUES (2, true, 20, '2', 2);
