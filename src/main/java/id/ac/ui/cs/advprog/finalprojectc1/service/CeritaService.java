package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;

import java.util.List;

public interface CeritaService {

    Cerita createCerita(String judul, String isi);

    Cerita getCeritaById(String id);

    List<Cerita> getAllCerita();

    List<Cerita> getAllUserCerita();

    Cerita updateCerita(String id, String judulOpt, String isiOpt);

    void deleteCerita(String id);

    void deleteCeritaFromAllReadingList(String ceritaId);
}
