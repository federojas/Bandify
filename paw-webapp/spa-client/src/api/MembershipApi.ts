import { AxiosInstance } from "axios";
import Membership from "./types/Membership";


interface Params {
    userId: number;
    state?: string;
    preview?: boolean;
}


class MembershipApi {

    private axiosPrivate: AxiosInstance;

    constructor(axiosPrivate: AxiosInstance) {
        this.axiosPrivate = axiosPrivate;
    }

    private config = {
        headers: {
            'Accept': 'application/vnd.membership.v1+json'
        }
    }

    private endpoint: string = "/memberships";

    public getMembershipById = async (id: number) => {
        return this.axiosPrivate.get(`${this.endpoint}/${id}`, this.config).then((response) => {
            const data = response.data;
            const artistLink = data.artist.split('/')
            const artistId: number = parseInt(artistLink[artistLink.length - 1]);
            const bandLink = data.artist.split('/')
            const bandId: number = parseInt(bandLink[bandLink.length - 1]);

            const membership: Membership = {
                id: data.id,
                artistId: artistId,
                bandId: bandId,
                state: data.state,
                description: data.description,
            }

            return Promise.resolve(membership);
        });
    };

    public getUserMemberships = async (params: Params) => {
        return this.axiosPrivate.get(this.endpoint, {
            params: {
                userId: params.userId,
                state: params.state,
                preview: params.preview,
            }, ...this.config
        })
            .then((response) => {
                const data = response.data;
                const memberships: Membership[] =
                    Array.isArray(data) ?
                        data.map((membership: any) => {
                            const artistLink = membership.artist.split('/')
                            const artistId: number = parseInt(artistLink[artistLink.length - 1]);
                            const bandLink = membership.artist.split('/')
                            const bandId: number = parseInt(bandLink[bandLink.length - 1]);
                            return {
                                id: membership.id,
                                artistId: artistId,
                                bandId: bandId,
                                state: membership.state,
                                description: membership.description,
                            };
                        })
                        : [];
                return Promise.resolve(memberships);
            });
    }


};

export default MembershipApi;
