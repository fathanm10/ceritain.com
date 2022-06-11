package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.core.appuser.AppUser;
import id.ac.ui.cs.advprog.finalprojectc1.model.Profile;
import id.ac.ui.cs.advprog.finalprojectc1.repository.AppUserRepository;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    public Profile getProfileById(int id){
        return profileRepository.getById(id);
    }

    public Profile getProfileByEmail(String email){
        for (Profile p: profileRepository.findAll()){
            if (p.getEmail().equals(email)) return p;
        }
        return null;
    }

    public Profile getProfileByEmail(){
        String email = getAppuser().getEmail();
        return getProfileByEmail(email);
    }

    public String getUsernameLogin(){
        String res = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            res = username;
        } else {
            String username = principal.toString();
            res = username;
        }
        return res;
    }

    public AppUser getAppuser(){
        String email = getUsernameLogin();
        Optional<AppUser> appUser = appUserRepository.findByEmail(email);
        if (appUser.isPresent()) return appUser.get();
        return null;
    }

    public Profile saveProfileToDB(String firstName, String lastName,
                                   String email, String username, String bio, String linkPhoto){

        Profile profile = getProfileByEmail(email);

        if (profile != null) {
            profile.setFirstName(firstName);
            profile.setLastName(lastName);
            if (isUsernameUnique(username)) {
                profile.setUsername(username);
            }
            profile.setBio(bio);
            profile.setUrl("http://ceritain.com/@" + username);
            profile.setLinkPhoto(linkPhoto);
            profileRepository.save(profile);
            return profile;
        }

        return profile;
    }

    public Profile saveNewProfile(){
        String email = getAppuser().getUsername();
        String fullName = getAppuser().getFullname();
        String username = getAppuser().getName();
        Profile profile = getProfileByEmail(email);

        String[] name = fullName.split("\\s+");
        if (name.length != 0){
            profile.setFirstName(name[0]);
            if (name.length > 1){
                StringBuilder lastName = new StringBuilder();
                for (int i = 1; i < name.length; ++i){
                    if (i != name.length-1){
                        lastName.append(name[i] + " ");
                    } else lastName.append(name[i] + " ");
                }
                profile.setLastName(lastName.toString());
            }
        }

        profile.setEmail(email);
        profile.setUsername(username);
        profile.setUrl("http://ceritain.com/@"+username);

        profileRepository.save(profile);
        return profile;

    }

    public boolean isUsernameUnique(String username){
        boolean check = true;

        for (Profile p: profileRepository.findAll()){
            if (p.getUsername().equals(username)) {
                check = false;
                return check;
            }
        }
        return check;
    }

}
