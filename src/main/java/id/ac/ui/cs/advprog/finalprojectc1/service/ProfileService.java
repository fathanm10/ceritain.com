package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.core.appuser.AppUser;
import id.ac.ui.cs.advprog.finalprojectc1.model.Profile;

public interface ProfileService {

    Profile saveProfileToDB(String firstName, String lastName,
                                   String email, String username, String bio, String linkPhoto);
    Profile saveNewProfile();
    boolean isUsernameUnique(String username);
    Profile getProfileById(int id);
    Profile getProfileByEmail(String email);
    Profile getProfileByEmail();
    String getUsernameLogin();
    AppUser getAppuser();
}
