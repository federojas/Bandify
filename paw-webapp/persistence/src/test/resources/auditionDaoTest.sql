TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE auditions RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE genres RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE locations RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE auditionroles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE auditiongenres RESTART IDENTITY AND COMMIT NO CHECK;


INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (1, 'band@mail.com', '12345678', 'name', 'surname', true, false, 'description');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (2, 'band2@mail.com', '12345678', 'name', 'surname', true, false, 'description');

INSERT INTO roles(id, role)
VALUES (1, 'role');

INSERT INTO genres(id, genre)
VALUES (1, 'genre');

INSERT INTO roles(id, role)
VALUES (2, 'role2');

INSERT INTO genres(id, genre)
VALUES (3, 'genreUnique');

INSERT INTO roles(id, role)
VALUES (3, 'roleUnique');

INSERT INTO genres(id, genre)
VALUES (2, 'genre2');

INSERT INTO locations(id, location)
VALUES (1, 'location');

INSERT INTO locations(id, location)
VALUES (2, 'location2');

INSERT INTO locations(id, location)
VALUES (3, 'location3');

INSERT INTO locations(id, location)
VALUES (4, 'locationUnique');

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (1, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (2, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (3, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (4, 1, 'title', 'description', '2022-07-05 14:23:30', 2);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (5, 1, 'title', 'description', '2022-07-05 14:23:30', 2);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (6, 1, 'title', 'description', '2022-07-05 14:23:30', 2);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (7, 1, 'title', 'description', '2022-07-05 14:23:30', 3);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (8, 1, 'title', 'description', '2022-07-05 14:23:30', 3);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (9, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (10, 1, 'query title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (11, 1, 'title', 'description', '2022-07-05 14:23:30', 1);


INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (12, 2, 'title', 'description', '2022-08-05 14:23:30', 1);

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (13, 2, 'title', 'description', '2022-06-05 14:23:30', 1);


INSERT INTO auditionroles(auditionid, roleid)
VALUES (1,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (1,1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (1,2);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (1,2);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (11,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (11,1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (11,2);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (11,2);

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
VALUES (6,2);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (6,2);

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
VALUES (12,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (12,1);

INSERT INTO auditionroles(auditionid, roleid)
VALUES (13,1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (13,1);