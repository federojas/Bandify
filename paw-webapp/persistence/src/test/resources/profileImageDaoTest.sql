TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE profileimages RESTART IDENTITY AND COMMIT NO CHECK;

--ARTIST USER
INSERT INTO users(id, email, password, name, surname, isband, isenabled, description)
VALUES (1, 'artist@mail.com', '12345678', 'name', 'surname', false, false, 'description');
