import MembershipApi from "../api/MembershipApi";
import RoleApi from "../api/RoleApi";
import ApiResult from "../api/types/ApiResult";
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

    public async getUserMemberships(params: Params): Promise<ApiResult<Membership[]>> {
        try {
            const membershipList = await this.membershipApi.getUserMemberships(params);
            let memberships: Membership[] = [];
            for await (const membership of membershipList) {
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
                memberships,
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

    public async canInvite(bandId: number, artistId: number) : Promise<ApiResult<Boolean>> {
        console.log("canInvite (service) con bandId: "+ bandId + " y el user ID: " + artistId);
        try {
            const members = await this.getUserMemberships({user: bandId, state: "ACCEPTED"});
            const pendings = await this.getUserMemberships({user: bandId, state: "PENDING"});
            if(!members.hasFailed()) {
                if(!pendings.hasFailed()) {
                    const membersIds = members.getData().map( (member) => member.artist.id );
                    const pendingsIds = pendings.getData().map( (member) => member.artist.id );
                    return new ApiResult( !(membersIds.includes(artistId) || pendingsIds.includes(artistId)), false, null as any);
                } else {
                    throw pendings.getError();
                }
            }
            throw members.getError();
        } catch (error: any) {
            return ErrorService.returnApiError(error);
        }
    }
}