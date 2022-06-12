package id.ac.ui.cs.advprog.finalprojectc1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

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

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "creator")
    private String creator;

}
