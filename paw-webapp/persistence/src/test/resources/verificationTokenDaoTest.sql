TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE verificationtokens RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE locations RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO locations(id, location)
VALUES (1, 'loc');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, locationid, available)
VALUES (1, 'band@mail.com', '12345678', 'name', null, true, false, 'description', 1, false);

INSERT INTO verificationtokens(tokenid, userid, token, expirydate, type)
VALUES (1, 1, 'token-reset', '2022-07-05 14:23:30', 'reset');

INSERT INTO verificationtokens(tokenid, userid, token, expirydate, type)
VALUES (2, 1, 'token-verify', '2022-07-05 14:23:30', 'verify');

INSERT INTO users(id, email, password, name, surname, isband, isenabled, description, locationid, available)
VALUES (2, 'band2@mail.com', '12345678', 'name', null, true, false, 'description', 1, false);

INSERT INTO verificationtokens(tokenid, userid, token, expirydate, type)
VALUES (3, 2, 'token-reset-2', '2022-07-05 14:23:30', 'reset');