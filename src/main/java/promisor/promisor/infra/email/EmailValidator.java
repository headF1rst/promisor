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

        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(email).matches()) {
            return true;
        } else{
            return false;
        }
    }
}
