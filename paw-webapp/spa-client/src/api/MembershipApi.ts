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

interface PutParams {
    roles: string[];
    state: string;
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

    private putConfig = {
        headers: {
            'Content-Type': 'application/vnd.membership.v1+json'
        }
    }

    private postConfig = {
        headers: {
            'Content-Type': 'application/vnd.membership.v1+json',
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
            const membership: Membership = {
                id: data.id,
                artist: data.artist,
                band: data.band,
                state: data.state,
                description: data.description,
                roles: data.roles
            }

            return Promise.resolve(membership);
        });
    };

    public getUserMemberships = async (params: GetParams, page: number) => {
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
                            return {
                                id: membership.id,
                                artist: membership.artist,
                                band: membership.band,
                                state: membership.state,
                                description: membership.description,
                                roles: membership.roles
                            };
                        })
                        : [];
                let maxPage = 1;
                let previousPage = "";
                let nextPage = "";
                let lastPage = "";
                let firstPage = "";
                let parsed;
                if(response.headers) {
                    parsed = parseLinkHeader(response.headers.link);
                    if(parsed) {
                        maxPage = parseInt(parsed.last.page);
                        lastPage = parsed.last.url;
                        firstPage = parsed.first.url;
                        if(parsed.prev)
                            previousPage = parsed.prev.url;
                        if(parsed.next)
                            nextPage = parsed.next.url;
                    }
                }
                return Promise.resolve(new PagedContent(memberships, maxPage, nextPage, previousPage, lastPage, firstPage));
            });
    }

    public getUserMembershipsByBand = async (userId: number, bandId: number) => {
        return this.axiosPrivate.get(this.endpoint, {
            params: {
                user: userId,
                band: bandId
            }
        })
            .then((response) => {
                const data = response.data;
                const memberships: Membership[] =
                    Array.isArray(data) ?
                        data.map((membership: any) => {
                            return {
                                id: membership.id,
                                artist: membership.artist,
                                band: membership.band,
                                state: membership.state,
                                description: membership.description,
                                roles: membership.roles
                            };
                        })
                        : [];
                let maxPage = 1;
                let previousPage = "";
                let nextPage = "";
                let lastPage = "";
                let firstPage = "";
                let parsed;
                if(response.headers) {
                    parsed = parseLinkHeader(response.headers.link);
                    if(parsed) {
                        maxPage = parseInt(parsed.last.page);
                        lastPage = parsed.last.url;
                        firstPage = parsed.first.url;
                        if(parsed.prev)
                            previousPage = parsed.prev.url;
                        if(parsed.next)
                            nextPage = parsed.next.url;
                    }
                }
                return Promise.resolve(new PagedContent(memberships, maxPage, nextPage, previousPage, lastPage, firstPage));
            });
    }


    public getUserMembershipsUrl = async (url: string) => {
        return this.axiosPrivate.get(url)
            .then((response) => {
                const data = response.data;
                const memberships: Membership[] =
                    Array.isArray(data) ?
                        data.map((membership: any) => {
                            return {
                                id: membership.id,
                                artist: membership.artist,
                                band: membership.band,
                                state: membership.state,
                                description: membership.description,
                                roles: membership.roles
                            };
                        })
                        : [];
                let maxPage = 1;
                let previousPage = "";
                let nextPage = "";
                let lastPage = "";
                let firstPage = "";
                let parsed;
                if(response.headers) {
                    parsed = parseLinkHeader(response.headers.link);
                    if(parsed) {
                        maxPage = parseInt(parsed.last.page);
                        lastPage = parsed.last.url;
                        firstPage = parsed.first.url;
                        if(parsed.prev)
                            previousPage = parsed.prev.url;
                        if(parsed.next)
                            nextPage = parsed.next.url;
                    }
                }
                return Promise.resolve(new PagedContent(memberships, maxPage, nextPage, previousPage, lastPage, firstPage));
            });
    }

    public inviteToBand = async (params: PostParams) => {
        return this.axiosPrivate.post(`${this.endpoint}?user=${params.userId}`,
            { roles: params.roles, description: params.description },
            this.postConfig).then((response) => {
                return Promise.resolve(response);
            });
    }

    public createMembershipByApplication = async (params: PostParams, auditionId: number) => {
        return this.axiosPrivate.post(`${this.endpoint}?user=${params.userId}&audition=${auditionId}`,
            { roles: params.roles, description: params.description },
            this.postConfig).then((response) => {
            return Promise.resolve(response);
        });
    }

    public editMembership = async (membershipId: number, membership: PutParams) => {
        return this.axiosPrivate.put(`${this.endpoint}/${membershipId}`,
            membership, this.putConfig).then((response) => {
                return Promise.resolve(response);
            });
    }

    public kickMember = async (membershipId: number) => {
        return this.axiosPrivate.delete(`${this.endpoint}/${membershipId}`, this.config).then((response) => {
            return Promise.resolve(response);
        });
    }

}

export default MembershipApi;
