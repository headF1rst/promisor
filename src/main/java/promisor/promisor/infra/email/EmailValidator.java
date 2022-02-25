package promisor.promisor.infra.email;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 * Email 검증
 *
 * @author Sanha Ko
 */
@Service
public class EmailValidator implements Predicate<String> {


    @Override
    public boolean test(String s) {
//        TODO: Regex to validate email
        return true;
    }
}
