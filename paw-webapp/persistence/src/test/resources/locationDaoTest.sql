TRUNCATE TABLE locations RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO locations(id, location)
VALUES (1, 'loc');

INSERT INTO locations(id, location)
VALUES (2, 'loc2');

INSERT INTO locations(id, location)
VALUES (3, 'loc3');