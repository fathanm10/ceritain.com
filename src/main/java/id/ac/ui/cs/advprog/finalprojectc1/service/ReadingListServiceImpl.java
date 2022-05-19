package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.ReadingList;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadingListServiceImpl implements ReadingListService {

    @Autowired
    ReadingListRepository readingListRepository;

    @Autowired
    CeritaService ceritaService;

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

    public ReadingList updateReadingList(int readinglistId, String judul, String deskripsi) {
        var readingList = getReadingListById(readinglistId);
        readingList.setJudul(judul);
        readingList.setDeskripsi(deskripsi);
        return readingListRepository.save(readingList);
    }

    public void deleteReadingList(int readinglistId) {
        var readingList = getReadingListById(readinglistId);
        readingListRepository.delete(readingList);
    }

    public List<ReadingList> getAllReadingList() {
        return readingListRepository.findAll();
    }

    public ReadingList updateCerita(int readinglistId, String ceritaId, String cmd) {
        var cerita = ceritaService.getCeritaById(ceritaId);
        var readingList = getReadingListById(readinglistId);
        var ceritaSet = readingList.getCeritaSet();
        if (cmd.equals("add")) {
            if (ceritaSet.contains(cerita)) {
                return null;
            }
            ceritaSet.add(cerita);
        } else if (cmd.equals("delete")) {
            ceritaSet.remove(cerita);
        }
        readingList.setCeritaSet(ceritaSet);
        return readingListRepository.save(readingList);
    }
}
