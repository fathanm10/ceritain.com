package id.ac.ui.cs.advprog.finalprojectc1.repository;

import id.ac.ui.cs.advprog.finalprojectc1.core.ReadingList;
import id.ac.ui.cs.advprog.finalprojectc1.core.token.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingListRepository extends JpaRepository<ReadingList,Integer> {
}
