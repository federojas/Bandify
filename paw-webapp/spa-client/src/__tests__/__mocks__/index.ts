import axios from "axios"
export const user1 = {
    applications: "",
    available: true,
    description: "",
    enabled: true,
    band: true,
    genres: [],
    id: 1,
    location: "CABA",
    name: "name",
    roles: [],
    socialMedia: "",
    surname: "",
    profileImage: ""
}

export const user2 = {
    applications: "",
    available: true,
    description: "",
    enabled: true,
    band: false,
    genres: ["rock", "pop"],
    id: 1,
    location: "arg",
    name: "artist",
    roles: ["baterista", "guitarrista"],
    socialMedia: "",
    surname: "surname",
    profileImage: ""
}

export const correctUserCreateInput = {
    email: "band@mail.com",
    password: "12345678",
    passwordConfirmation: "12345678",
    name: "name",
    surname: "surname",
    band: false
}

export const correctUserUpdateInput = {
    name: "updated name",
    surname: "",
    description: "description",
    location: "arg",
    genres: [],
    roles: [],
    available: true
}

export const application1 = {
    id: 1,
    creationDate: new Date(),
    message: "message",
    audition: "audition",
    applicant: "",
    state: "PENDING",
    title: "title 1"
}

export const application2 = {
    id: 2,
    creationDate: new Date(),
    message: "message",
    audition: "audition",
    applicant: "",
    state: "PENDING",
    title: "title 2"
}

export const passwordRequest = {
    email:correctUserCreateInput.email,
}

export const socialMedia1 = {
    id: 1,
    type: "facebook",
    url: "https://www.facebook.com/",
    user: "1"
}

export const socialMedia2 = {
    id: 2,
    type: "instagram",
    url: "https://www.instagram.com/",
    user: "1"
}

export const audition1 = {
    id: 1,
    creationDate: new Date(),
    title: "title 1",
    lookingFor: ["baterista", "guitarrista"],
    musicGenres: ["rock", "pop"],
    location: "arg",
    description: "descripcion",
    applications: "",
    owner: ""
}

export const audition2 = {
    id: 2,
    creationDate: new Date(),
    title: "title 2",
    lookingFor: ["baterista", "guitarrista"],
    musicGenres: ["rock", "pop"],
    location: "arg",
    description: "descripcion",
    applications: "",
    owner: ""
}

export const correctAuditionInput = {
    title: "title",
    location: "arg",
    lookingFor: ["baterista", "guitarrista"],
    musicGenres: ["rock", "pop"],
    description: "descripcion",
}

export const auditionPostResponse = {
    id:1,
    url: "http://localhost:8080/auditions/1",
}


//todo: ojo que el genre que devuelve la api es distinto al genre que devuelve el service
export const genre1Response = {
    id: 1,
    genreName: "rock"
}

export const genre2Response = {
    id: 2,
    genreName: "pop"
}

export const genre1 = {
    id: 1,
    name: "rock"
}

export const genre2 = {
    id: 2,
    name: "pop"
}

export const location1Response = {
    id: 1,
    locName: "CABA"
}

export const location2Response = {
    id: 2,
    locName: "CORDOBA"
}

export const location1 = {
    id: 1,
    name: "CABA"
}

export const location2 = {
    id: 2,
    name: "CORDOBA"
}

export const role1Response = {
    id: 1,
    roleName: "baterista"
}

export const role2Response = {
    id: 2,
    roleName: "guitarrista"
}

export const role1 = {
    id: 1,
    name: "baterista"
}

export const role2 = {
    id: 2,
    name: "guitarrista"
}

export const membership1 = {
    id: 1,
    artist: user2,
    band: user1,
    description: "description",
    state: "PENDING",
    roles: ["baterista", "guitarrista"]
}


test("", () => {});
