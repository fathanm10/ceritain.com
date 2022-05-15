package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import id.ac.ui.cs.advprog.finalprojectc1.repository.SearchPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchPageService {
    @Autowired
    private SearchPageRepository searchPageRepository;

    public List<Cerita> findAllCerita(String keyword) {
        if (keyword != null) {
            return searchPageRepository.search(keyword);
        }
        return searchPageRepository.findAll();
    }
}

