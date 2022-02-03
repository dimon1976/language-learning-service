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
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String description;
    private String shortDescription;

    @Column(updatable = false)
    private LocalDateTime dateCreating;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Course> courseList;

    @PrePersist
    protected void onCreate() {
        this.dateCreating = LocalDateTime.now();
    }
}
