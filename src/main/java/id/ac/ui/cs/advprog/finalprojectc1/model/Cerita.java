package id.ac.ui.cs.advprog.finalprojectc1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cerita")
public class Cerita {

    @Id
    @Column(name="cerita_id")
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy= "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name="cerita_judul")
    private String judulCerita;

    @Column(name="cerita_isi")
    private String isiCerita;

}
