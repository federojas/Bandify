TRUNCATE TABLE auditions RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE auditionroles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE locations RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO locations(id, location)
VALUES (1, 'location');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (1, 'band@mail.com', '12345678', 'name', null, true, false, 'description');

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (1, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO roles(id, role)
VALUES (1, 'role');

INSERT INTO auditionroles(auditionid, roleid)
VALUES (1, 1);

INSERT INTO roles(id, role)
VALUES (2, 'role2');

INSERT INTO auditionroles(auditionid, roleid)
VALUES (1, 2);

INSERT INTO roles(id, role)
VALUES (3, 'role3');

INSERT INTO auditionroles(auditionid, roleid)
VALUES (1, 3);

INSERT INTO roles(id, role)
VALUES (4, 'role4');

INSERT INTO roles(id, role)
VALUES (5, 'role5');