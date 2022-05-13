package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import id.ac.ui.cs.advprog.finalprojectc1.repository.SearchPageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class SearchPageServiceImplement implements SearchService {
    @Autowired
    private SearchPageRepository searchPageRepository;

    @Override
    public List<Cerita> findAllCerita(String keyword) {
        if (keyword != null) {
            return searchPageRepository.search(keyword);
        }
        return (List<Cerita>) searchPageRepository.findAll();
    }
}
