package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipServiceImpl implements MembershipService {

    @Override
    public List<Membership> getBandMemberships(User band, int page) {
        return null;
    }

    @Override
    public int getTotalBandMembershipsPages(User band) {
        return 0;
    }

    @Override
    public void addMember(Membership.Builder builder) {

    }

    @Override
    public void deleteMembership(Membership membership) {

    }

    @Override
    public List<Membership> getArtistMemberships(User artist, int page) {
        return null;
    }

    @Override
    public int getTotalArtistMembershipsPages(User band) {
        return 0;
    }
}
