package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.core.ReadingList;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadingListService {

    @Autowired
    ReadingListRepository readingListRepository;

    public ReadingList createReadingList(String judul, String deskripsi) {
        var readinglist = ReadingList.builder()
                .judul(judul)
                .deskripsi(deskripsi)
                .build();
        return readingListRepository.save(readinglist);
    }


    public ReadingList getReadingListById(int readinglistId) {
        return readingListRepository.getById(readinglistId);
    }
}
