TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE memberships RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE membershiproles RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO roles VALUES(1,'role');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, locationid, available)
VALUES (1, 'artist@mail.com', '12345678', 'name', 'surname', false, false, 'description', null, false);

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, locationid, available)
VALUES (2, 'band@mail.com', '12345678', 'name', null, true, false, 'description', null, false);

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, locationid, available)
VALUES (3, 'artist2@mail.com', '12345678', 'name', 'surname', false, false, 'description', null, false);

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, locationid, available)
VALUES (4, 'band2@mail.com', '12345678', 'name', null, true, false, 'description', null, false);

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, locationid, available)
VALUES (5, 'band3@mail.com', '12345678', 'name', null, true, false, 'description', null, false);

INSERT INTO memberships(id, description, state, artistid, bandid)
VALUES (1, 'description', 'PENDING', 1, 2);

INSERT INTO memberships(id, description, state, artistid, bandid)
VALUES (2, 'description', 'ACCEPTED', 3, 2);

INSERT INTO memberships(id, description, state, artistid, bandid)
VALUES (3, 'description', 'PENDING', 3, 4);

INSERT INTO memberships(id, description, state, artistid, bandid)
VALUES (4, 'description', 'PENDING', 1, 4);

INSERT INTO membershiproles(membershipid, roleid)
VALUES (1, 1);

INSERT INTO membershiproles(membershipid, roleid)
VALUES (2, 1);

INSERT INTO membershiproles(membershipid, roleid)
VALUES (3, 1);

INSERT INTO membershiproles(membershipid, roleid)
VALUES (4, 1);