package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CeritaService {

    Cerita createCerita(String judul, String isi);

    Cerita getCeritaById(String id);

    List<Cerita> getAllCerita();

    Cerita updateCerita(String id, Optional<String> judulOpt, Optional<String> isiOpt);

    void deleteCerita(String id);

    void deleteCeritaFromAllReadingList(String ceritaId);
}
