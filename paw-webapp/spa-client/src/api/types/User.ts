type UserCreateInput = {
    email: string;
    password: string;
    passwordConfirmation: string;
    name: string;
    surname?: string;
    band: boolean;
}

type UserUpdateInput = {
    name: string;
    surname: string;
    description: string;
    location: string;
    genres: string[];
    roles: string[];
    available: boolean;
}

type User = {
    applications: string;
    available: boolean;
    band: boolean;
    description: string;
    enabled: boolean;
    genres: string[];
    id: number;
    location: string;
    name: string;
    roles: string[];
    self: string;
    socialMedia: string;
    profileImage: string;
    surname: string;
};

type UserPasswordResetRequestInput = {
    email: string
}

type UserPasswordResetInput = {
    newPassword: string,
    newPasswordConfirmation: string
}

type UserSocialMedia = {
    id: number;
    twitterUrl: string;
    spotifyUrl: string;
    user: string;
}


export type { UserCreateInput, UserUpdateInput, User, UserSocialMedia, UserPasswordResetRequestInput, UserPasswordResetInput }