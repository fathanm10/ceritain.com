package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.ReadingList;
import id.ac.ui.cs.advprog.finalprojectc1.repository.CeritaRepository;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReadingListServiceImpl implements ReadingListService {

    @Autowired
    ReadingListRepository readingListRepository;

    @Autowired
    CeritaRepository ceritaRepository;

    @Override
    public ReadingList createReadingList(String judul, String deskripsi) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = ((UserDetails)principal).getUsername();
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = ((UserDetails)principal).getUsername();

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
                readingList.addCerita(cerita.get());
            } else if (cmd.equals("remove")) {
                readingList.removeCerita(cerita.get());
            }
        }
        return readingListRepository.save(readingList);
    }

    @Override
    public boolean matchCreatorWithUser (ReadingList readingList) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = ((UserDetails)principal).getUsername();

        return readingList.getCreator().equals(currentUsername);
    }
}