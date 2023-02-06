import User from "./User";

export default interface Membership {
    id: number;
    artist: User;
    band: User;
    description: string;
    state: string;
    roles: string[];
}