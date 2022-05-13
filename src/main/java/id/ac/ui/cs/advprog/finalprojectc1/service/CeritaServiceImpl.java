package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import id.ac.ui.cs.advprog.finalprojectc1.repository.CeritaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CeritaServiceImpl implements CeritaService {

    @Autowired
    CeritaRepository ceritaRepository;

    @Override
    public Cerita createCerita(String judul, String isi) {
        Cerita cerita = Cerita.builder().judulCerita(judul).isiCerita(isi).build();
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
        return ceritaRepository.save(cerita);
    };

    @Override
    public void deleteCerita(String id) {ceritaRepository.deleteById(id);}

}
