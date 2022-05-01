-- TODO: CHEQUEAR LOS TIPOS DE DATOS, INT, BIGINT, VARCHAR, TIMESTAMP,ETC
CREATE TABLE IF NOT EXISTS locations
(
    id SERIAL PRIMARY KEY,
    location VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS genres
(
    id SERIAL PRIMARY KEY,
    genre VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS roles
(
    id SERIAL PRIMARY KEY,
    role VARCHAR(100) UNIQUE NOT NULL
);


CREATE TABLE IF NOT EXISTS users
(
    id SERIAL PRIMARY KEY,
    email VARCHAR(254) NOT NULL,
    password TEXT NOT NULL,
    name TEXT NOT NULL,
    surname TEXT,
    isBand BOOLEAN,
    isEnabled BOOLEAN,
    description TEXT,
   -- profileImage BIGINT,
    UNIQUE(email)
);

CREATE TABLE IF NOT EXISTS auditions
(
    id SERIAL PRIMARY KEY,
    bandId INT NOT NULL,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(300) NOT NULL,
    creationDate TIMESTAMP NOT NULL,
    locationId integer NOT NULL,
    FOREIGN KEY (locationId) REFERENCES locations,
    FOREIGN KEY (bandId) REFERENCES users(id) ON DELETE CASCADE
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

CREATE TABLE IF NOT EXISTS verificationTokens (
    tokenId SERIAL PRIMARY KEY,
    userId INTEGER NOT NULL,
    token TEXT NOT NULL,
    expiryDate TIMESTAMP NOT NULL,
    type TEXT NOT NULL,
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS userGenres (
    userId INTEGER NOT NULL,
    genreId INTEGER NOT NULL,
    FOREIGN KEY(userId) references users(id) ON DELETE CASCADE,
    FOREIGN KEY(genreId) references genres(id) ON DELETE CASCADE,
    UNIQUE(userId, genreId)
);

CREATE TABLE IF NOT EXISTS userRoles (
    userId INTEGER NOT NULL,
    roleId INTEGER NOT NULL,
    FOREIGN KEY(userId) references users(id) ON DELETE CASCADE,
    FOREIGN KEY(roleId) references roles(id) ON DELETE CASCADE,
    UNIQUE(userId, roleId)
);

CREATE TABLE IF NOT EXISTS profileImages
(
   userId INTEGER PRIMARY KEY,
   image    BYTEA,
   FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE
);

-- TODO: usar enum para state
CREATE TABLE IF NOT EXISTS applications
(
    auditionId INTEGER NOT NULL,
    applicantId INTEGER NOT NULL,
    state TEXT NOT NULL,
    PRIMARY KEY(auditionId,applicantId),
    FOREIGN KEY (auditionId) REFERENCES auditions(id) ON DELETE CASCADE,
    FOREIGN KEY (applicantId) REFERENCES users(id) ON DELETE CASCADE
);