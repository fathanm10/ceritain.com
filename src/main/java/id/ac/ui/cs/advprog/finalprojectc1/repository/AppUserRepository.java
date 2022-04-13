package id.ac.ui.cs.advprog.finalprojectc1.repository;

import id.ac.ui.cs.advprog.finalprojectc1.core.appuser.AppUser;
import id.ac.ui.cs.advprog.finalprojectc1.core.appuser.AppUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository {
    Optional<AppUser> findByEmail(String email);
}
