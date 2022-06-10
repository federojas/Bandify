package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.MembershipState;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Optional;

public interface MembershipService {

    List<Membership> getBandMemberships(User user, MembershipState state, int page);

    List<Membership> getBandMembershipsPreview(User user);

    List<Membership> getArtistMemberships(User user, MembershipState state, int page);

    int getTotalUserMembershipsPages(User user, MembershipState state);

    Membership createMembershipInvite(Membership.Builder builder);

    void deleteMembership(long id);

    void changeState(Membership membership, MembershipState state);

    Optional<Membership> getMembershipById(long id);

    void createMembershipByApplication(Membership.Builder builder, long applicationId, long id);

    int getPendingMembershipsCount(User user);
}
