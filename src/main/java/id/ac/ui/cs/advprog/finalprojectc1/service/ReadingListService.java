package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.core.ReadingList;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadingListService {

    @Autowired
    ReadingListRepository readingListRepository;

    public ReadingList createReadingList(String judul, String deskripsi) {
        var readinglist = ReadingList.builder()
                .judul(judul)
                .deskripsi(deskripsi)
                .build();
        System.out.println("createReadingList:");
        loggerReadingList(readinglist.getId(),judul,deskripsi);
        return readingListRepository.save(readinglist);
    }

    public ReadingList getReadingListById(int readinglistId) {
        return readingListRepository.getById(readinglistId);
    }

    public ReadingList updateReadingList(int readinglistId, String judul, String deskripsi) {
        var readingList = getReadingListById(readinglistId);
        readingList.setJudul(judul);
        readingList.setDeskripsi(deskripsi);
        System.out.println("updateReadingList:");
        loggerReadingList(readinglistId,judul,deskripsi);
        return readingListRepository.save(readingList);
    }

    public void deleteReadingList(int readinglistId) {
        var readingList = getReadingListById(readinglistId);
        System.out.println("deleteReadingList:");
        loggerReadingList(readinglistId,readingList.getJudul(),readingList.getDeskripsi());
        readingListRepository.delete(readingList);
    }

    public List<ReadingList> getAllReadingList() {
        return readingListRepository.findAll();
    }

    public void loggerReadingList(int readinglistId, String judul, String deskripsi) {
        System.out.println(readinglistId);
        System.out.println("Judul: "+judul);
        System.out.println("Deskripsi: "+deskripsi);
        System.out.println();
    }
}
