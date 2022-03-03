package promisor.promisor.infra.email;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Email 검증
 *
 * @author Sanha Ko
 */
@Service
public class EmailValidator implements Predicate<String> {


    @Override
    public boolean test(String email) {

        Pattern pattern = Pattern.compile("\"^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\\\.[a-zA-Z0-9-]+)*$\"");

        if (!pattern.matcher(email).matches()) {
            return false;
        }

        return true;
    }
}
