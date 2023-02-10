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
    private RoleService roleService;
    @Autowired
    private AuthFacadeService authFacadeService;

    private static final int MAX_INV_ROLES = 5;
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
    public List<Membership> getUserMemberships(User user, int page) {
        int lastPage = getTotalUserMembershipsPages(user);
        lastPage = lastPage == 0 ? 1 : lastPage;
        checkPage(page, lastPage);
        LOGGER.info("Getting specified user memberships");
        return membershipDao.getUserMemberships(user, page);
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
    @Override
    public int getTotalUserMembershipsPages(User user) {
        LOGGER.info("Getting total specified user memberships");
        return membershipDao.getTotalUserMembershipsByStatePages(user);
    }

    @Transactional
    @Override
    public Membership createMembershipInvite(Membership.Builder builder) {
        if(builder.getArtist().isBand() || !builder.getArtist().isAvailable()) {
            throw new UserNotAvailableException();
        }
        if(builder.getRoles().size() > MAX_INV_ROLES)
            throw new IllegalArgumentException("Invalid amount of roles");
        Locale locale = LocaleContextHolder.getLocale();
        LocaleContextHolder.setLocale(locale, true);
        mailingService.sendNewInvitationEmail(builder.getBand(), builder.getArtist().getEmail(), locale);
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
    public Membership changeState(long id, MembershipState state) {
        Membership membership = getMembershipById(id).orElseThrow(MembershipNotFoundException::new);
        User currentUser = authFacadeService.getCurrentUser();
        Long currentUserId = currentUser.getId();
        if (currentUserId.equals(membership.getBand().getId())
                || currentUserId.equals(membership.getArtist().getId())) {
            if(membership.getState() != MembershipState.PENDING)
                throw new IllegalArgumentException("Membership is not pending");
            membership.setState(state);
            if(state == MembershipState.ACCEPTED) {
                applicationService.closeApplications(membership.getBand().getId(), membership.getArtist().getId());
                Locale locale = LocaleContextHolder.getLocale();
                LocaleContextHolder.setLocale(locale, true);
                mailingService.sendInvitationAcceptedEmail(membership.getArtist(), membership.getBand().getEmail(), locale);
            }
        } else {
            throw new MembershipNotOwnedException();
        }
        return membership;
    }

    @Override
    public Optional<Membership> getMembershipById(long id) {
        checkMembershipId(id);
        return membershipDao.getMembershipById(id);
    }

    @Override
    public List<Membership> getMembershipsByUsers(User band, User artist) {
        if(!band.isBand())
            throw new NotABandException();
        if(artist.isBand())
            throw new NotAnArtistException();
        return membershipDao.getMembershipsByUsers(band, artist);
    }

    @Transactional
    @Override
    public boolean createMembershipByApplication(Membership.Builder builder, long auditionId) {
        if(membershipDao.membershipExists(builder.getBand(), builder.getArtist())) {
            LOGGER.info("User {} already in band ", builder.getArtist().getId());
            return false;
        }
        applicationService.select(auditionId, builder.getBand(), builder.getArtist().getId());
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
    public Membership editMembershipById(String description, List<String> roles, long id) {
        checkMembershipId(id);
        checkPermissions(id);
        Optional<Membership> membership = getMembershipById(id);
        if (membership.isPresent()) {
            Membership memRet = membership.get();
            Set<Role> roleSet = roleService.getRolesByNames(roles);
            memRet.edit(description, roleSet);
            return memRet;
        }
        throw new MembershipNotFoundException();
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
        if(band != null && band.isBand())
            return !membershipDao.membershipExists(band, artist) && artist.isAvailable();
        return false;
    }

    @Override
    public boolean isInBand(User band, User artist) {
        if(band.isBand() && artist != null)
            return membershipDao.isInBand(band, artist);
        return false;
    }
}
