package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import id.ac.ui.cs.advprog.finalprojectc1.repository.CeritaRepository;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CeritaServiceImpl implements CeritaService {

    @Autowired
    CeritaRepository ceritaRepository;

    @Autowired
    ReadingListRepository readingListRepository;

    @Override
    public Cerita createCerita(String judul, String isi) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        Cerita cerita = Cerita.builder().judulCerita(judul).isiCerita(isi).creator(username).build();
        cerita.setCreatedAt(new Date());
        return ceritaRepository.save(cerita);
    };

    @Override
    public Cerita getCeritaById(String id) {
        return ceritaRepository.getById(id);
    };

    @Override
    public List<Cerita> getAllCerita() {
        return ceritaRepository.findAll();
    };

    @Override
    public List<Cerita> getAllUserCerita() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = ((UserDetails)principal).getUsername();

        List<Cerita> allCerita = ceritaRepository.findAll();
        List<Cerita> userCeritas = allCerita.stream()
                .filter(p -> p.getCreator().equals(currentUsername)).collect(Collectors.toList());
        return userCeritas;
    };

    @Override
    public Cerita updateCerita(String id, Optional<String> judulOpt, Optional<String> isiOpt) {
        Cerita cerita = getCeritaById(id);
        judulOpt.ifPresent(cerita::setJudulCerita);
        isiOpt.ifPresent(cerita::setIsiCerita);
        cerita.setUpdatedAt(new Date());
        return ceritaRepository.save(cerita);
    };

    @Override
    public void deleteCerita(String id) {ceritaRepository.deleteById(id);}

    @Override
    public void deleteCeritaFromAllReadingList(String ceritaId) {
        var readingListAll = readingListRepository.findAll();
        var cerita = getCeritaById(ceritaId);
        readingListAll.forEach(readingList -> {
            readingList.removeCerita(cerita);
            readingListRepository.save(readingList);
        });
    }
}
