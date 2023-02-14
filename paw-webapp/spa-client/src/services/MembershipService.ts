import MembershipApi from "../api/MembershipApi";
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

interface EditParams {
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
      const artist: User = await this.userApi.getUserByUrl(currentMembership.artist);
      const band: User = await this.userApi.getUserByUrl(currentMembership.band);

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

  public async getUserMemberships(params: Params, page: number): Promise<ApiResult<PagedContent<Membership[]>>> {
    try {
      const response = await this.membershipApi.getUserMemberships(params, page);
      let memberships: Membership[] = [];
      for await (const membership of response.getContent()) {
        const artist: User = await this.userApi.getUserByUrl(membership.artist);
        const band: User = await this.userApi.getUserByUrl(membership.band);
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
          response.getPreviousPage(),
            response.getLastPage(),
            response.getFirstPage()),
        false,
        null as any
      )
    } catch (error: any) {
      return ErrorService.returnApiError(error);
    }
  }

  public async getUserMembershipsByBand(userId: number, bandId: number): Promise<ApiResult<PagedContent<Membership[]>>> {
    try {
      const response = await this.membershipApi.getUserMembershipsByBand(userId, bandId);
      let memberships: Membership[] = [];
      for await (const membership of response.getContent()) {
        const artist: User = await this.userApi.getUserByUrl(membership.artist);
        const band: User = await this.userApi.getUserByUrl(membership.band);
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
              response.getPreviousPage(),
              response.getLastPage(),
              response.getFirstPage()),
          false,
          null as any
      )
    } catch (error: any) {
      return ErrorService.returnApiError(error);
    }
  }

  public async getUserMembershipsUrl(url: string): Promise<ApiResult<PagedContent<Membership[]>>> {
    try {
      const response = await this.membershipApi.getUserMembershipsUrl(url);
      let memberships: Membership[] = [];
      for await (const membership of response.getContent()) {
        const artist: User = await this.userApi.getUserByUrl(membership.artist);
        const band: User = await this.userApi.getUserByUrl(membership.band);
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
              response.getPreviousPage(),
              response.getLastPage(),
              response.getFirstPage()),
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

  public async createMembershipByApplication(params: PostParams, auditionId: number): Promise<ApiResult<PostResponse>> {
    try {
      const postResponse = await this.membershipApi.createMembershipByApplication(params, auditionId);
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

  public async accept(membership:Membership) {
    try {
      await this.membershipApi.editMembership(membership.id, {
        roles: membership.roles,
        description: membership.description,
        state: "ACCEPTED"
      });
      return new ApiResult(null as any, false, null as any);
    } catch (error: any) {
      return ErrorService.returnApiError(error);
    }
  }

  public async reject(membership:Membership) {
    try {
      await this.membershipApi.editMembership(membership.id, {
        roles: membership.roles,
        description: membership.description,
        state: "REJECTED"
      });
      return new ApiResult(null as any, false, null as any);
    } catch (error: any) {
      return ErrorService.returnApiError(error);
    }
  }

  public async edit(membership:EditParams, membershipId: number) {
    try {
      await this.membershipApi.editMembership(membershipId, {
        roles: membership.roles,
        description: membership.description,
        state: 'ACCEPTED'
      });
      return new ApiResult(null as any, false, null as any);
    } catch (error: any) {
      return ErrorService.returnApiError(error);
    }
  }

  public async kickMember(membershipId:number) {
    try {
      await this.membershipApi.kickMember(membershipId);

      return new ApiResult(null as any, false, null as any);
    } catch (error: any) {
      return ErrorService.returnApiError(error);
    }
  }
    
}