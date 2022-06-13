package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.MembershipState;
import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MembershipService {

    List<Membership> getUserMemberships(User user, MembershipState state, int page);

    List<Membership> getUserMembershipsPreview(User user);

    int getTotalUserMembershipsPages(User user, MembershipState state);

    Membership createMembershipInvite(Membership.Builder builder);

    void deleteMembership(long id);

    void changeState(Membership membership, MembershipState state);

    Optional<Membership> getMembershipById(long id);

    Optional<Membership> getMembershipByUsers(User band, User artist);

    boolean createMembershipByApplication(Membership.Builder builder, long auditionId);

    int getPendingMembershipsCount(User user);

    void editMembershipById(String description, Set<Role> roles, long id);

    boolean canBeAddedToBand(User band, User artist);
}
