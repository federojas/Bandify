import { parseLinkHeader } from "@web3-storage/parse-link-header";
import { AxiosInstance } from "axios";
import Membership from "./types/Membership";
import PagedContent from "./types/PagedContent";


interface GetParams {
    user: number;
    state?: string;
    preview?: boolean;
}

interface PostParams {
    userId: number;
    roles: string[];
    description: string;
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

    private listConfig = {
        headers: {
            'Accept': 'application/vnd.membership-list.v1+json'
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
                roles: data.roles
            }

            return Promise.resolve(membership);
        });
    };

    public getUserMemberships = async (params: GetParams, page?: number) => {
        return this.axiosPrivate.get(this.endpoint, {
            params: {
                user: params.user,
                state: params.state,
                preview: params.preview,
                page: page
            }, paramsSerializer: {
                indexes: null
            }, ...this.listConfig
        })
            .then((response) => {
                const data = response.data;
                const memberships: Membership[] =
                    Array.isArray(data) ?
                        data.map((membership: any) => {
                            const artistLink = membership.artist.split('/')
                            const artistId: number = parseInt(artistLink[artistLink.length - 1]);
                            const bandLink = membership.band.split('/')
                            const bandId: number = parseInt(bandLink[bandLink.length - 1]);
                            return {
                                id: membership.id,
                                artistId: artistId,
                                bandId: bandId,
                                state: membership.state,
                                description: membership.description,
                                roles: membership.roles
                            };
                        })
                        : [];
                let maxPage = 1;
                let previousPage = "";
                let nextPage = "";
                let parsed;
                if (response.headers) {
                    parsed = parseLinkHeader(response.headers.link);
                    if (parsed) {
                        maxPage = parseInt(parsed.last.page);
                        if (parsed.prev)
                            previousPage = parsed.prev.url;
                        if (parsed.next)
                            nextPage = parsed.next.url;
                    }
                }
                return Promise.resolve(new PagedContent(memberships, maxPage, nextPage, previousPage));
            });
    }

    public inviteToBand = async (params: PostParams) => {
        return this.axiosPrivate.post(`${this.endpoint}?user=${params.userId}`,
            { roles: params.roles, description: params.description },
            this.config).then((response) => {
                return Promise.resolve(response);
            });
    }

};

export default MembershipApi;
