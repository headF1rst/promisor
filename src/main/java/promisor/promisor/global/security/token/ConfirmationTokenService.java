package promisor.promisor.global.security.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConfirmationTokenService {

    private final ConfirmationTokenDAO confirmationTokenDAO;

    @Transactional
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenDAO.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenDAO.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenDAO.updateConfirmedAt(token, LocalDateTime.now());
    }


}
