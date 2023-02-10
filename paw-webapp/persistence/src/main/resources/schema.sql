CREATE TABLE IF NOT EXISTS locations
(
    id SERIAL PRIMARY KEY,
    location VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS genres
(
    id SERIAL PRIMARY KEY,
    genre VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS roles
(
    id SERIAL PRIMARY KEY,
    role VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id SERIAL PRIMARY KEY,
    email VARCHAR(254) NOT NULL,
    password TEXT NOT NULL,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50),
    isBand BOOLEAN,
    isEnabled BOOLEAN,
    available BOOLEAN,
    description VARCHAR(500),
    locationId BIGINT,
    UNIQUE(email),
    FOREIGN KEY (locationId) REFERENCES locations
);

CREATE TABLE IF NOT EXISTS auditions
(
    id SERIAL PRIMARY KEY,
    bandId BIGINT NOT NULL,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(300) NOT NULL,
    creationDate TIMESTAMP NOT NULL,
    locationId BIGINT NOT NULL,
    isOpen BOOLEAN,
    FOREIGN KEY (locationId) REFERENCES locations,
    FOREIGN KEY (bandId) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS auditionGenres
(
    auditionId BIGINT NOT NULL,
    genreId BIGINT NOT NULL,
    FOREIGN KEY(auditionId) REFERENCES auditions(id) ON DELETE CASCADE,
    FOREIGN KEY(genreId) REFERENCES genres(id) ON DELETE CASCADE,
    UNIQUE(auditionId, genreId)
);

CREATE TABLE IF NOT EXISTS auditionRoles
(
    auditionId BIGINT NOT NULL,
    roleId BIGINT NOT NULL,
    FOREIGN KEY(auditionId) REFERENCES auditions(id) ON DELETE CASCADE,
    FOREIGN KEY(roleId) REFERENCES roles(id) ON DELETE CASCADE,
    UNIQUE(auditionId, roleId)
);

CREATE TABLE IF NOT EXISTS verificationTokens (
    tokenId SERIAL PRIMARY KEY,
    userId BIGINT NOT NULL,
    token TEXT NOT NULL,
    expiryDate TIMESTAMP NOT NULL,
    type TEXT NOT NULL,
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS userGenres (
    userId BIGINT NOT NULL,
    genreId BIGINT NOT NULL,
    FOREIGN KEY(userId) references users(id) ON DELETE CASCADE,
    FOREIGN KEY(genreId) references genres(id) ON DELETE CASCADE,
    UNIQUE(userId, genreId)
);

CREATE TABLE IF NOT EXISTS userRoles (
    userId BIGINT NOT NULL,
    roleId BIGINT NOT NULL,
    FOREIGN KEY(userId) references users(id) ON DELETE CASCADE,
    FOREIGN KEY(roleId) references roles(id) ON DELETE CASCADE,
    UNIQUE(userId, roleId)
);

CREATE TABLE IF NOT EXISTS profileImages
(
   userId BIGINT PRIMARY KEY,
   image BYTEA NOT NULL,
   FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS profileMediaUrls
(
    id SERIAL PRIMARY KEY,
    userId BIGINT NOT NULL,
    url VARCHAR(2083) NOT NULL,
    mediatype TEXT NOT NULL,
    UNIQUE(userId,mediatype),
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS applications
(
    id SERIAL PRIMARY KEY,
    auditionId BIGINT NOT NULL,
    applicantId BIGINT NOT NULL,
    creationDate TIMESTAMP NOT NULL,
    state TEXT NOT NULL,
    message VARCHAR(300) NOT NULL,
    UNIQUE(auditionId,applicantId),
    FOREIGN KEY (auditionId) REFERENCES auditions(id) ON DELETE CASCADE,
    FOREIGN KEY (applicantId) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS memberships
(
    id SERIAL PRIMARY KEY,
    bandId BIGINT NOT NULL,
    artistId BIGINT NOT NULL,
    description VARCHAR(100),
    state TEXT NOT NULL,
    FOREIGN KEY (bandId) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (artistId) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE(bandId,artistId)
);

CREATE TABLE IF NOT EXISTS membershipRoles
(
    membershipId BIGINT NOT NULL,
    roleId BIGINT NOT NULL,
    FOREIGN KEY (membershipId) REFERENCES memberships(id) ON DELETE CASCADE,
    FOREIGN KEY (roleId) REFERENCES roles(id),
    UNIQUE(membershipId,roleId)
);