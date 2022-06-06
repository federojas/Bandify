TRUNCATE TABLE genres RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO genres(id, genre)
VALUES (1, 'genre');

INSERT INTO genres(id, genre)
VALUES (2, 'genre2');

INSERT INTO genres(id, genre)
VALUES (3, 'genre3');
