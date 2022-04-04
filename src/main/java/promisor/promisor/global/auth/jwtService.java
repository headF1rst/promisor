package promisor.promisor.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.global.config.security.JwtProvider;

@Service
@RequiredArgsConstructor
public class jwtService {

    private final JwtProvider jwtProvider;

    public ProfileTokenResponse createToken(Member member, boolean isAdmin) {
        String token = jwtProvider.createAccessToken(member.getEmail());
        return new ProfileTokenResponse(member, token, isAdmin);
    }

    public String findEmailByToken(String token) {
        return jwtProvider.extractEmail(token);
    }
}
