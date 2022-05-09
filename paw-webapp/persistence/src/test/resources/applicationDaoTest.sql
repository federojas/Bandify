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
VALUES (2, 'artist@mail.com', '12345678', 'name', 'surname', false, false, 'description');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (3, 'artist2@mail.com', '12345678', 'name', 'surname', false, false, 'description');

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (1, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (2, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (3, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (1,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (1,1);

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (1, 2, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (1, 3, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (2, 2, '2022-07-05 14:23:30', 'ACCEPTED');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (2, 3, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (3, 2, '2022-07-05 14:23:30', 'REJECTED');


