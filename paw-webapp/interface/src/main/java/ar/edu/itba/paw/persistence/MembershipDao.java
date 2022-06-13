package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.MembershipState;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Optional;

public interface MembershipDao {

    List<Membership> getUserMembershipsByState(User user, MembershipState state, int page);

    List<Membership> getUserMembershipsPreview(User user);

    int getTotalUserMembershipsByStatePages(User user, MembershipState state);

    Membership createMembership(Membership.Builder builder);

    void deleteMembership(long id);

    Optional<Membership> getMembershipById(long id);

    Optional<Membership> getMembershipByUsers(User band, User artist);

    boolean membershipExists(User band, User artist);

    int getPendingMembershipsCount(User user);
}
