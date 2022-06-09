package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.ApplicationDao;
import ar.edu.itba.paw.persistence.MembershipDao;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    private MembershipDao membershipDao;
    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MembershipServiceImpl.class);

    @Override
    public List<Membership> getUserMemberships(User user, int page) {
        LOGGER.info("Getting specified band memberships");
        return membershipDao.getUserMemberships(user, page);
    }

    @Override
    public int getTotalUserMembershipsPages(User user) {
        LOGGER.info("Getting total specified band memberships");
        return membershipDao.getTotalUserMembershipsPages(user);
    }

    @Override
    public Membership createMembership(Membership.Builder builder) {
        LOGGER.info("Creating new membership");
        return membershipDao.createMembership(builder);
    }

    @Override
    public void deleteMembership(long id) {
        LOGGER.info("Deleting membership with id: {}", id);
        membershipDao.deleteMembership(id);
    }
}
