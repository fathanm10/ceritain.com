package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.core.appuser.AppUser;
import id.ac.ui.cs.advprog.finalprojectc1.model.Profile;
import id.ac.ui.cs.advprog.finalprojectc1.repository.AppUserRepository;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private AppUserRepository appUserRepository;

//    public void saveProfileToDB(MultipartFile file, String firstName, String lastName,
//                                String email, String username, String bio){
    public int saveProfileToDB(String firstName, String lastName,
            String email, String username, String bio, String linkPhoto){
        Profile profile = new Profile();
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        if (fileName.contains("..")){
//            System.out.println("not a valid file");
//        }
//        try {
//            profile.setPhoto(Base64.getEncoder().encodeToString(file.getBytes()));
//        } catch (IOException e){
//            e.printStackTrace();
//        }

        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setEmail(email);
        profile.setUsername(username);
        profile.setBio(bio);
        profile.setUrl("http://ceritain.com/@"+username);
        profile.setLinkPhoto(linkPhoto);

        profileRepository.save(profile);
        return profile.getId();
    }

    public Profile getProfileById(int id){
        return profileRepository.getById(id);
    }

    public Profile getUserByEmail(String email){
        List<Profile> lst = profileRepository.findAll();

        for (Profile i: lst){
            if (i.getEmail().equals(email)){
                return i;
            }
        }
        return null;
    }

    public String getUsernameLogin(){
        String res = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            System.out.println(username);
            res = username;
        } else {
            String username = principal.toString();
            System.out.println(username);
            res = username;
        }
        return res;
    }

    public AppUser getAppuser(){
        String email = getUsernameLogin();
        Optional<AppUser> appUser = appUserRepository.findByEmail(email);
        return appUser.get();
    }
}
