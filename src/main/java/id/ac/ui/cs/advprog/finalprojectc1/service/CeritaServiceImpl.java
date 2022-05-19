package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import id.ac.ui.cs.advprog.finalprojectc1.repository.CeritaRepository;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CeritaServiceImpl implements CeritaService {

    @Autowired
    CeritaRepository ceritaRepository;

    @Autowired
    ReadingListRepository readingListRepository;

    @Override
    public Cerita createCerita(String judul, String isi) {
        Cerita cerita = Cerita.builder().judulCerita(judul).isiCerita(isi).build();
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
