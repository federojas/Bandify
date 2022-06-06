TRUNCATE TABLE auditions RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE genres RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE auditiongenres RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE auditionroles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE locations RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE applications RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO locations(id, location)
VALUES (1, 'location');

INSERT INTO genres(id, genre)
VALUES (1, 'genre');

INSERT INTO roles(id, role)
VALUES (1, 'role');


INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (1, 'band@mail.com', '12345678', 'name', null, true, false, 'description');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (13, 'band2@mail.com', '12345678', 'name', null, true, false, 'description');


INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (2, 'artist@mail.com', '12345678', 'name', 'surname', false, false, 'description');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (3, 'artist2@mail.com', '12345678', 'name', 'surname', false, false, 'description');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (4, 'artist3@mail.com', '12345678', 'name', 'surname', false, false, 'description');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (5, 'artist4@mail.com', '12345678', 'name', 'surname', false, false, 'description');


INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (1, 1, 'title', 'description', '2022-07-05 14:23:30', 1);


INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (1, 1, 2, '2022-07-05 14:23:30', 'PENDING', 'message');

INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (2, 1, 3, '2022-07-05 14:23:30', 'PENDING', 'message');

INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (3, 1, 4, '2022-07-05 14:23:30', 'ACCEPTED', 'message');

INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (4, 1, 5, '2022-07-05 14:23:30', 'REJECTED', 'message');

INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (5, 1, 5, '2022-07-05 14:23:30', 'REJECTED', 'message');
