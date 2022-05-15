package id.ac.ui.cs.advprog.finalprojectc1.repository;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SearchPageRepository extends JpaRepository <Cerita, String>{
    @Query("SELECT judulCerita, isiCerita, createdAt From Cerita WHERE judulCerita LIKE %?1%" + " OR isiCerita LIKE %?1%")
    public List<Cerita> search(String keyword);

}

