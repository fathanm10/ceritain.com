package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;

import java.util.List;

public interface SearchPageService {
    public Cerita findCeritaById(String id);

    public List<Cerita> findAllCerita(String keyword);

    public Cerita findCeritaByName(String name);

    public void addCerita(Cerita cerita);

    public void deleteCerita(String id);
}
