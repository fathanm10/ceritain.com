package id.ac.ui.cs.advprog.finalprojectc1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="readinglist")
public class ReadingList {

    @Id
    @Column(name="readinglist_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="readinglist_judul")
    private String judul;

    @Column(name="readinglist_deskripsi")
    private String deskripsi;

    @Column(name = "creator")
    private String creator;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "readinglist_listcerita",
            joinColumns = @JoinColumn(name = "readinglist_id"),
            inverseJoinColumns = @JoinColumn(name = "cerita_id"))
    private Set<Cerita> ceritaSet;
}
