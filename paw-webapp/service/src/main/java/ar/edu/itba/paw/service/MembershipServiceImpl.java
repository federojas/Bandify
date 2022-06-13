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

    @Override
    public List<Membership> getUserMemberships(User user, MembershipState state, int page) {
        int lastPage = getTotalUserMembershipsPages(user, state);
        lastPage = lastPage == 0 ? 1 : lastPage;
        checkPage(page, lastPage);
        LOGGER.info("Getting specified user memberships");
        return membershipDao.getUserMembershipsByState(user, state, page);
    }

    @Override
    public List<Membership> getUserMembershipsPreview(User user) {
        return membershipDao.getUserMembershipsPreview(user);
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
        User currentUser = authFacadeService.getCurrentUser();
        Long currentUserId = currentUser.getId();
        if (currentUserId.equals(membership.getBand().getId())
                || currentUserId.equals(membership.getArtist().getId())) {
            if (!membership.getState().equals(MembershipState.PENDING)) {
                throw new MembershipNotFoundException();
            }
            membership.setState(state);
        } else {
            throw new MembershipNotOwnedException();
        }

    }

    @Override
    public Optional<Membership> getMembershipById(long id) {
        checkMembershipId(id);
        return membershipDao.getMembershipById(id);
    }

    @Override
    public Optional<Membership> getMembershipByUsers(User band, User artist) {
        if(!band.isBand() || artist.isBand())
            throw new IllegalArgumentException();
        return membershipDao.getMembershipByUsers(band, artist);
    }

    @Transactional
    @Override
    public boolean createMembershipByApplication(Membership.Builder builder, long auditionId) {
        if(membershipDao.membershipExists(builder.getBand(), builder.getArtist())) {
            LOGGER.info("User {} already in band ", builder.getArtist().getId());
            return false;
        }
        applicationService.select(auditionId, builder.getArtist().getId());
        createMembership(builder.state(MembershipState.ACCEPTED));
        Locale locale = LocaleContextHolder.getLocale();
        LocaleContextHolder.setLocale(locale, true);
        mailingService.sendAddedToBandEmail(builder.getBand(), builder.getArtist().getEmail(), locale);
        return true;
    }

    @Override
    public int getPendingMembershipsCount(User user) {
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
        Membership membership = getMembershipById(id).orElseThrow(MembershipNotFoundException::new);
        User current = authFacadeService.getCurrentUser();
        if(current.isBand()) {
            if(!Objects.equals(membership.getBand().getId(), current.getId())) {
                LOGGER.warn("The authenticated user is not the band owner");
                throw new BandNotOwnedException();
            }
        } else {
            if(!Objects.equals(membership.getArtist().getId(), current.getId())) {
                LOGGER.warn("The authenticated user is not in the band");
                throw new UserNotInBandException();
            }
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

    @Override
    public boolean canBeAddedToBand(User band, User artist) {
        return !membershipDao.membershipExists(band, artist) && artist.isAvailable();
    }
}
