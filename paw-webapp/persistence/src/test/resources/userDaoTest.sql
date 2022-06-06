TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE locations RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE genres RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE usergenres RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE userroles RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO locations(id, location)
VALUES (1, 'loc');
--ARTIST
INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, locationid, available)
VALUES (1, 'artist@mail.com', '12345678', 'name', 'surname', false, false, 'description', null, false);

--ARTIST EDIT
INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, locationid, available)
VALUES (2, 'artist2@mail.com', '12345678', 'name', 'surname', false, false, 'description',1,false);

--VERIFIED USER FOR FILTER TESTING
INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, locationid, available)
VALUES (3, 'artist3@mail.com', '12345678', 'name', 'surname', false, true, 'description', 1, false);

INSERT INTO roles VALUES(1,'GUITARIST');
INSERT INTO genres VALUES(1,'ROCK');
INSERT INTO usergenres VALUES(3,1);
INSERT INTO userroles VALUES(3,1);