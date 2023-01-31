package promisor.promisor.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.domain.MemberRole;
import promisor.promisor.domain.member.domain.RefreshToken;
import promisor.promisor.domain.member.dao.RefreshTokenRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.dto.request.LoginRequest;
import promisor.promisor.domain.member.dto.request.ModifyMemberRequest;
import promisor.promisor.domain.member.dto.request.SignUpRequest;
import promisor.promisor.domain.member.dto.response.*;
import promisor.promisor.domain.member.exception.*;
import promisor.promisor.global.auth.JwtProvider;
import promisor.promisor.global.token.exception.InvalidTokenException;
import promisor.promisor.global.token.exception.TokenExpiredException;
import promisor.promisor.global.token.ConfirmationToken;
import promisor.promisor.global.token.ConfirmationTokenService;
import promisor.promisor.global.token.exception.TokenNotExistException;
import promisor.promisor.infra.email.EmailSender;
import promisor.promisor.infra.email.exception.EmailConfirmedException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public String save(SignUpRequest request) {
        Member member = Member.of(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getTelephone(),
                MemberRole.valueOf(request.getMemberRole())
        );
        String imageUrl = request.getImageUrl();
        member.setImageUrl(imageUrl);
        String token = signUpUser(member);
        String link = "http://localhost:8080/members/confirm?token=" + token;
        emailSender.send(request.getEmail(), emailSender.buildEmail(request.getName(), link));
        return token;
    }

    @Transactional
    public String signUpUser(Member member) {
        Optional<Member> userExists = memberRepository.findByEmail(member.getEmail());

        if (userExists.isPresent()) {
            throw new EmailDuplicatedException(member.getEmail());
        }
        String password = member.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        member.setEncodedPassword(encodedPassword);

        memberRepository.save(member);
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = ConfirmationToken.of(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                member
                );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    @Transactional
    public MemberResponse confirmToken(String token) {
        if (token.isEmpty()) {
            throw new TokenNotExistException();
        }
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(InvalidTokenException::new);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailConfirmedException();
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException();
        }
        Member member = confirmationToken.getMember();
        String email = member.getEmail();
        confirmationTokenService.setConfirmedAt(token);
        memberRepository.enableMember(email);
        return new MemberResponse(member);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        TokenResponse createToken = createTokenReturn(loginRequest);
        Long tokenExpireTime = jwtProvider.getTokenExpireTime(createToken.getAccessToken());
        return new LoginResponse(
                createToken.getAccessToken(),
                createToken.getRefreshId(),
                tokenExpireTime);
    }

    public LoginResponse refreshToken(LoginRequest.GetRefreshTokenDto getRefreshTokenDto) {
        Long refreshId = getRefreshTokenDto.getRefreshId();
        String refreshToken = refreshTokenRepository.findRefreshTokenById(refreshId);
        if (jwtProvider.validateJwtToken(refreshToken)) {
            String email = jwtProvider.extractEmail(refreshToken);
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail(email);

            TokenResponse createToken = createTokenReturn(loginRequest);
            Long tokenExpireTime = jwtProvider.getTokenExpireTime(createToken.getAccessToken());

            return new LoginResponse(
                    createToken.getAccessToken(),
                    createToken.getRefreshId(),
                    tokenExpireTime);
        } else {
            throw new LoginAgainException();
        }
    }

    private TokenResponse createTokenReturn(LoginRequest loginRequest) {

        String accessToken = jwtProvider.createAccessToken(loginRequest.getEmail());
        String refreshToken = jwtProvider.createRefreshToken(loginRequest.getEmail()).get("refreshToken");
        String refreshTokenExpirationAt = jwtProvider.createRefreshToken(loginRequest.getEmail()).get("refreshTokenExpirationAt");

        RefreshToken insertRefreshToken = new RefreshToken(
                loginRequest.getEmail(),
                accessToken,
                refreshToken,
                refreshTokenExpirationAt
        );
        refreshTokenRepository.save(insertRefreshToken);
        return new TokenResponse(accessToken, insertRefreshToken.getId());
    }


    @Transactional
    public ModifyMemberResponse modifyInfo(String email , @Valid ModifyMemberRequest modifyMemberRequest){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Member member = optionalMember.orElseThrow(MemberNotFoundException::new);
        member.modifyMemberInfo(modifyMemberRequest.getName(), modifyMemberRequest.getImageUrl(), modifyMemberRequest.getLocation());
        return new ModifyMemberResponse(
                member.getName(),
                member.getImageUrl(),
                member.getLocation()
        );
    }

    public ProfileMemberResponse getInfo(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        return new ProfileMemberResponse(member);
    }
}
