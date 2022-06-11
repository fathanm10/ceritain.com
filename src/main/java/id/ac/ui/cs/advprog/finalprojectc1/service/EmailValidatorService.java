package id.ac.ui.cs.advprog.finalprojectc1.service;

import org.springframework.stereotype.Service;
import java.util.function.Predicate;
import java.util.regex.Pattern;



@Service
public class EmailValidatorService implements Predicate<String> {

    @Override
    public boolean test(String s) {
        var emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        var pat = Pattern.compile(emailRegex);
        return pat.matcher(s).matches();

    }
}
