package id.ac.ui.cs.advprog.finalprojectc1.repository;

import id.ac.ui.cs.advprog.finalprojectc1.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Profile findById(int id);
}
