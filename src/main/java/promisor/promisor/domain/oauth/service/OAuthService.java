package promisor.promisor.domain.oauth.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.domain.MemberRole;
import promisor.promisor.domain.member.dto.request.LoginRequest;
import promisor.promisor.domain.member.dto.request.SignUpRequest;
import promisor.promisor.domain.member.dto.response.LoginResponse;
import promisor.promisor.domain.member.service.MemberService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public LoginResponse login(String accessToken) {
        String reqURL = "https://kauth.kakao.com/oauth/token";
        LoginRequest loginRequest = null;
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());
            String memberId = element.getAsJsonObject().get("id").getAsString();
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            JsonObject profile = kakaoAccount.get("profile").getAsJsonObject();

            String nickname = profile.get("nickname").getAsString();
            String profileImage = profile.get("profile_image_url").getAsString();
            String email = kakaoAccount.get("email").getAsString();
            String phoneNumber = kakaoAccount.get("phone_number").getAsString();

            String telephone = String.join("", phoneNumber.split("-"));
            String password = memberId + email;

            Optional<Member> member = memberRepository.findByEmail(email);

            if (member.isEmpty()) {
                SignUpRequest signUpRequest =
                        new SignUpRequest(nickname, email, password, telephone, MemberRole.USER.role(), profileImage);
                memberService.save(signUpRequest);
            }
            loginRequest = new LoginRequest(email, password);
            return memberService.login(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert loginRequest != null;
        return memberService.login(loginRequest);
    }
}
