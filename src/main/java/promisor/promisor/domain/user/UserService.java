package promisor.promisor.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.user.dto.SignUpDto;
import promisor.promisor.infra.email.EmailValidator;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

//      TODO: userRepository DI 필요한지 고려해서 삭제 결정
    private final UserRepository userRepository; // 삭제 생각해 봐야함
    private final UserDAO userDao;
    private final EmailValidator emailValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일의 사용자를 찾을수 없습니다."));
    }

    public String register(SignUpDto request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

//        TODO: 예외발생 처리 더 나은 방법으로 수정하기
        if (!isValidEmail) {
            throw new IllegalStateException("유효하지 않은 이메일입니다.");
        }

        String token = signUpUser(
                new User(
                        request.getName(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getTelephone(),
                        UserRole.USER
                )
        );
        return token;
    }

    public String signUpUser(User user) {
        boolean userExists = userDao
                .findByEmail(user.getEmail())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setEncodedPassword(encodedPassword);
        return "";
    }
}
