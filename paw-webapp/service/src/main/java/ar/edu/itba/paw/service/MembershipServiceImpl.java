package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.MembershipState;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.MembershipDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    private MembershipDao membershipDao;
    @Autowired
    private UserService userService;
    // TODO: CHEQUEOS DE OWNER?

    private static final Logger LOGGER = LoggerFactory.getLogger(MembershipServiceImpl.class);

    @Override
    public List<Membership> getUserMemberships(User user, MembershipState state, int page) {
        LOGGER.info("Getting specified user memberships");
        return membershipDao.getUserMembershipsByState(user, state, page);
    }

    @Override
    public int getTotalUserMembershipsPages(User user, MembershipState state) {
        LOGGER.info("Getting total specified user memberships");
        return membershipDao.getTotalUserMembershipsByStatePages(user,state);
    }

    @Override
    public Membership createMembership(Membership.Builder builder) {
        // TODO: CHEQUEAR QUE NO EXISTA
        LOGGER.info("Creating new membership");
        return membershipDao.createMembership(builder);
    }

    @Override
    public void deleteMembership(long id) {
        LOGGER.info("Deleting membership with id: {}", id);
        membershipDao.deleteMembership(id);
    }

    @Transactional
    @Override
    public void changeState(Membership membership, MembershipState state) {
        membership.setState(state);
    }

    @Override
    public Optional<Membership> getMembershipById(long id) {
        return membershipDao.getMembershipById(id);
    }
}
