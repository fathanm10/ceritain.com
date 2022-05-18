package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.ReadingList;

import java.util.List;

public interface ReadingListService {
    ReadingList createReadingList(String judul, String deskripsi);
    ReadingList getReadingListById(int readinglistId);
    ReadingList updateReadingList(int readinglistId, String judul, String deskripsi);
    void deleteReadingList(int readinglistId);
    List<ReadingList> getAllReadingList();
}
