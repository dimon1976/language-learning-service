package by.languagelearningservice.entity;

import by.languagelearningservice.entity.courses.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;

    private String description;
    private String shortDescription;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Course> courses;

    @Enumerated(EnumType.STRING)
    private Language learning;

    @Column(updatable = false)
    private LocalDateTime dateCreating;

    private Boolean teacher;

    @PrePersist
    protected void onCreate() {
        this.dateCreating = LocalDateTime.now();
    }
}
