package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.MembershipState;
import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.*;
import ar.edu.itba.paw.persistence.MembershipDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    private MembershipDao membershipDao;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private MailingService mailingService;
    @Autowired
    private AuthFacadeService authFacadeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MembershipServiceImpl.class);

    private List<Membership> getUserMemberships(User user, MembershipState state, int page) {
        LOGGER.info("Getting specified user memberships");
        return membershipDao.getUserMembershipsByState(user, state, page);
    }

    @Override
    public List<Membership> getBandMemberships(User user, MembershipState state, int page) {
        if(!user.isBand())
            throw new MembershipNotFoundException();
        int lastPage = getTotalUserMembershipsPages(user, state);
        checkPage(page, lastPage);
        return getUserMemberships(user,state,page);
    }

    @Override
    public List<Membership> getBandMembershipsPreview(User user) {
        if(!user.isBand())
            throw new MembershipNotFoundException();
        return membershipDao.getBandMembershipsPreview(user);
    }

    @Override
    public List<Membership> getArtistMemberships(User user, MembershipState state, int page) {
        if(user.isBand())
            throw new MembershipNotFoundException();
        int lastPage = getTotalUserMembershipsPages(user, state);
        checkPage(page, lastPage);
        return getUserMemberships(user,state,page);
    }

    @Override
    public int getTotalUserMembershipsPages(User user, MembershipState state) {
        LOGGER.info("Getting total specified user memberships");
        return membershipDao.getTotalUserMembershipsByStatePages(user,state);
    }

    @Transactional
    @Override
    public Membership createMembershipInvite(Membership.Builder builder) {
        if(builder.getArtist().isBand() || !builder.getArtist().isAvailable()) {
            throw new UserNotAvailableException();
        }
        // TODO: mandar mail de invitación
        /*
        Locale locale = LocaleContextHolder.getLocale();
        LocaleContextHolder.setLocale(locale, true);
        mailingService.sendInviteEmail(builder.getBand(), builder.getArtist().getEmail(), locale);
         */
        return createMembership(builder.state(MembershipState.PENDING));
    }

    private Membership createMembership(Membership.Builder builder) {
        if(membershipDao.membershipExists(builder.getBand(), builder.getArtist()))
            throw new DuplicateMembershipException();
        LOGGER.info("Creating new membership");
        return membershipDao.createMembership(builder);
    }

    @Transactional
    @Override
    public void deleteMembership(long id) {
        checkMembershipId(id);
        checkPermissions(id);
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
        checkMembershipId(id);
        return membershipDao.getMembershipById(id);
    }

    @Transactional
    @Override
    public void createMembershipByApplication(Membership.Builder builder, long auditionId, long applicationId) {
        applicationService.select(auditionId, applicationId);
        createMembership(builder.state(MembershipState.ACCEPTED));
        Locale locale = LocaleContextHolder.getLocale();
        LocaleContextHolder.setLocale(locale, true);
        mailingService.sendAddedToBandEmail(builder.getBand(), builder.getArtist().getEmail(), locale);
    }

    @Override
    public int getPendingMembershipsCount(User user) {
        //TODO CHEQUEAR USER???????
        return membershipDao.getPendingMembershipsCount(user);
    }

    @Transactional
    @Override
    public void editMembershipById(String description, Set<Role> roles, long id) {
        checkMembershipId(id);
        checkPermissions(id);
        Optional<Membership> membership = getMembershipById(id);
        membership.ifPresent(value -> value.edit(description, roles));
    }

    private void checkPermissions(long id) {
        if(!Objects.equals(getMembershipById(id).orElseThrow(MembershipNotFoundException::new).getBand().getId(), authFacadeService.getCurrentUser().getId())) {
            LOGGER.warn("The authenticated user is not the band owner");
            throw new BandNotOwnedException();
        }
    }

    private void checkPage(int page, int lastPage) {
        if(page <= 0)
            throw new IllegalArgumentException();
        if(page > lastPage)
            throw new PageNotFoundException();
    }

    private void checkMembershipId(long id) {
        if(id < 0)
            throw new IllegalArgumentException();
    }
}
