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

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (4, 'artist3@mail.com', '12345678', 'name', 'surname', false, false, 'description');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (5, 'artist4@mail.com', '12345678', 'name', 'surname', false, false, 'description');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (6, 'artist5@mail.com', '12345678', 'name', 'surname', false, false, 'description');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (7, 'artist6@mail.com', '12345678', 'name', 'surname', false, false, 'description');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (8, 'artist7@mail.com', '12345678', 'name', 'surname', false, false, 'description');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (9, 'artist8@mail.com', '12345678', 'name', 'surname', false, false, 'description');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (10, 'artist9@mail.com', '12345678', 'name', 'surname', false, false, 'description');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (11, 'artist10@mail.com', '12345678', 'name', 'surname', false, false, 'description');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (12, 'artist11@mail.com', '12345678', 'name', 'surname', false, false, 'description');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (13, 'band2@mail.com', '12345678', 'name', null, true, false, 'description');

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (1, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (2, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (3, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (4, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (5, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (6, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (7, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (8, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (9, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (10, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (11, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (12, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (13, 13, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (1,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (1,1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (2,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (2,1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (3,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (3,1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (4,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (4,1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (5,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (5,1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (6,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (6,1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (7,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (7,1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (8,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (8,1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (9,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (9,1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (10,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (10,1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (11,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (11,1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (12,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (12,1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (13,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (13,1);

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (1, 2, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (1, 3, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (1, 5, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (1, 6, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (1, 7, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (1, 8, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (1, 9, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (1, 10, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (1, 11, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (1, 12, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (2, 2, '2022-07-05 14:23:30', 'ACCEPTED');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (2, 3, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (3, 2, '2022-07-05 14:23:30', 'REJECTED');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (1, 4, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (2, 4, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (3, 4, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (4, 4, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (5, 4, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (6, 4, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (7, 4, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (8, 4, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (9, 4, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (10, 4, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (11, 4, '2022-06-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (12, 4, '2022-06-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (13, 4, '2022-06-05 14:23:30', 'REJECTED');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (13, 2, '2022-06-05 14:23:30', 'REJECTED');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (2, 5, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (2, 6, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (2, 7, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (2, 8, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (2, 9, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (2, 10, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (2, 11, '2022-07-05 14:23:30', 'PENDING');

INSERT INTO applications(auditionid, applicantid, creationdate, state)
VALUES (2, 12, '2022-07-05 14:23:30', 'PENDING');