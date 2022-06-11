package id.ac.ui.cs.advprog.finalprojectc1.repository;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchPageRepository extends JpaRepository <Cerita, String>{
    @Query(value = "SELECT * From Cerita WHERE cerita_judul LIKE %:keyword%" + " OR cerita_isi LIKE %:keyword%", nativeQuery = true)
    List<Cerita> findByKeyword(@Param("keyword") String keyword);

}

