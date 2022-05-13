package id.ac.ui.cs.advprog.finalprojectc1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "profile")
@Data
@NoArgsConstructor
@SequenceGenerator(name = "seqProfile", initialValue = 1)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqProfile")
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "bio")
    private String bio;

    @Column(name = "url")
    private String url;

//    @Lob
//    @Column(name = "photo")
//    private String photo;

//    @Column(name = "photo", nullable = true)
//    private String photo;




}
