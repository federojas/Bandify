package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.User;

import java.util.List;

public interface MembershipService {

    List<Membership> getUserMemberships(User user, int page);

    int getTotalUserMembershipsPages(User user);

    Membership createMembership(Membership.Builder builder);

    void deleteMembership(long id);

}
