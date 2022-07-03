package promisor.promisor.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import promisor.promisor.domain.bandate.service.BanDateService;
import promisor.promisor.domain.member.service.MemberService;
import promisor.promisor.domain.member.service.RelationService;
import promisor.promisor.domain.promise.service.PromiseService;
import promisor.promisor.domain.team.service.TeamService;
import promisor.promisor.global.auth.JwtService;

@ActiveProfiles("test")
public abstract class ControllerTest {

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected TeamService teamService;

    @MockBean
    protected PromiseService promiseService;

    @MockBean
    protected RelationService relationService;

    @MockBean
    protected BanDateService banDateService;

    @MockBean
    protected JwtService jwtService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
