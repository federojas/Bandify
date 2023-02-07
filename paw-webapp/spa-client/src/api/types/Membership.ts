type Membership = {
    id: number;
    artistId: number;
    bandId: number;
    description: string;
    state: string;
    roles: string[];
}

export default Membership;