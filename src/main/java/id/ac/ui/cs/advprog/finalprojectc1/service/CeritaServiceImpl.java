package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import id.ac.ui.cs.advprog.finalprojectc1.repository.CeritaRepository;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CeritaServiceImpl implements CeritaService {

    @Autowired
    CeritaRepository ceritaRepository;

    @Autowired
    ReadingListRepository readingListRepository;

    @Override
    public Cerita createCerita(String judul, String isi) {
        if (judul.equals("") || isi.equals("")) {
            throw new IllegalArgumentException("One of the field cannot be empty!");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        var cerita = Cerita.builder().judulCerita(judul).isiCerita(isi).creator(username).build();
        cerita.setCreatedAt(new Date());
        return ceritaRepository.save(cerita);
    }

    @Override
    public Cerita getCeritaById(String id) {
        return ceritaRepository.getById(id);
    }

    @Override
    public List<Cerita> getAllCerita() {
        return ceritaRepository.findAll();
    }

    @Override
    public List<Cerita> getAllUserCerita() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = ((UserDetails) principal).getUsername();

        List<Cerita> allCerita = ceritaRepository.findAll();
        return allCerita.stream()
                .filter(p -> p.getCreator().equals(currentUsername)).collect(Collectors.toList());
    }

    @Override
    public Cerita updateCerita(String id, String judulOpt, String isiOpt) {
        var cerita = getCeritaById(id);
        cerita.setJudulCerita(judulOpt);
        cerita.setIsiCerita(isiOpt);
        cerita.setUpdatedAt(new Date());
        return ceritaRepository.save(cerita);
    }

    @Override
    public void deleteCerita(String id) {
        ceritaRepository.deleteById(id);
    }

    @Override
    public void deleteCeritaFromAllReadingList(String ceritaId) {
        var readingListAll = readingListRepository.findAll();
        var cerita = getCeritaById(ceritaId);
        readingListAll.forEach(readingList -> {
            Set<Cerita> ceritaSet = readingList.getCeritaSet();
            ceritaSet.remove(cerita);
            readingList.setCeritaSet(ceritaSet);
            readingListRepository.save(readingList);
        });
    }
}
