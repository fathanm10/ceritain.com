package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.Profile;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

//    public void saveProfileToDB(MultipartFile file, String firstName, String lastName,
//                                String email, String username, String bio){
    public int saveProfileToDB(String firstName, String lastName,
            String email, String username, String bio){
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
}
