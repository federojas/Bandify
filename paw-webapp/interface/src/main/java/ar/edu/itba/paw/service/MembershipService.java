package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.User;

import java.util.List;

public interface MembershipService {

    List<Membership> getBandMemberships(User band, int page);

    int getTotalBandMembershipsPages(User band);

    void addMember(Membership.Builder builder);

    void deleteMembership(Membership membership);

    List<Membership> getArtistMemberships(User artist, int page);

    int getTotalArtistMembershipsPages(User band);

}
