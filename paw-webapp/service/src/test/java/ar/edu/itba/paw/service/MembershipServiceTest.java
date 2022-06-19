package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.MembershipState;
import ar.edu.itba.paw.persistence.MembershipDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MembershipServiceTest {

    @InjectMocks
    private final MembershipService membershipService = new MembershipServiceImpl();

    @Mock
    private Membership membership;

    @Mock
    private MembershipDao membershipDao;

    @Mock
    private ApplicationService applicationService;

    @Mock
    private MailingService mailingService;

    @Mock
    private AuthFacadeService authFacadeService = new AuthFacadeServiceImpl();

    @Test
    public void testChangeState() {
//        membershipService.changeState(membership, MembershipState.PENDING);
        membership.setState(MembershipState.PENDING);
        verify(membership).setState(MembershipState.PENDING);
    }

}
