package id.ac.ui.cs.advprog.finalprojectc1.service;


import id.ac.ui.cs.advprog.finalprojectc1.core.appuser.AppUser;
import id.ac.ui.cs.advprog.finalprojectc1.core.token.ConfirmationToken;
import id.ac.ui.cs.advprog.finalprojectc1.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {


    private final AppUserRepository appUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private  final  ConfirmationTokenService confirmationTokenService;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user with email " + email + " not found"));

    }

    public  String signUpUser(AppUser appUser){

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        var token = UUID.randomUUID().toString();
        var confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
                                                                    LocalDateTime.now().plusMinutes(15),appUser);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public void enableAppUser(String email) {
        appUserRepository.enableAppUser(email);
    }
}
