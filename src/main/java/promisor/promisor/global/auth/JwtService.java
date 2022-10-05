package promisor.promisor.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.domain.Member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtService {

    private final JwtProvider jwtProvider;

    @Transactional
    public ProfileTokenResponse createToken(Member member, boolean isAdmin) {
        String token = jwtProvider.createAccessToken(member.getEmail());
        return new ProfileTokenResponse(member, token, isAdmin);
    }

    public String findEmailByToken(String token) {
        return jwtProvider.extractEmail(token);
    }

    public boolean validateJwtToken(String authentication) {
        return jwtProvider.validateJwtToken(authentication);
    }
}
