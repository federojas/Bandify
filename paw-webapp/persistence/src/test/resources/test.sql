CREATE TABLE IF NOT EXISTS locations
(
    id INTEGER IDENTITY PRIMARY KEY,
    location VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS auditions
(
    id INTEGER IDENTITY PRIMARY KEY,
    bandId INT NOT NULL,
    title VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(512) NOT NULL,
    creationDate TIMESTAMP NOT NULL,
    locationId integer NOT NULL,
    email VARCHAR(100) NOT NULL,
    FOREIGN KEY (locationId) REFERENCES locations
);
-- TODO : FOREIGN KEY(band_id) REFERENCES Band(band_id) ON DELETE CASCADE

CREATE TABLE IF NOT EXISTS genres
(
    id INTEGER IDENTITY PRIMARY KEY,
    genre VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS roles
(
    id INTEGER IDENTITY PRIMARY KEY,
    role VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS auditionGenres
(
    auditionId INTEGER NOT NULL,
    genreId INTEGER NOT NULL,
    FOREIGN KEY(auditionId) references auditions(id) ON DELETE CASCADE,
    FOREIGN KEY(genreId) references genres(id) ON DELETE CASCADE,
    UNIQUE(auditionId, genreId)
);

CREATE TABLE IF NOT EXISTS auditionRoles
(
    auditionId INTEGER NOT NULL,
    roleId INTEGER NOT NULL,
    FOREIGN KEY(auditionId) REFERENCES auditions(id) ON DELETE CASCADE,
    FOREIGN KEY(roleId) references roles(id) ON DELETE CASCADE,
    UNIQUE(auditionId, roleId)
);