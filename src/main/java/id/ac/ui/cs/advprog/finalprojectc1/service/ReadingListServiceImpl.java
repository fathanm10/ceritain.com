package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import id.ac.ui.cs.advprog.finalprojectc1.model.ReadingList;
import id.ac.ui.cs.advprog.finalprojectc1.repository.CeritaRepository;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ReadingListServiceImpl implements ReadingListService {

    @Autowired
    ReadingListRepository readingListRepository;

    @Autowired
    CeritaRepository ceritaRepository;

    @Override
    public ReadingList createReadingList(String judul, String deskripsi) {
        String currentUsername = getCurrentUsername();
        var readinglist = ReadingList.builder()
                .judul(judul)
                .deskripsi(deskripsi)
                .creator(currentUsername)
                .build();
        return readingListRepository.save(readinglist);
    }

    @Override
    public ReadingList getReadingListById(int readinglistId) {
        return readingListRepository.getById(readinglistId);
    }

    @Override
    public ReadingList updateReadingList(int readinglistId, String judul, String deskripsi) {
        var readingList = getReadingListById(readinglistId);
        readingList.setJudul(judul);
        readingList.setDeskripsi(deskripsi);
        return readingListRepository.save(readingList);
    }

    @Override
    public void deleteReadingList(int readinglistId) {
        var readingList = getReadingListById(readinglistId);
        readingListRepository.delete(readingList);
    }

    @Override
    public List<ReadingList> getAllUserReadingList() {
        String currentUsername = getCurrentUsername();
        List<ReadingList> userReadingList = new ArrayList<>();
        List<ReadingList> allReadingList = readingListRepository.findAll();
        allReadingList.forEach(readingList -> {
            if (readingList.getCreator().equals(currentUsername)) userReadingList.add(readingList);
        });
        return userReadingList;
    }

    @Override
    public List<ReadingList> getAllReadingList() {
        return readingListRepository.findAll();
    }

    @Override
    public ReadingList updateCerita(int readinglistId, String ceritaId, String cmd) {
        var cerita = ceritaRepository.findById(ceritaId);
        var readingList = getReadingListById(readinglistId);
        if (cerita.isPresent()){
            if (cmd.equals("add")) {
                Set<Cerita> ceritaSet = readingList.getCeritaSet();
                ceritaSet.add(cerita.get());
                readingList.setCeritaSet(ceritaSet);
            } else if (cmd.equals("remove")) {
                Set<Cerita> ceritaSet = readingList.getCeritaSet();
                ceritaSet.remove(cerita.get());
                readingList.setCeritaSet(ceritaSet);
            }
        }
        return readingListRepository.save(readingList);
    }

    @Override
    public boolean matchCreatorWithUser (ReadingList readingList) {
        String currentUsername = getCurrentUsername();
        return readingList.getCreator().equals(currentUsername);
    }

    @Override
    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails)principal).getUsername();
    }
}
