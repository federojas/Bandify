TRUNCATE TABLE auditions RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE genres RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE auditiongenres RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE locations RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE usergenres RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO locations(id, location)
VALUES (1, 'location');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (1, 'band@mail.com', '12345678', 'name', null, true, false, 'description');

INSERT INTO auditions(id, bandid, title, description, creationdate, locationid)
VALUES (1, 1, 'title', 'description', '2022-07-05 14:23:30', 1);

INSERT INTO genres(id, genre)
VALUES (1, 'genre');

INSERT INTO genres(id, genre)
VALUES (2, 'genre2');

INSERT INTO genres(id, genre)
VALUES (3, 'genre3');

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (1, 1);

INSERT INTO auditiongenres(auditionid, genreid)
VALUES (1, 2);

INSERT INTO usergenres(userid, genreid)
VALUES (1, 1);

INSERT INTO usergenres(userid, genreid)
VALUES (1, 2);
