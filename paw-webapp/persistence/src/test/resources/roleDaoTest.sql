TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO roles(id, role)
VALUES (1, 'role');

INSERT INTO roles(id, role)
VALUES (2, 'role2');

INSERT INTO roles(id, role)
VALUES (3, 'role3');

INSERT INTO roles(id, role)
VALUES (4, 'role4');

INSERT INTO roles(id, role)
VALUES (5, 'role5');
