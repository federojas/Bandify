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


INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, available)
VALUES (1, 'band@mail.com', '12345678', 'name', null, true, false, 'description', false);

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, available)
VALUES (13, 'band2@mail.com', '12345678', 'name', null, true, false, 'description', false);


INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, available)
VALUES (2, 'artist@mail.com', '12345678', 'name', 'surname', false, false, 'description', false);

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, available)
VALUES (3, 'artist2@mail.com', '12345678', 'name', 'surname', false, false, 'description', false);

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, available)
VALUES (4, 'artist3@mail.com', '12345678', 'name', 'surname', false, false, 'description', false);

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, available)
VALUES (5, 'artist4@mail.com', '12345678', 'name', 'surname', false, false, 'description', false);

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, available)
VALUES (6, 'artist5@mail.com', '12345678', 'name', 'surname', false, false, 'description', false);

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, available)
VALUES (7, 'artist6@mail.com', '12345678', 'name', 'surname', false, false, 'description', false);

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, available)
VALUES (8, 'artist7@mail.com', '12345678', 'name', 'surname', false, false, 'description', false);

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, available)
VALUES (9, 'artist8@mail.com', '12345678', 'name', 'surname', false, false, 'description', false);

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, available)
VALUES (10, 'artist9@mail.com', '12345678', 'name', 'surname', false, false, 'description', false);

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, available)
VALUES (11, 'artist10@mail.com', '12345678', 'name', 'surname', false, false, 'description', false);

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, available)
VALUES (12, 'artist11@mail.com', '12345678', 'name', 'surname', false, false, 'description', false);


INSERT INTO auditions(id, bandid, title, description, creationdate, locationid, isOpen)
VALUES (1, 1, 'title', 'description', '2022-07-05 14:23:30', 1, true);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid, isOpen)
VALUES (2, 1, 'title', 'description', '2022-07-05 14:23:30', 1, true);


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

INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (6, 2, 2, '2022-07-05 14:23:30', 'PENDING', 'message');

INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (7, 2, 3, '2022-07-05 14:23:30', 'PENDING', 'message');

INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (8, 2, 4, '2022-07-05 14:23:30', 'PENDING', 'message');

INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (9, 2, 5, '2022-07-05 14:23:30', 'PENDING', 'message');

INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (10, 2, 6, '2022-07-05 14:23:30', 'PENDING', 'message');

INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (11, 2, 7, '2022-07-05 14:23:30', 'PENDING', 'message');

INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (12, 2, 8, '2022-07-05 14:23:30', 'PENDING', 'message');

INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (13, 2, 9, '2022-07-05 14:23:30', 'PENDING', 'message');

INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (14, 2, 10, '2022-07-05 14:23:30', 'PENDING', 'message');

INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (15, 2, 11, '2022-07-05 14:23:30', 'PENDING', 'message');

INSERT INTO applications(id, auditionid, applicantid, creationdate, state, message)
VALUES (16, 2, 12, '2022-07-05 14:23:30', 'PENDING', 'message');
