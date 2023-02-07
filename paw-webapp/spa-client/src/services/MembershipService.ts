import MembershipApi from "../api/MembershipApi";
import RoleApi from "../api/RoleApi";
import ApiResult from "../api/types/ApiResult";
import PagedContent from "../api/types/PagedContent";
import PostResponse from "../api/types/PostResponse";
import { User } from "../api/types/User";
import UserApi from "../api/UserApi";
import Membership from "../models/Membership";
import { ErrorService } from "./ErrorService";

interface Params {
    user: number;
    state?: string;
    preview?: boolean;
}


interface PostParams {
    userId: number;
    roles: string[];
    description: string;
}

export default class MembershipService {

    private membershipApi: MembershipApi;
    private userApi: UserApi;

    constructor(membershipApi: MembershipApi, userApi: UserApi) {
        this.membershipApi = membershipApi;
        this.userApi = userApi;
    }

    public async getMembershipById(id: number): Promise<ApiResult<Membership>> {
        try {
            const currentMembership = await this.membershipApi.getMembershipById(id);
            const artist: User = await this.userApi.getUserById(currentMembership.artistId);
            const band: User = await this.userApi.getUserById(currentMembership.bandId);

            return new ApiResult(
                {
                    id: currentMembership.id,
                    artist: artist,
                    band: band,
                    description: currentMembership.description,
                    roles: currentMembership.roles,
                    state: currentMembership.state
                } as Membership,
                false,
                null as any
            );
        } catch (error: any) {
            return ErrorService.returnApiError(error);
        }
    }

    // TODO: si piden preview, no vienen los links de paginacion, pasa algo con eso?
    public async getUserMemberships(params: Params, page?: number): Promise<ApiResult<PagedContent<Membership[]>>> {
        try {
            const response = await this.membershipApi.getUserMemberships(params, page);
            let memberships: Membership[] = [];
            for await (const membership of response.getContent()) {
                const artist: User = await this.userApi.getUserById(membership.artistId);
                const band: User = await this.userApi.getUserById(membership.bandId);
                memberships.push(
                    {
                        id: membership.id,
                        artist: artist,
                        band: band,
                        description: membership.description,
                        roles: membership.roles,
                        state: membership.state
                    } as Membership
                )
            }
            return new ApiResult(
                new PagedContent(
                    memberships, response.getMaxPage(),
                    response.getNextPage(),
                    response.getPreviousPage()),
                false,
                null as any
            )
        } catch (error: any) {
            return ErrorService.returnApiError(error);
        }
    }

    public async inviteToBand(params: PostParams): Promise<ApiResult<PostResponse>> {
        try {
            const postResponse = await this.membershipApi.inviteToBand(params);
            const url: string = postResponse.headers!.location!;
            const tokens = url.split('/')
            const id: number = parseInt(tokens[tokens.length - 1]);
            const data: PostResponse = { url: url, id: id };
            return new ApiResult(
                data,
                false,
                null as any
            );
        } catch (error: any) {
            return ErrorService.returnApiError(error);
        }
    }

    // TODO: esto esta mal porque solo revisa la pagina 1.
    public async canInvite(bandId: number, artistId: number) : Promise<ApiResult<Boolean>> {
        console.log("canInvite (service) con bandId: "+ bandId + " y el user ID: " + artistId);
        try {
            const members = await this.getUserMemberships({user: bandId});
            if(!members.hasFailed()) {
                const membersIds = members.getData().getContent().map( (member) => member.artist.id );
                return new ApiResult( !membersIds.includes(artistId) , false, null as any);
            }
            throw members.getError();
        } catch (error: any) {
            return ErrorService.returnApiError(error);
        }
    }

    
}